package com.framework.game;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.game.core.service.AbstractService;
import com.game.core.service.IServerService;

/**
 * GameServer入口类
 * @author lxs
 *
 */
@Service
public class GameServer extends AbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);
	public static final String SERVICE_ID_ROOT = "SERVICE_ROOT";
	public static ApplicationContext context;
	
	@Autowired(required = false) 
	private List<IServerService> serverServices;
	
	public GameServer() {
		super(SERVICE_ID_ROOT);
	}
	
	@Override
	public boolean initService() {
		boolean isInit = false;
		try {
			for(IServerService serverService : serverServices) {
				serverService.onReady();
			}
			isInit = true;
			LOGGER.info("初始化服务成功。");
		} catch (Exception e) {
			LOGGER.error("初始化服务失败！！！{}", e);
			stopService();
		}
		return isInit;
	}

	@Override
	public boolean startService() {
		boolean isStart = false;
		try {
			for(IServerService serverService : serverServices) {
				serverService.onStart();
			}
			isStart = true;
			LOGGER.info("启动服务成功。");
		} catch (Exception e) {
			LOGGER.error("启动服务成功。{}", e);
		}
		return isStart;
	}

	@Override
	public boolean stopService() {
		boolean isStop = false;
		try {
			for(IServerService serverService : serverServices) {
				serverService.onDown();
			}
			isStop = true;
			LOGGER.info("关闭服务成功。");
		} catch (Exception e) {
			LOGGER.error("关闭服务失败！！！{}", e);
		}
		return isStop;
	}

	/**
	 * 启动game server
	 */
	@PostConstruct
	public void startServer() {
		boolean result = initService();
		if(result) {
			result = startService();
		}
		if(result) {
			// 启动成功
			LOGGER.info("game server启动成功。");
		} else {
			LOGGER.info("game server启动失败！！！");
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("classpath:root-context.xml");
	}
}
