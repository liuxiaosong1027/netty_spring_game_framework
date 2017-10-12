package com.framework.login.message;

import org.json.JSONObject;

import com.framework.login.net.message.JSONMessage;
import com.game.common.enumeration.MessageType;
import com.game.core.net.exception.MessageParseException;

public class TestMessage extends JSONMessage {
	public static final int MSG_TYPE = MessageType.LOGIN_TEST.getType();

	public String content;
	
	@Override
	public int getMsgType() {
		return MSG_TYPE;
	}

	@Override
	public void loadJson(JSONObject messageData) throws MessageParseException {
		content = messageData.getString("content");
	}

	@Override
	public JSONObject saveJson() throws MessageParseException {
		JSONObject json = new JSONObject();
		json.put("content", content);
		return json;
	}

}
