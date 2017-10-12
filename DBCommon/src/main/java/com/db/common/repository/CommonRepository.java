package com.db.common.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.db.common.mapper.user.DistShardHostBean;
import com.db.common.mapper.user.DistUserBean;
import com.db.common.mapper.user.DistUserMapper;
import com.db.common.session.SqlSessionManager;
import com.db.common.session.SqlSessionManager.CommonDB;

import redis.clients.jedis.ShardedJedisPool;

@Repository
public class CommonRepository {
	@Autowired private SqlSessionManager sessionManager;
	@Autowired private ShardedJedisPool shardedJedisPool;
	
	private static final String _FIELD = "distUserBean";
	private static final String _SHARD_DB_FIELD = "shardDBHost";
	private static final String _SHARD_DB_LIST_KEY = "shardDBListKey";
	private RedisRepository<DistUserBean> redisRepository;
	private RedisRepository<List<DistShardHostBean>> shardDBListRepository;
	
	@PostConstruct
	public void setup() {
		redisRepository = new RedisRepository<DistUserBean>(shardedJedisPool, _FIELD);
		shardDBListRepository = new RedisRepository<List<DistShardHostBean>>(shardedJedisPool, _SHARD_DB_FIELD);
	}
	
	/**
	 * 取得 db shard列表
	 * @return
	 */
	public List<DistShardHostBean> getDistShardDBInfo() {
		List<DistShardHostBean> distShardBeanList = shardDBListRepository.find(_SHARD_DB_LIST_KEY);
		if(distShardBeanList == null) {
			SqlSession session = sessionManager.getCommonDBSession(CommonDB.READ);
			DistUserMapper distUserMap = session.getMapper(DistUserMapper.class);
			distShardBeanList = distUserMap.getDistShardDBInfo();
			if(distShardBeanList != null) {
				shardDBListRepository.save(_SHARD_DB_LIST_KEY, distShardBeanList);
			}
		}
		return distShardBeanList;
	}
	
	/**
	 * 根据userNo取得用户信息
	 * @param userNo
	 * @return
	 */
	public DistUserBean getDistUser(long userNo) {
		DistUserBean distUserBean = redisRepository.find(String.valueOf(userNo));
		if(distUserBean == null) {
			SqlSession session = sessionManager.getCommonDBSession(CommonDB.READ);
			DistUserMapper distUserMap = session.getMapper(DistUserMapper.class);
			distUserBean = distUserMap.getDistUser(userNo);
			if(distUserBean != null) {
				redisRepository.save(String.valueOf(userNo), distUserBean);
			}
		}
		return distUserBean;
	}
	
	/**
	 * 根据mid取得用户信息
	 * @param userNo
	 * @param platform_code
	 * @return
	 */
	public DistUserBean getDistUserByMid(String userMid, int platform_code) {
		DistUserBean distUserBean = redisRepository.find(getMidKey(userMid, platform_code));
		if(distUserBean == null) {
			SqlSession session = sessionManager.getCommonDBSession(CommonDB.READ);
			DistUserMapper distUserMap = session.getMapper(DistUserMapper.class);
			distUserBean = distUserMap.getDistUserByMid(userMid, platform_code);
			if(distUserBean != null) {
				redisRepository.save(getMidKey(userMid, platform_code), distUserBean);
			}
		}
		return distUserBean;
	}

	/**
	 * 添加用户信息
	 * @param distUser
	 */
	public void addDistUser(DistUserBean distUser) {
		SqlSession session = sessionManager.getCommonDBSession(CommonDB.WRITE);
		DistUserMapper distUserMap = session.getMapper(DistUserMapper.class);
		distUserMap.addDistUser(distUser);
	}
	
	/**
	 * 更新DistUserBean
	 * @param distUser
	 */
	public void updateDistUserBean(DistUserBean distUser) {
		SqlSession session = sessionManager.getCommonDBSession(CommonDB.WRITE);
		DistUserMapper distUserMap = session.getMapper(DistUserMapper.class);
		distUserMap.updateDistUserBean(distUser);
	}
	
	private String getMidKey(String userMid, int platform_code) {
		return userMid + "_" + platform_code;
	}
}
