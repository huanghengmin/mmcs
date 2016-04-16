package com.hzih.mmcs.domain;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-16
 * Time: 上午11:56
 * To change this template use File | Settings | File Templates.
 */
public class SysDeviceInf {
   private int id;// INT(11) NOT NULL AUTO_INCREMENT,
   private String idsystem;// VARCHAR(10) NULL DEFAULT NULL,
   private int iddevice;// INT(11) NULL DEFAULT NULL COMMENT '设备标识',
   private String devicedesc;// VARCHAR(60) NULL DEFAULT NULL,
   private String devicetypecode;// VARCHAR(4) NULL DEFAULT NULL,
   private String deviceIP;// VARCHAR(128) NULL DEFAULT NULL,
   private String brandtyoe;// VARCHAR(128) NULL DEFAULT NULL COMMENT '生产厂家名称/型号',
   private Timestamp collecttime;// TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

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

    public int getIddevice() {
        return iddevice;
    }

    public void setIddevice(int iddevice) {
        this.iddevice = iddevice;
    }

    public String getDevicedesc() {
        return devicedesc;
    }

    public void setDevicedesc(String devicedesc) {
        this.devicedesc = devicedesc;
    }

    public String getDevicetypecode() {
        return devicetypecode;
    }

    public void setDevicetypecode(String devicetypecode) {
        this.devicetypecode = devicetypecode;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public String getBrandtyoe() {
        return brandtyoe;
    }

    public void setBrandtyoe(String brandtyoe) {
        this.brandtyoe = brandtyoe;
    }

    public Timestamp getCollecttime() {
        return collecttime;
    }

    public void setCollecttime(Timestamp collecttime) {
        this.collecttime = collecttime;
    }
}
