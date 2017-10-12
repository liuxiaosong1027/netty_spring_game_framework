package com.framework.login.serverService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.framework.login.net.AbstractNettyHttpServer;
import com.game.core.service.IServerService;

/**
 * 网络服务
 * @author lxs
 *
 */
@Service
public class HttpServerService extends AbstractNettyHttpServer implements IServerService {
	@Value("${isSsl}") private String isSsl;
	@Value("${httpPort}") private String httpPort;

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
		setPort(Integer.valueOf(httpPort));
	}
}
