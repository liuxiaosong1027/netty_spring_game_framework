package com.framework.login.dispatcher;

import com.game.core.dispatcher.DispatcherTemplate;
import com.game.core.net.exception.MessageParseException;

public abstract class XDRDispatcherTemplate<T> extends DispatcherTemplate<T> {
	/**
	 * 消息处理
	 * @param msg
	 * @return 返回错误码
	 * @throws MessageParseException
	 */
	public abstract int processMessage(T msg) throws MessageParseException;
}
