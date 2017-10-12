package com.framework.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.common.table.GameTableLoader;

/**
 * GM
 * @author lxs
 *
 */
@Service
public class AdminService {
	@Autowired private GameTableLoader gameTableLoader;
	
	/**
	 * 重新加载数据表
	 */
	public void reload() {
		gameTableLoader.setReload();
	}
}
