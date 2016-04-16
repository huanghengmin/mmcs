package com.hzih.mmcs.vo;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-10
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class MessageUserVo {
    private int id;
    private String msgName;
    private String msgType;
    private String username;
    private String unit;
    private String roleids;
    private String ifchecked;
    private Date sendTime;
    private Date checkedTime;

    public MessageUserVo() {
    }

    public MessageUserVo(int id, String msgName, String msgType, String username, String unit, String roleids, String ifchecked, Date sendTime, Date checkedTime) {
        this.id = id;
        this.msgName = msgName;
        this.msgType = msgType;
        this.username = username;
        this.unit = unit;
        this.roleids = roleids;
        this.ifchecked = ifchecked;
        this.sendTime = sendTime;
        this.checkedTime = checkedTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsgName() {
        return msgName;
    }

    public void setMsgName(String msgName) {
        this.msgName = msgName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRoleids() {
        return roleids;
    }

    public void setRoleids(String roleids) {
        this.roleids = roleids;
    }

    public String getIfchecked() {
        return ifchecked;
    }

    public void setIfchecked(String ifchecked) {
        this.ifchecked = ifchecked;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
    }
}
