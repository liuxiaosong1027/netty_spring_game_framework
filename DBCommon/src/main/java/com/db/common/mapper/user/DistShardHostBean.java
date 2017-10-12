package com.db.common.mapper.user;

import java.io.Serializable;

/**
 * db shard
 * @author lxs
 *
 */
@SuppressWarnings("serial")
public class DistShardHostBean implements Serializable, Comparable<DistShardHostBean> {
	private int db_id;
	private String host;
	private int user_count;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getDb_id() {
		return db_id;
	}

	public void setDb_id(int db_id) {
		this.db_id = db_id;
	}

	public void setUser_count(int user_count) {
		this.user_count = user_count;
	}

	public int getUser_count() {
		return this.user_count;
	}

	@Override
	public String toString() {
		return "DistShardHostBean [db_id=" + db_id + ", host=" + host + ", user_count=" + user_count + "]";
	}

	@Override
	public int compareTo(DistShardHostBean o) {
		return this.user_count - o.user_count;
	}
}
