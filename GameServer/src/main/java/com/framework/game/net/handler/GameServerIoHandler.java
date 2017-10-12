package com.framework.game.net.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.framework.game.net.model.Player;

import io.netty.channel.ChannelHandlerContext;

/**
 * Game Server的网络消息接收器
 */
@Component
@Scope("prototype")
public class GameServerIoHandler extends BaseIoHandler<Player> {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		super.channelRead(ctx, msg);
	}
	
	/**
	 * 创建Sender
	 */
	@Override
	protected Player createSender() {
		return new Player();
	}
}
