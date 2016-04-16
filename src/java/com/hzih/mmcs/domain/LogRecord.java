package com.hzih.mmcs.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-16
 * Time: 下午6:59
 * 登录情况日志
 */
public class LogRecord {
    private long id;
    /**
     * 登陆用户名
     */
    private String userName;
    /**
     * 登陆成功失败消息
     */
    private String info;
    /**
     * 登陆时间
     */
    private Date logTime;

    /**
     * 当天登录次数
     */
    private int count;

    /**
     * 系统标识
     */
    private String idSystem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getIdSystem() {
        return idSystem;
    }

    public void setIdSystem(String idSystem) {
        this.idSystem = idSystem;
    }
}
