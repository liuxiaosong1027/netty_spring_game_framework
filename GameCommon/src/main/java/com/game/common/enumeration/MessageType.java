package com.game.common.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息类型
 * @author lxs
 *
 */
public enum MessageType {
	/**
	 * login服消息(1~999)
	 */
	LOGIN_TEST(1),
	/**
	 * game服消息(1000~1999)
	 */
	/** 系统消息 */
	SYSTEM(1000),
	TEST(1100),
	;
	
	private int type;
	
	private MessageType(int type) {
		this.type = type;
	}

	private static final Map<Integer, MessageType> _map = new HashMap<Integer, MessageType>();
	
	static {
		for (MessageType enumClass : MessageType.values())
			_map.put(enumClass.type, enumClass);
	}

	public static MessageType from(int reqValue) {
		return _map.get(reqValue);
	}

	public int getType() {
		return type;
	}
}
