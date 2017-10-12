package com.framework.game.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.game.core.net.socket.model.ISender;

import io.netty.channel.Channel;

/**
 * 在线玩家
 * @author lxs
 *
 */
@Service
public class OnlineService {
	/** sender集合 */
	private Map<Channel, ISender> senderMap = new ConcurrentHashMap<>();
	
	/**
	 * 添加sender
	 * @param sender
	 */
	public void addSender(ISender sender) {
		if(sender != null) {
			senderMap.put(sender.getChannel(), sender);
		}
	}
	
	/**
	 * 移除sender
	 * @param sender
	 */
	public void removeSender(ISender sender) {
		if(sender != null) {
			senderMap.remove(sender.getChannel());
		}
	}
	
	/**
	 * 取得在线玩家集合
	 * @return
	 */
	public Map<Channel, ISender> getSenderMap() {
		return senderMap;
	}
	
	/**
	 * 取得在线人数
	 * @return
	 */
	public int getCurrentChannelNum() {
		return senderMap.size();
	}
}
