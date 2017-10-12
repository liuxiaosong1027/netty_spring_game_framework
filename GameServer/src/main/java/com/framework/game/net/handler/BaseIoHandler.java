package com.framework.game.net.handler;

import com.game.core.net.socket.model.ISender;

import io.netty.channel.ChannelHandlerContext;

/**
 * 消息处理器
 */
public abstract class BaseIoHandler<T extends ISender> extends AbstractIoHandler<T> {

	public BaseIoHandler() {
	}
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		super.channelRead(ctx, msg);
    }
}
