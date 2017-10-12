package com.framework.login.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RequestModel implements Serializable {
	/** 玩家编号 */
	private long user_no;
	/** 操作系统类型 */
	private byte os_type;
	/** 版本号 */
	private String version;
	/** 消息号 */
	private int message_no;
	/** 访问令牌 */
	private String accessToken;
	/** 消息数据 */
	private Object message_data;
	/** 签名 */
	private String sign;

	/** 玩家编号 */
	public long getUser_no() {
		return user_no;
	}

	/** 玩家编号 */
	public void setUser_no(long user_no) {
		this.user_no = user_no;
	}

	/** 操作系统类型 */
	public byte getOs_type() {
		return os_type;
	}

	/** 操作系统类型 */
	public void setOs_type(byte os_type) {
		this.os_type = os_type;
	}

	/** 版本号 */
	public String getVersion() {
		return version;
	}

	/** 版本号 */
	public void setVersion(String version) {
		this.version = version;
	}

	/** 消息号 */
	public int getMessage_no() {
		return message_no;
	}

	/** 消息号 */
	public void setMessage_no(int message_no) {
		this.message_no = message_no;
	}

	/** 访问令牌 */
	public String getAccessToken() {
		return accessToken;
	}

	/** 访问令牌 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/** 消息数据 */
	public Object getMessage_data() {
		return message_data;
	}

	/** 消息数据 */
	public void setMessage_data(Object message_data) {
		this.message_data = message_data;
	}

	/** 签名 */
	public String getSign() {
		return sign;
	}

	/** 签名 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "RequestModel [user_no=" + user_no + ", os_type=" + os_type + ", version=" + version + ", message_no="
				+ message_no + ", message_data=" + message_data + ", sign=" + sign + "]";
	}
}
