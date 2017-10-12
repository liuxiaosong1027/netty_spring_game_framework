package com.framework.game.message;

import com.game.common.enumeration.MessageType;
import com.game.core.net.socket.message.AbstractMessage;
import com.game.core.net.socket.message.Message;

public class TestMessage extends AbstractMessage {
	public static final int MSG_TYPE = MessageType.TEST.getType();
	public String content;
	
	@Override
	public int getMsgType() {
		return MSG_TYPE;
	}
	
	@Override
	public void decodeBody(Message msg) {
		content = msg.getString();
	}

	@Override
	public void encodeBody(Message msg) {
		msg.setString(content);
	}

	@Override
	public String toString() {
		return "TestMessage [content=" + content + "]";
	}
}
