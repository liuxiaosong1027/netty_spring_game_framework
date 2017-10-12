package com.db.common.lock;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db.common.repository.RedisRepository;

import redis.clients.jedis.ShardedJedisPool;

@Component
public class RedisLock {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);
	
	@Autowired private ShardedJedisPool shardedJedisPool;
	
	protected int _expireSeconds = 7;
	protected int _tryCount = 15;
	private static final String _FIELD = "redisLock";
	private RedisRepository<Long> redisRepository;
	
	@PostConstruct
	public void setup() {
		redisRepository = new RedisRepository<>(shardedJedisPool, _FIELD, true, _expireSeconds);
	}
	
	public boolean tryLock(String key) throws Exception {
		boolean ret = false;
		int count = 0;
		do {
			ret = redisRepository.setNX(key, System.currentTimeMillis());
			if(ret) {
				LOGGER.info("redis lock... [key:{}]", key);
				break;
			}
			count++;
			synchronized (key) {
				key.wait(50);
			}
		} while (!ret && count < _tryCount);
		
		return ret;
	}
	
	public void unlock(String key) {
		LOGGER.info("exit redis lock... [key:{}]", key);
		redisRepository.remove(key);
		synchronized (key) {
			key.notify();
		}
	}
	
	protected void setExpireSeconds(int _expireSeconds) {
		this._expireSeconds = _expireSeconds;
	}
}
