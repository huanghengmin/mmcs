package com.hzih.mmcs.domain;

import java.util.Date;
import java.util.Set;

/**
 * 角色
 * 
 * @author collin.code@gmail.com
 * @hibernate.class table="role"
 */
public class Role {
	/**
	 * @hibernate.id column="id" generator-class="increment" type="long"
	 */
	Long id;

	/**
	 * @hibernate.property name="name" type="string" length="30"
	 */
	String name;

	/**
	 * @hibernate.property name="desc" type="string" length="50"
	 */
	String description;

	/**
	 * @hibernate.property name="created_time" type="java.util.Date"
	 */
	Date createdTime;

    /**
     * @hibnate.prperty name="type" type="java.lang.Integer"
     * 1:管理员
     * 2.普通用户
     */
    int type;

	/**
	 * @hibernate.property name="modified_time" type="java.util.Date"
	 */
	Date modifiedTime;

	/**
	 * 权限
	 * 
	 * @hibernate.set cascade="save-update" table="role_permission" lazy="false"
	 * @hibernate.key column="role_id"
	 * @hibernate.many-to-many column="permission_id"
	 *                         class="com.hzjava.monitorcenter.domain.Permission"
	 */
	Set<Permission> permissions;

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Role() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
