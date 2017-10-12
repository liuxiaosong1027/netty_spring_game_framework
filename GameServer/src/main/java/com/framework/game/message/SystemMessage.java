package com.framework.game.message;

import com.game.common.enumeration.ErrorCode;
import com.game.common.enumeration.MessageType;
import com.game.core.net.socket.message.AbstractMessage;
import com.game.core.net.socket.message.Message;

/**
 * 系统消息
 * @author lxs
 *
 */
public class SystemMessage extends AbstractMessage {
	public static final int MSG_TYPE = MessageType.SYSTEM.getType();
	
	/** 错误码 */
	public int errorCode;
	
	public SystemMessage(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public SystemMessage(ErrorCode errorCode) {
		this.errorCode = errorCode.getValue();
	}

	@Override
	public int getMsgType() {
		return MSG_TYPE;
	}

	@Override
	protected void decodeBody(Message msg) {
		
	}

	@Override
	protected void encodeBody(Message msg) {
		msg.setInt(errorCode);
	}

}
