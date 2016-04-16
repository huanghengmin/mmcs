package com.hzih.mmcs.domain;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-16
 * Time: 上午11:56
 * To change this template use File | Settings | File Templates.
 */
public class SysOutLinkInf {
   private int id;// INT(11) NOT NULL AUTO_INCREMENT,
   private String idsystem;// VARCHAR(10) NULL DEFAULT NULL COMMENT '系统标识',
   private int idoutconnlink;// INT(11) NULL DEFAULT NULL COMMENT '外部链路标识',
   private String outlinkvender;// VARCHAR(32) NULL DEFAULT NULL COMMENT '外部链路提供商',
   private int outlinkbandwidth;// INT(11) NULL DEFAULT NULL,
   private String outlinkdisc;// VARCHAR(254) NULL DEFAULT NULL COMMENT '外部链路说明',
   private Timestamp collecttime;// TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '统计时间',

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdsystem() {
        return idsystem;
    }

    public void setIdsystem(String idsystem) {
        this.idsystem = idsystem;
    }

    public int getIdoutconnlink() {
        return idoutconnlink;
    }

    public void setIdoutconnlink(int idoutconnlink) {
        this.idoutconnlink = idoutconnlink;
    }

    public String getOutlinkvender() {
        return outlinkvender;
    }

    public void setOutlinkvender(String outlinkvender) {
        this.outlinkvender = outlinkvender;
    }

    public int getOutlinkbandwidth() {
        return outlinkbandwidth;
    }

    public void setOutlinkbandwidth(int outlinkbandwidth) {
        this.outlinkbandwidth = outlinkbandwidth;
    }

    public String getOutlinkdisc() {
        return outlinkdisc;
    }

    public void setOutlinkdisc(String outlinkdisc) {
        this.outlinkdisc = outlinkdisc;
    }

    public Timestamp getCollecttime() {
        return collecttime;
    }

    public void setCollecttime(Timestamp collecttime) {
        this.collecttime = collecttime;
    }
}
