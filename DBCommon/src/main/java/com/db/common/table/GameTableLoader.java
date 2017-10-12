package com.db.common.table;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.common.repository.RedisRepository;

import redis.clients.jedis.ShardedJedisPool;

/**
 * 加载游戏配置表
 * @author lxs
 */
@Component
public class GameTableLoader extends TimerTask {
	public static final Logger LOGGER = LoggerFactory.getLogger(GameTableLoader.class);
	private static Date tempDate = new Date();
	@Autowired private List<Loadable> tables;
	@Autowired private ShardedJedisPool shardedJedisPool;
	
	private static final String _FIELD = "gameTable";
	private static final String TIME_KEY = "timeKey";
	private RedisRepository<Long> redisRepository;
	
	@PostConstruct
	public void setup() {
		redisRepository = new RedisRepository<>(shardedJedisPool, _FIELD, false);
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
		pool.scheduleAtFixedRate(this, 30000, 30000, TimeUnit.MILLISECONDS);
		loadTable();
		Long version = redisRepository.find(TIME_KEY);
		if(version != null && version > 0) {
			tempDate = new Date(version);
		}
	}

	public synchronized void loadTable() {
		LOGGER.info("start load tables...");
		for (Loadable table : tables) {
			try {
				table.load();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("end load tables.");
	}
	
	@Override
	public void run() {
		try {
			Long version = redisRepository.find(TIME_KEY);
			if(version == null || version <= tempDate.getTime()) {
				return;
			}
			loadTable();
			tempDate = new Date(version);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setReload() {
		redisRepository.save(TIME_KEY, System.currentTimeMillis());
	}
}
