package com.game.poker.fightTheLandlord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.game.poker.model.Poker;
import com.game.poker.model.PokerSuits;

/**
 * 斗地主
 * @author lxs
 *
 */
public class FightTheLandlord {
	/**
	 * 生成牌集
	 * 基础牌(3、4、5、6、7、8、9、10、J(11)、Q(12)、K(13)、A(14)、2(15))
	 * 小王16、大王17
	 * @return
	 */
	public static List<Poker> buildPokers() {
		List<Poker> pokers = new ArrayList<Poker>(54);
		PokerSuits[] suits = PokerSuits.values();
		// 生成基础牌(3、4、5、6、7、8、9、10、J(11)、Q(12)、K(13)、1(14)、2(15))
		for(byte i=Poker.MIN_VALUE; i<=Poker.MAX_VALUE; i++) {
			for(PokerSuits pokerSuits : suits) {
				if(pokerSuits == PokerSuits.JOKER) {
					continue;
				}
				Poker poker = new Poker(pokerSuits.getSuits(), i);
				pokers.add(poker);
			}
		}
		// 添加大、小王
		Poker poker = new Poker(PokerSuits.JOKER.getSuits(), Poker.BLACK_JOKER_VALUE);
		pokers.add(poker);
		poker = new Poker(PokerSuits.JOKER.getSuits(), Poker.RED_JOKER_VALUE);
		pokers.add(poker);
		// 乱序
		Collections.shuffle(pokers);
		
		return pokers;
	}
	
	/**
	 * 比较牌集
	 * @param afterPokers 上一牌集
	 * @param pokers 当前牌集
	 * @return
	 */
	public static boolean comparePokers(List<Poker> afterPokers, List<Poker> pokers) {
		Collections.sort(afterPokers);
		Collections.sort(pokers);
		// 王炸
		if(isRocket(afterPokers)) {
			return false;
		}
		if(isRocket(pokers)) {
			return true;
		}
		// 炸
		if(isBomb(afterPokers) && isBomb(pokers)) {
			return checkSingle(afterPokers.get(0), pokers.get(0));
		}
		if(isBomb(afterPokers)) {
			return false;
		}
		if(isBomb(pokers)) {
			return true;
		}
		// 普通牌
		int afterSize = afterPokers.size();
		int size = pokers.size();
		// 单张
		if(afterSize == 1 && size == 1) {
			return checkSingle(afterPokers.get(0), pokers.get(0));
		} 
		// 对
		if(afterSize == 2 && size == 2 && isEqual(afterPokers) && isEqual(pokers)) {
			return checkSingle(afterPokers.get(0), pokers.get(0));
		} 
		// 三带一
		Poker afterComprePoker = null;
		Poker poker = null;
		afterComprePoker = getTripletToAttachedComparePoker(afterPokers);
		if(afterComprePoker != null && afterSize == size) {
			poker = getTripletToAttachedComparePoker(pokers);
			if(poker != null) {
				return checkSingle(afterComprePoker, poker);
			}
		}
		// 四带二
		afterComprePoker = getQuadplexSetComparePoker(afterPokers);
		if(afterComprePoker != null && afterSize == size) {
			poker = getQuadplexSetComparePoker(pokers);
			if(poker != null) {
				return checkSingle(afterComprePoker, poker);
			}
		}
		// 飞机
		afterComprePoker = getSequenceOfTripletsComparePoker(afterPokers);
		if(afterComprePoker != null && afterSize == size) {
			poker = getSequenceOfTripletsComparePoker(pokers);
			if(poker != null) {
				return checkSingle(afterComprePoker, poker);
			}
		}
		// 连对
		afterComprePoker = getSequenceOfPairsComparePoker(afterPokers);
		if(afterComprePoker != null && afterSize == size) {
			poker = getSequenceOfPairsComparePoker(pokers);
			if(poker != null) {
				return checkSingle(afterComprePoker, poker);
			}
		}
		// 顺子
		afterComprePoker = getSequenceComparePoker(afterPokers);
		if(afterComprePoker != null && afterSize == size) {
			poker = getSequenceComparePoker(pokers);
			if(poker != null) {
				return checkSingle(afterComprePoker, poker);
			}
		}
		return false;
	}
	
