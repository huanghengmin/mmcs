package com.hzih.mmcs.domain;

import java.util.Date;
import java.util.Set;

/**
 * 帐号，用户
 * 
 * @author collin.code@gmail.com
 * @hibernate.class table="account"
 */
public class Account {
	/**
	 * @hibernate.id column="id" generator-class="increment" type="long"
	 *               length="11"
	 */
	Long id;

	/**
	 * 用户名
	 * 
	 * @hibernate.property column="user_name" type="string" length="20"
	 */
	String userName;

    /**
     * 身份证号
     */
    String userId;

	/**
	 * 角色
	 * 
	 * @hibernate.set cascade="save-update" table="account_role" lazy="false"
	 * @hibernate.key column="account_id"
	 * @hibernate.many-to-many column="role_id"
	 *                         class="com.hzjava.monitorcenter.domain.Role"
	 */
	Set<Role> roles;

	/**
	 * 电话
	 * 
	 * @hibernate.property column="phone" type="string" length="20"
	 */
	String phone;

    /**
	 * 手机
	 *
	 * @hibernate.property column="mobile_phone" type="string" length="20"
	 */
	String mobilePhone;

	/**
	 * 创建时间
	 * 
	 * @hibernate.property column="created_time" type="java.util.Date"
	 */
	Date registerTime;

	/**
	 * 更新时间
	 * 
	 * @hibernate.property column="modified_time" type="java.util.Date"
	 */
	Date modifiedTime;

	/**
	 * 0:未通过审核，1:通过审核
	 * 
	 * @hibernate.property column="status" type="string" length="1"
	 */
	String status;

	/**
	 * 邮箱
	 * 
	 * @hibernate.property column="email" type="string" length="30"
	 */
	String email;

    /**
     * 地区编号
     */
    String districtCode;

    /**
     * 下级平台编号
     */
    String systemId;

    /**
     * 部门
     */
    String department;

    /**
     * 单位
     */
    String orgCode;

	/**
	 * 权限，当用户登录的时候初始化
	 */
	Set<Permission> permissions;
	
	public Account() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
