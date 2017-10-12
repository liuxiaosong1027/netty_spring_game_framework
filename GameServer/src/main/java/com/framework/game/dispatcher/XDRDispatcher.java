package com.framework.game.dispatcher;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.common.lock.RedisLock;
import com.framework.game.message.SystemMessage;
import com.framework.game.net.model.Player;
import com.game.common.enumeration.ErrorCode;
import com.game.core.dispatcher.Dispatcher;
import com.game.core.dispatcher.DispatcherTemplate;
import com.game.core.dispatcher.IMessage;
import com.game.core.net.socket.message.AbstractMessage;
import com.game.core.net.socket.message.ISenderMessage;
import com.game.core.net.socket.message.Message;
import com.game.core.net.socket.model.ISender;

@Component
public class XDRDispatcher extends Dispatcher {
	private static final Logger LOGGER = LoggerFactory.getLogger(XDRDispatcher.class);
	
	@Autowired(required = false) 
	private List<DispatcherTemplate<? extends IMessage>> messageActions;
	@Autowired private RedisLock redisLock;
	
	public XDRDispatcher() {
		super("MSG_TYPE");
	}
	
	@PostConstruct
	public void setup() {
		register(messageActions);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dispatchAction(ISenderMessage<ISender> message) throws Exception {
		Player player = (Player)message.getSender();
		Message msg = (Message)message;
		int messageId = msg.getType();
		XDRDispatcherTemplate handler = (XDRDispatcherTemplate)messageActionMap.get(messageId);
		if(handler == null) {
			LOGGER.error("消息未注册！{}", messageId);
			return;
		}
		AbstractMessage abstractMessage = (AbstractMessage)handler.getMsg().newInstance();
		abstractMessage.decodeMessage(msg);
		LOGGER.info("userId[{}]---message->{}", player.getPid(), abstractMessage.toString());
		long time1 = System.currentTimeMillis();
		if(handler.needUserLock()) {
			if(redisLock.tryLock(String.valueOf(player.getPid()))) {
				try {
					handler.processMessage(abstractMessage, player);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					redisLock.unlock(String.valueOf(player.getPid()));
				}
			} else {
				// 服务器繁忙 
				SystemMessage systemMessage = new SystemMessage(ErrorCode.ERR_SERVER_IS_BUSY);
				player.sendMsg(systemMessage);
				
				abstractMessage = systemMessage;
			}
		} else {
			try {
				handler.processMessage(abstractMessage, player);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long time2 = System.currentTimeMillis();
		LOGGER.info("userId[{}]---handler message->{}---handler time {}.", player.getPid(), abstractMessage.toString(), (time2 - time1));
	}
}
