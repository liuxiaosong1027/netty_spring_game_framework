package com.db.common.mapper.user;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class DistUserBean implements Serializable, Comparable<DistUserBean> {
	/** 玩家id */
	private long user_no;
	/** db id */
	private int db_id;
	/** 玩家唯一标识(openid、 用户名) */
	private String user_mid;
	/** 平台编号 */
	private int platform_code;
	/** 版本号 */
	private String version;
	/** 操作系统 */
	private int os_type;
	/** 注册时间 */
	private Date reg_date;

	/** 玩家id */
	public long getUser_no() {
		return user_no;
	}

	/** 玩家id */
	public void setUser_no(long user_no) {
		this.user_no = user_no;
	}

	/** 平台编号 */
	public int getPlatform_code() {
		return platform_code;
	}

	/** 平台编号 */
	public void setPlatform_code(int platform_code) {
		this.platform_code = platform_code;
	}

	/** db id */
	public int getDb_id() {
		return db_id;
	}

	/** db id */
	public void setDb_id(int db_id) {
		this.db_id = db_id;
	}

	/** 玩家唯一标识(openid、 用户名) */
	public String getUser_mid() {
		return user_mid;
	}

	/** 玩家唯一标识(openid、 用户名) */
	public void setUser_mid(String user_mid) {
		this.user_mid = user_mid;
	}

	/** 版本号 */
	public String getVersion() {
		return version;
	}

	/** 版本号 */
	public void setVersion(String version) {
		this.version = version;
	}

	/** 操作系统 */
	public int getOs_type() {
		return os_type;
	}

	/** 操作系统 */
	public void setOs_type(int os_type) {
		this.os_type = os_type;
	}

	/** 注册时间 */
	public Date getReg_date() {
		return reg_date;
	}

	/** 注册时间 */
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	@Override
	public int compareTo(DistUserBean o) {
		return this.db_id - o.db_id;
	}
}
