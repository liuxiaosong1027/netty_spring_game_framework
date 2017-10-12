package com.game.poker.model;

/**
 * 花色
 * @author lxs
 *
 */
public enum PokerSuits {
	/** ♠(黑) */
	SPADES("A"),
	/** ♥(红) */
	HEARTS("B"),
	/** ♦(方) */
	DIAMONDS("C"),
	/** ♣(梅) */
	CLUBS("D"),
	/** Joker */
	JOKER("Joker"),
	;
	
	private String suits;
	
	private PokerSuits(String suits) {
		this.suits = suits;
	}

	public String getSuits() {
		return suits;
	}
}