	/**
	 * 取得飞机的比较牌(最大连牌为A)
	 * @param pokers
	 * @return
	 */
	private static Poker getSequenceOfTripletsComparePoker(List<Poker> pokers) {
		int size = pokers.size();
		if(size > 5) {
			List<Poker> triplets = new ArrayList<Poker>();
			Poker poker = null;
			int num = 0;
			for(Poker p : pokers) {
				if(poker == null || poker.getValue() == p.getValue()) {
					poker = p;
					num ++;
				} else {
					poker = p;
					num = 1;
				}
				if(num >= 3) {
					triplets.add(p);
				}
			}
			int tripletSize = triplets.size();
			Poker maxPoker = triplets.get(tripletSize - 1);
			if(maxPoker.getValue() >= Poker.MAX_VALUE) {
				return null;
			}
			poker = triplets.get(0);
			for(int i=1; i<triplets.size(); i++) {
				if(poker.getValue() + 1 != triplets.get(i).getValue()) {
					return null;
				}
				poker = triplets.get(i);
			}
			int otherSize = size - tripletSize * 3;
			if(otherSize == tripletSize) {
				// 单牌
				return triplets.get(0);
			} else if(otherSize == tripletSize * 2) {
				// 对
				List<Poker> otherPokers = new ArrayList<Poker>(2);
				for(Poker p : pokers) {
					boolean isOtherPoker = true;
					for(Poker tripletPoker : triplets) {
						if(p.getValue() == tripletPoker.getValue()) {
							isOtherPoker = false;
							break;
						}
					}
					if(isOtherPoker) {
						otherPokers.add(p);
						if(otherPokers.size() == 2) {
							if(!isEqual(otherPokers)) {
								return null;
							}
							otherPokers.clear();
						}
					}
				}
				return triplets.get(0);
			}
		}
		return null;
	}
	
	/**
	 * 取得连对的比较牌(最大连牌为A，最小3对)
	 * @param pokers
	 * @return
	 */
	private static Poker getSequenceOfPairsComparePoker(List<Poker> pokers) {
		int size = pokers.size();
		if(size > 5 && size % 2 == 0) {
			Poker maxPoker = pokers.get(size - 1);
			if(maxPoker.getValue() >= Poker.MAX_VALUE) {
				return null;
			}
			byte value = 0;
			Poker poker = null;
			List<Poker> pairPokers = new ArrayList<Poker>(2);
			for(int i=0; i<size; i++) {
				if(i % 2 == 0) {
					poker = pokers.get(i);
					pairPokers.add(poker);
					if(value == 0) {
						value = poker.getValue();
					}
				} else {
					poker = pokers.get(i);
					pairPokers.add(poker);
					if(!isEqual(pairPokers)) {
						return null;
					}
					if(i != 1) {
						if(value + 1 != poker.getValue()) {
							return null;
						}
						value += 1;
					}
					pairPokers.clear();
				}
			}
			poker = null;
			pairPokers = null;
			return pokers.get(0);
		}
		return null;
	}
	
	/**
	 * 取得顺子的比较牌(最大连牌为A，最小5张)
	 * @param pokers
	 * @return
	 */
	private static Poker getSequenceComparePoker(List<Poker> pokers) {
		int size = pokers.size();
		if(size > 4) {
			Poker maxPoker = pokers.get(size - 1);
			if(maxPoker.getValue() >= Poker.MAX_VALUE) {
				return null;
			}
			Poker comparePoker = pokers.get(0);
			byte comparePokerValue = comparePoker.getValue();
			for(int i=1; i<pokers.size(); i++) {
				if(comparePokerValue + 1 == pokers.get(i).getValue()) {
					comparePokerValue ++;
				} else {
					return null;
				}
			}
			return comparePoker;
		}
		return null;
	}
	
	/**
	 * 取得四带二的比较牌
	 * @param pokers
	 * @return
	 */
	private static Poker getQuadplexSetComparePoker(List<Poker> pokers) {
		int size = pokers.size();
		if(size == 6) {
			// 四带二
			List<Poker> subPokers = pokers.subList(0, 4);
			if(isEqual(subPokers)) {
				return subPokers.get(0);
			}
			subPokers = pokers.subList(2, 6);
			if(isEqual(subPokers)) {
				return subPokers.get(0);
			}
		} else if(size == 8) {
			// 四带两对
			List<Poker> subPokers = pokers.subList(0, 4);
			List<Poker> endPokers1 = pokers.subList(4, 6);
			List<Poker> endPokers2 = pokers.subList(6, 8);
			if(isEqual(subPokers) && isEqual(endPokers1) && isEqual(endPokers2)) {
				return subPokers.get(0);
			}
			subPokers = pokers.subList(2, 6);
			endPokers1 = pokers.subList(0, 2);
			endPokers2 = pokers.subList(6, 8);
			if(isEqual(subPokers) && isEqual(endPokers1) && isEqual(endPokers2)) {
				return subPokers.get(0);
			}
			subPokers = pokers.subList(4, 8);
			endPokers1 = pokers.subList(0, 2);
			endPokers2 = pokers.subList(2, 4);
			if(isEqual(subPokers) && isEqual(endPokers1) && isEqual(endPokers2)) {
				return subPokers.get(0);
			}
		}
		return null;
	}
	
