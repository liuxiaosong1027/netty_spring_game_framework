package com.db.common.mapper.user;

import java.util.List;

public interface DistUserMapper {
	/**
	 * 取得 db shard 列表
	 * @return
	 */
	public List<DistShardHostBean> getDistShardDBInfo();
	/**
	 * 根据userNo取得用户信息
	 * @param userNo
	 * @return
	 */
	public DistUserBean getDistUser(long userNo);
	/**
	 * 根据mid取得用户信息
	 * @param userNo
	 * @param platform_code
	 * @return
	 */
	public DistUserBean getDistUserByMid(String userMid, int platform_code);
	/**
	 * 添加用户信息
	 * @param distUser
	 */
	public void addDistUser(DistUserBean distUser);
	/**
	 * 更新DistUserBean
	 * @param distUser
	 */
	public void updateDistUserBean(DistUserBean distUser);
}
