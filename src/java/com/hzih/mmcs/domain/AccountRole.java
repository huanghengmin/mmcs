package com.hzih.mmcs.domain;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-19
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */
public class AccountRole {
    private long accountId;
    private long roleId;

    public AccountRole() {
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
