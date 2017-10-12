package com.framework.game.net.handler;

import org.springframework.stereotype.Component;

import com.framework.game.GameServer;
import com.game.core.net.socket.codec.DefaultMessageDecoder;
import com.game.core.net.socket.codec.DefaultMessageEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

/**
 * Netty服务端初始化
 * @author lxs
 *
 */
@Component
public class GameServerInitializer extends ChannelInitializer<SocketChannel> {
	private SslContext sslCtx;
	
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        
        p.addLast(new DefaultMessageEncoder());
        p.addLast(new DefaultMessageDecoder());
        p.addLast(getHandler());
    }

	public SslContext getSslCtx() {
		return sslCtx;
	}

	public void setSslCtx(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}
	
	private GameServerIoHandler getHandler() {
		return GameServer.context.getBean(GameServerIoHandler.class);
	}
}
