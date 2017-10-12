package com.framework.login.net;

import org.springframework.beans.factory.annotation.Autowired;

import com.framework.login.net.handler.HttpServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * 抽象Netty http服务端
 * 
 * @author lxs
 *
 */
public abstract class AbstractNettyHttpServer {
	/** 端口 */
	protected int port = 9001;
	/** 是否配置ssl(SslContext) */
	protected boolean ssl = false;
	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;
	
	@Autowired private HttpServerInitializer initializer;

	public AbstractNettyHttpServer() {

	}

	/**
	 * 启动服务
	 * 
	 * @throws Exception
	 */
	protected final void startNetty() throws Exception {
		final SslContext sslCtx;
        if (ssl) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        initializer.setSslCtx(sslCtx);
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(initializer)
			// 允许重复使用本地地址和端口
			.option(ChannelOption.SO_REUSEADDR, true)
			// BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
			.option(ChannelOption.SO_BACKLOG, 10000)
			// 超时
			.option(ChannelOption.SO_TIMEOUT, 5000)
			// 是否启用心跳保活机制。在双方TCP套接字建立连接后（即都进入ESTABLISHED状态）并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活。
			.childOption(ChannelOption.SO_KEEPALIVE, true);

		// Start the server.
        ChannelFuture f = b.bind(port).sync();

        // Wait until the server socket is closed.
        f.channel().closeFuture().sync();
	}

	/**
	 * 停止服务
	 */
	protected final void stopNetty() {
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
		bossGroup = null;
		workerGroup = null;
	}

	/** 端口 */
	public int getPort() {
		return port;
	}

	/** 端口 */
	public void setPort(int port) {
		this.port = port;
	}

	/** 是否配置ssl(SslContext) */
	public boolean isSsl() {
		return ssl;
	}

	/** 是否配置ssl(SslContext) */
	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}
}
