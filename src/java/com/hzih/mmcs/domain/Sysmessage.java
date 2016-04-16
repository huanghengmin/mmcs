package com.hzih.mmcs.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-10
 * Time: 上午10:37
 * To change this template use File | Settings | File Templates.
 */
public class Sysmessage {
    private int id;
    private String msgName;
    private String msgType;
    private long idalertmatter;
    private long msgAdminId;
    private String idsystem;
    private String ifchecked;
    private Date sendTime;
    private Date checkedTime;

    public Sysmessage() {
    }

    public Sysmessage(String msgName, String msgType, long idalertmatter, long msgAdminId, String idsystem, String ifchecked, Date sendTime, Date checkedTime) {
        this.msgName = msgName;
        this.msgType = msgType;
        this.idalertmatter = idalertmatter;
        this.msgAdminId = msgAdminId;
        this.idsystem = idsystem;
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

    public long getIdalertmatter() {
        return idalertmatter;
    }

    public void setIdalertmatter(long idalertmatter) {
        this.idalertmatter = idalertmatter;
    }

    public long getMsgAdminId() {
        return msgAdminId;
    }

    public void setMsgAdminId(long msgAdminId) {
        this.msgAdminId = msgAdminId;
    }

    public String getIdsystem() {
        return idsystem;
    }

    public void setIdsystem(String idsystem) {
        this.idsystem = idsystem;
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
