package com.db.common.session;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlSessionManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(SqlSessionManager.class);

	public static enum CommonDB {
		READ("CommonRead"),
		WRITE("CommonWrite");

		private final String value;

		private CommonDB(String value) {
			this.value = value;
		}

		public String toString() {
			return this.value;
		}
	}

	private Map<String, SqlSessionTemplate> commonDBSessionMap;
	private List<SqlSessionTemplate> gameDBSessionList;
	private List<SqlSessionTemplate> gameLogDBSessionList;

	public SqlSession getCommonDBSession(CommonDB sessionType) {
		try {
			return commonDBSessionMap.get(sessionType.toString());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public SqlSession getGameDBSession(int shardNo) {
		try {
			return gameDBSessionList.get(shardNo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public SqlSession getGameLogDBSession(int shardNo) {
		try {
			return gameLogDBSessionList.get(shardNo);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	private SqlSessionManager() {
		LOGGER.info("initialize...");
	}

	public void setGameDBSessionList(List<SqlSessionTemplate> gameDBSessionList) {
		this.gameDBSessionList = gameDBSessionList;
	}

	public void setGameLogDBSessionList(List<SqlSessionTemplate> gameLogDBSessionList) {
		this.gameLogDBSessionList = gameLogDBSessionList;
	}

	public void setCommonDBSessionMap(Map<String, SqlSessionTemplate> commonDBSessionMap) {
		this.commonDBSessionMap = commonDBSessionMap;
	}
}