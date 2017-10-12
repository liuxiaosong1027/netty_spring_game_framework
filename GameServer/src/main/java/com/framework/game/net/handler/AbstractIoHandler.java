package com.framework.game.net.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.game.dispatcher.XDRDispatcher;
import com.framework.game.service.OnlineService;
import com.game.core.net.socket.message.ISenderMessage;
import com.game.core.net.socket.model.ISender;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

/**
 * 消息处理器
 */
public abstract class AbstractIoHandler<T extends ISender> extends ChannelInboundHandlerAdapter {
	private final AttributeKey<T> senderKey = AttributeKey.valueOf("sender.key");
	
	@Autowired private XDRDispatcher dispatcher;
	@Autowired private OnlineService onlineService;

	/**
	 * 由子类实现的
	 * 
	 * @return
	 */
	protected abstract T createSender();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		T sender = ctx.channel().attr(senderKey).get();
		if(sender == null) {
			ctx.close();
			return;
		}
		ISenderMessage message = (ISenderMessage)msg;
		message.setSender(sender);
		
		try {
			dispatcher.dispatchAction(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			message = null;
		}
    }

	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	if(!(cause instanceof IOException)) {
    		cause.printStackTrace();
    	}
        ctx.close();
    }

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		
		Channel channel = ctx.channel();
		T sender = this.createSender();
		sender.setChannel(channel);
		
		onlineService.addSender(sender);
		
		ctx.channel().attr(senderKey).set(sender);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		T sender = channel.attr(senderKey).get();
		if (sender != null) {
			onlineService.removeSender(sender);
			
			sender.disconnect();
			sender.setChannel(null);
			channel.attr(senderKey).set(null);
		}
		sender = null;
		
		super.channelUnregistered(ctx);
	}
}
