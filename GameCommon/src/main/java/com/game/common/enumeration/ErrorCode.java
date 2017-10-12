package com.game.common.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误码
 * @author lxs
 *
 */
public enum ErrorCode {
	/** 成功 */
	ERR_SUCCESS(0),
	/** 失败 */
	ERR_FAILED(-1),
	/** 服务器繁忙 */
	ERR_SERVER_IS_BUSY(-2),
	/** 消息不存在 */
	ERR_MESSAGE_NOT_FOUND(-3),
	/** 玩家不存在*/
	ERR_USER_NOT_FOUND(1000),
	;

	private final int value;

	private static final Map<Integer, ErrorCode> _map = new HashMap<Integer, ErrorCode>();
	static {
		for (ErrorCode enumClass : ErrorCode.values())
			_map.put(enumClass.value, enumClass);
	}

	public static ErrorCode from(int value) {
		return _map.get(value);
	}

	private ErrorCode(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
