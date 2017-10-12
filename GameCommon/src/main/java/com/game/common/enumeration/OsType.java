package com.game.common.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作系统类型
 * @author lxs
 *
 */
public enum OsType {
	NONE(0),
	OS_IOS(1),
	OS_ANDROID(2),
	;

	private final int value;

	private static final Map<Integer, OsType> _map = new HashMap<Integer, OsType>();
	
	static {
		for (OsType enumClass : OsType.values())
			_map.put(enumClass.value, enumClass);
	}

	public static OsType from(int value) {
		OsType osType =  _map.get(value);
		if(osType == null) {
			osType = NONE;
		}
		return osType;
	}

	private OsType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
