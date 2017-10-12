package com.game.poker.model;

import java.io.Serializable;

/**
 * 牌
 * 基础牌(3、4、5、6、7、8、9、10、J(11)、Q(12)、K(13)、A(14)、2(15))
 * 小王16、大王17
 * @author lxs
 *
 */
@SuppressWarnings("serial")
public class Poker implements Serializable, Comparable<Poker> {
	/** 小王值 */
	public static final byte BLACK_JOKER_VALUE = 16;
	/** 大王值 */
	public static final byte RED_JOKER_VALUE = 17;
	/** 最小牌值(基础牌) */
	public static final byte MIN_VALUE = 3;
	/** 最大牌值(基础牌) */
	public static final byte MAX_VALUE = 15;
	
	/** 花色 */
	private String suits;
	/** 值 */
	private byte value;
	
	public Poker(String suits, byte value) {
		this.suits = suits;
		this.value = value;
	}

	/** 花色 */
	public String getSuits() {
		return suits;
	}

	/** 花色 */
	public void setSuits(String suits) {
		this.suits = suits;
	}

	/** 值 */
	public byte getValue() {
		return value;
	}

	/** 值 */
	public void setValue(byte value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Poker [suits=" + suits + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suits == null) ? 0 : suits.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poker other = (Poker) obj;
		if (suits == null) {
			if (other.suits != null)
				return false;
		} else if (!suits.equals(other.suits))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public int compareTo(Poker o) {
		if(o.value - this.value != 0) {
			return this.value - o.value;
		} else {
			return this.suits.compareTo(o.suits);
		}
	}
	
	/**
	 * 是否小王
	 * @return
	 */
	public boolean isBlackJoker() {
		return PokerSuits.JOKER.equals(suits) && value == BLACK_JOKER_VALUE;
	}
	
	/**
	 * 是否大王
	 * @return
	 */
	public boolean isRedJoker() {
		return PokerSuits.JOKER.equals(suits) && value == RED_JOKER_VALUE;
	}
}
