package com.framework.login.net.handler;

import org.springframework.stereotype.Component;

import com.framework.login.LoginServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;

/**
 * Netty服务端初始化
 * @author lxs
 *
 */
@Component
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
	private SslContext sslCtx;

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        
        p.addLast(new HttpRequestDecoder());
        p.addLast(new HttpResponseEncoder());
        p.addLast(new HttpObjectAggregator(65536));
        p.addLast(getHandler());
    }
    
    public SslContext getSslCtx() {
		return sslCtx;
	}

	public void setSslCtx(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}

	private HttpServerHandler getHandler() {
		return LoginServer.context.getBean(HttpServerHandler.class);
	}
}
