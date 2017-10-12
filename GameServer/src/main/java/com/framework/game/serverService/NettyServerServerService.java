package com.framework.game.serverService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.framework.game.net.AbstractNettyServer;
import com.game.core.service.IServerService;

/**
 * 网络服务
 * @author lxs
 *
 */
@Service
public class NettyServerServerService extends AbstractNettyServer implements IServerService {
	@Value("${isSsl}") private String isSsl;
	@Value("${nettyPort}") private String nettyPort;
	
	@Override
	public void onStart() throws Exception {
		new Thread() {
			public void run() {
				try {
					startNetty();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	public void onDown() {
		this.stopNetty();
	}

	@Override
	public void onReady() throws Exception {
		setSsl(Boolean.valueOf(isSsl));
		setPort(Integer.valueOf(nettyPort));
	}
}