	/**
	 * 取得三带一的比较牌
	 * @param pokers
	 * @return
	 */
	private static Poker getTripletToAttachedComparePoker(List<Poker> pokers) {
		int size = pokers.size();
		if(size == 3) {
			// 三张
			if(isEqual(pokers)) {
				return pokers.get(0);
			}
		} else if(size == 4) {
			// 三带一
			List<Poker> subPokers = pokers.subList(0, 3);
			if(isEqual(subPokers)) {
				return subPokers.get(0);
			}
			subPokers = pokers.subList(1, 4);
			if(isEqual(subPokers)) {
				return subPokers.get(0);
			}
		} else if(size == 5) {
			// 三带一对
			List<Poker> subPokers = pokers.subList(0, 3);
			List<Poker> endPokers = pokers.subList(3, 5);
			if(isEqual(subPokers) && isEqual(endPokers)) {
				return subPokers.get(0);
			}
			subPokers = pokers.subList(2, 5);
			endPokers = pokers.subList(0, 2);
			if(isEqual(subPokers) && isEqual(endPokers)) {
				return subPokers.get(0);
			}
		}
		return null;
	}
	
	/**
	 * 检查单张
	 * @param afterPoker
	 * @param poker
	 * @return
	 */
	private static boolean checkSingle(Poker afterPoker, Poker poker) {
		return poker.getValue() > afterPoker.getValue();
	}
	
	/**
	 * 牌集是否同一张
	 * @param pokers
	 * @return
	 */
	private static boolean isEqual(List<Poker> pokers) {
		int value = 0;
		for(Poker poker : pokers) {
			if(value == 0) {
				value = poker.getValue();
			} else if(value != poker.getValue()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 是否炸
	 * @param pokers
	 * @return
	 */
	private static boolean isBomb(List<Poker> pokers) {
		int size = pokers.size();
		return size == 4 && isEqual(pokers);
	}
	
	/**
	 * 是否王炸
	 * @param pokers
	 * @return
	 */
	private static boolean isRocket(List<Poker> pokers) {
		int size = pokers.size();
		return size == 2 && pokers.get(0).isBlackJoker() && pokers.get(1).isRedJoker();
	}
	
	public static void main(String[] args) {
//		List<Poker> pokers = buildPokers();
//		Collections.sort(pokers);
//		System.out.println(pokers.size() + "" + pokers);
		
		List<Poker> afterPokers = new ArrayList<Poker>();
		List<Poker> pokers = new ArrayList<Poker>();
		Poker afterPoker1 = new Poker(PokerSuits.CLUBS.getSuits(), (byte)3);
		Poker afterPoker2 = new Poker(PokerSuits.DIAMONDS.getSuits(), (byte)3);
		Poker afterPoker3 = new Poker(PokerSuits.HEARTS.getSuits(), (byte)3);
		Poker afterPoker4 = new Poker(PokerSuits.SPADES.getSuits(), (byte)4);
		Poker afterPoker5 = new Poker(PokerSuits.DIAMONDS.getSuits(), (byte)4);
		Poker afterPoker6 = new Poker(PokerSuits.SPADES.getSuits(), (byte)4);
		Poker afterPoker7 = new Poker(PokerSuits.DIAMONDS.getSuits(), (byte)6);
		Poker afterPoker8 = new Poker(PokerSuits.SPADES.getSuits(), (byte)6);
		Poker afterPoker9 = new Poker(PokerSuits.DIAMONDS.getSuits(), (byte)7);
		Poker afterPoker10 = new Poker(PokerSuits.SPADES.getSuits(), (byte)7);
		afterPokers.add(afterPoker1);
		afterPokers.add(afterPoker2);
		afterPokers.add(afterPoker3);
		afterPokers.add(afterPoker4);
		afterPokers.add(afterPoker5);
		afterPokers.add(afterPoker6);
		afterPokers.add(afterPoker7);
		afterPokers.add(afterPoker8);
		afterPokers.add(afterPoker9);
		afterPokers.add(afterPoker10);
		
		Poker poker1 = new Poker(PokerSuits.CLUBS.getSuits(), (byte)10);
		Poker poker2 = new Poker(PokerSuits.DIAMONDS.getSuits(), (byte)10);
		Poker poker3 = new Poker(PokerSuits.HEARTS.getSuits(), (byte)10);
		Poker poker4 = new Poker(PokerSuits.SPADES.getSuits(), (byte)11);
		Poker poker5 = new Poker(PokerSuits.DIAMONDS.getSuits(), (byte)11);
		Poker poker6 = new Poker(PokerSuits.SPADES.getSuits(), (byte)11);
		Poker poker7 = new Poker(PokerSuits.DIAMONDS.getSuits(), (byte)12);
		Poker poker8 = new Poker(PokerSuits.SPADES.getSuits(), (byte)12);
		Poker poker9 = new Poker(PokerSuits.DIAMONDS.getSuits(), (byte)13);
		Poker poker10 = new Poker(PokerSuits.SPADES.getSuits(), (byte)13);
		pokers.add(poker1);
		pokers.add(poker2);
		pokers.add(poker3);
		pokers.add(poker4);
		pokers.add(poker5);
		pokers.add(poker6);
		pokers.add(poker7);
		pokers.add(poker8);
		pokers.add(poker9);
		pokers.add(poker10);
		for(int i=0; i<1; i++) {
			long time1 = System.currentTimeMillis();
			boolean result = comparePokers(afterPokers, pokers);
			long time2 = System.currentTimeMillis();
			System.out.println(result + "-----" + (time2 - time1));
		}
	}
}
