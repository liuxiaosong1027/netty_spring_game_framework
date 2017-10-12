package com.framework.game.entity;

import com.framework.game.em.RoleTypeEnum;

/**
 * 角色
 * 
 * @author lxs
 *
 */
public class Role {
	/** 角色类型 */
	private RoleTypeEnum roleType;
	/** 位置 */
	private Vector3 position;

	/** 角色类型 */
	public RoleTypeEnum getRoleType() {
		return roleType;
	}

	/** 角色类型 */
	public void setRoleType(RoleTypeEnum roleType) {
		this.roleType = roleType;
	}

	/** 位置 */
	public Vector3 getPosition() {
		return position;
	}

	/** 位置 */
	public void setPosition(Vector3 position) {
		this.position = position;
	}

}
