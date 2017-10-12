package com.game.common.enumeration;

import java.util.HashMap;
import java.util.Map;

import com.game.common.consts.ConstValue;

/**
 * 登录服类型
 * @author lxs
 *
 */
public enum LoginServerType {
	MAIN(ConstValue.LOGIN_SERVER_NAME + "main"),
	PLATFORM(ConstValue.LOGIN_SERVER_NAME + "platform"),
	ADMIN(ConstValue.LOGIN_SERVER_NAME + "admin"),
	;
	
	private String type;
	
	private LoginServerType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	private static final Map<String, LoginServerType> _map = new HashMap<String, LoginServerType>();
	
	static {
		for (LoginServerType enumClass : LoginServerType.values())
			_map.put(enumClass.type, enumClass);
	}

	public static LoginServerType from(String reqValue) {
		return _map.get(reqValue);
	}
}
