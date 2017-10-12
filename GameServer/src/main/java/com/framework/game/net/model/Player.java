package com.framework.game.net.model;

import com.game.core.net.socket.message.AbstractMessage;
import com.game.core.net.socket.message.Message;
import com.game.core.net.socket.model.ISender;

import io.netty.channel.Channel;

/**
 * 玩家
 * 
 * @author lxs
 *
 */
public class Player implements ISender {
	/** 玩家id */
	private int pid = 0;
	/** 所在场景的id */
	private int sceneId = 0;
	/** Channel */
	private Channel channel = null;

	/** 玩家id */
	public int getPid() {
		return pid;
	}

	/** 玩家id */
	public void setPid(int pid) {
		this.pid = pid;
	}

	/** 所在场景的id */
	public int getSceneId() {
		return sceneId;
	}

	/** 所在场景的id */
	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	@Override
	public void disconnect() {
		
	}

	@Override
	public boolean closeOnException() {
		return false;
	}
	
	@Override
	public void sendMsg(AbstractMessage msg) {
		Message message = msg.encodeMessage();
		if(message != null) {
			channel.writeAndFlush(message);
		}
	}

	@Override
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public Channel getChannel() {
		return channel;
	}
}
