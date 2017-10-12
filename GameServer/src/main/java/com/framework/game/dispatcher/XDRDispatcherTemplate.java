package com.framework.game.dispatcher;

import com.framework.game.net.model.Player;
import com.game.core.dispatcher.DispatcherTemplate;
import com.game.core.net.exception.MessageParseException;

public abstract class XDRDispatcherTemplate<T> extends DispatcherTemplate<T> {
	public abstract void processMessage(T msg, Player player) throws MessageParseException;
}
