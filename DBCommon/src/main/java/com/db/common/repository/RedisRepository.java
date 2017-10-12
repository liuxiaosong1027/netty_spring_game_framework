package com.db.common.repository;

import com.db.common.util.SerializationUtil;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisRepository<T> implements IRepository<T> {
	private static final int INIT_EXPIRE_SECONDS = 300;
	private String _FIELD;
	private int _EXPIRE_SECONDS;
	private boolean isAutoClear = false;
    private ShardedJedisPool shardedJedisPool;
	
	public RedisRepository(ShardedJedisPool shardedJedisPool, String field) {
		this(shardedJedisPool, field, true, INIT_EXPIRE_SECONDS);
	}
	
	public RedisRepository(ShardedJedisPool shardedJedisPool, String field, boolean isAutoClear) {
		this(shardedJedisPool, field, isAutoClear, INIT_EXPIRE_SECONDS);
	}
	
	public RedisRepository(ShardedJedisPool shardedJedisPool, String field, int expireSeconds) {
		this(shardedJedisPool, field, true, expireSeconds);
	}
	
	public RedisRepository(ShardedJedisPool shardedJedisPool, String field, boolean isAutoClear, int expireSeconds) {
		this.shardedJedisPool = shardedJedisPool;
		this._FIELD = field;
		this.isAutoClear = isAutoClear;
		this._EXPIRE_SECONDS = expireSeconds;
	}
	
	@Override
	public void save(String key, T obj) {
		byte[] bKey = buildKey(key);
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			byte[] value = SerializationUtil.serialize(obj);
			shardedJedis.set(bKey, value);
			if(isAutoClear) {
				shardedJedis.expire(bKey, _EXPIRE_SECONDS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(shardedJedis != null) {
				shardedJedisPool.returnResourceObject(shardedJedis);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T find(String key) {
		byte[] bKey = buildKey(key);
		ShardedJedis shardedJedis = null;
		byte[] value = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			value = shardedJedis.get(bKey);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(shardedJedis != null) {
				shardedJedisPool.returnResourceObject(shardedJedis);
			}
		}
		
		if(value != null) {
			return (T)SerializationUtil.deserialize(value);
			
		}
		return null;
	}

	@Override
	public boolean isExist(String key) {
		byte[] bKey = buildKey(key);
		ShardedJedis shardedJedis = null;
		boolean isExist = false;
		try {
			shardedJedis = shardedJedisPool.getResource();
			isExist = shardedJedis.exists(bKey);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(shardedJedis != null) {
				shardedJedisPool.returnResourceObject(shardedJedis);
			}
		}
		return isExist;
	}
	
	@Override
	public void remove(String key) {
		byte[] bKey = buildKey(key);
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.del(bKey);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(shardedJedis != null) {
				shardedJedisPool.returnResourceObject(shardedJedis);
			}
		}
	}
	
	@Override
	public boolean setNX(String key, T obj) {
		boolean setnx = false;
		byte[] bKey = buildKey(key);
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			byte[] value = SerializationUtil.serialize(obj);
			Long nx = shardedJedis.setnx(bKey, value);
			if(nx != null && nx >= 1) {
				setnx = true;
			}
			if(isAutoClear) {
				shardedJedis.expire(bKey, _EXPIRE_SECONDS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(shardedJedis != null) {
				shardedJedisPool.returnResourceObject(shardedJedis);
			}
		}
		return setnx;
	}
	
	private byte[] buildKey(String key) {
		return (_FIELD + ":" + key).getBytes();
	}
}
