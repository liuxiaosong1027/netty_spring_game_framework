package com.framework.login.net.handler;

import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.framework.login.dispatcher.XDRDispatcher;
import com.game.common.enumeration.LoginServerType;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;

@Component
@Scope("prototype")
public class HttpServerHandler extends ChannelInboundHandlerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerHandler.class);
	
	private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
    private static final AsciiString CONNECTION = new AsciiString("Connection");
    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");
    
    @Autowired private XDRDispatcher dispatcher;
    
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            if(HttpMethod.GET.equals(req.method())) {
            	LOGGER.error("error request Get.");
            	return;
            }
            HttpContent reqContent = (HttpContent) msg;
            ByteBuf buf = reqContent.content();
            String requestContent = buf.toString(CharsetUtil.UTF_8);
            buf.release();
            buf = null;
            reqContent = null;
            
            if (HttpUtil.is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            }
            String uri = req.uri();
            LoginServerType loginServerType = LoginServerType.from(uri);
            if(loginServerType == null) {
            	LOGGER.error("error request {}", uri);
            	return;
            }
            String responseContent = "";
        	switch(loginServerType) {
	            case MAIN:
	            	responseContent = dispatcher.requestHandler(requestContent);
	            	break;
	            case PLATFORM:
	            	break;
	            case ADMIN:
	            	break;
            }
            boolean keepAlive = HttpUtil.isKeepAlive(req);
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(responseContent.getBytes()));
            response.headers().set(CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.write(response);
            }
        }
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		if(!(cause instanceof IOException)) {
    		cause.printStackTrace();
    	}
        ctx.close();
    }
}
