package com.framework.login.model;

import java.io.Serializable;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class ResponseModel implements Serializable {
	/** 消息编号 */
	private int message_no;
	/** 错误码 */
	private int error_code;
	/** 消息数据 */
	private Object message_data;

	/** 消息编号 */
	public int getMessage_no() {
		return message_no;
	}

	/** 消息编号 */
	public void setMessage_no(int message_no) {
		this.message_no = message_no;
	}

	/** 错误码 */
	public int getError_code() {
		return error_code;
	}

	/** 错误码 */
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	/** 消息数据 */
	public Object getMessage_data() {
		return message_data;
	}

	/** 消息数据 */
	public void setMessage_data(Object message_data) {
		this.message_data = message_data;
	}

	@Override
	public String toString() {
		return "ResponseModel [message_no=" + message_no + ", error_code=" + error_code + ", message_data="
				+ message_data + "]";
	}
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("message_no", message_no);
		json.put("error_code", error_code);
		json.put("message_data", message_data);
		return json;
	}
}
