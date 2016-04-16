package com.hzih.mmcs.domain;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-16
 * Time: 上午11:56
 * To change this template use File | Settings | File Templates.
 */
public class SysBizInf {
   private int id;// INT(11) NOT NULL AUTO_INCREMENT,
   private int idbiz;// INT(11) NULL DEFAULT NULL,
   private String idsystem;// VARCHAR(10) NULL DEFAULT NULL,
   private String bizMode;// VARCHAR(5) NULL DEFAULT NULL COMMENT '接入应用模式',
   private String bizType;// VARCHAR(10) NULL DEFAULT NULL COMMENT '接入应用类型',
   private String bizfunctype;// VARCHAR(30) NULL DEFAULT NULL COMMENT '接入应用形式,可以有多项，每项之间请用&分隔',
   private String bizTerminal;// VARCHAR(30) NULL DEFAULT NULL COMMENT '接入应用涉及的移动终端,可以有多项，每项之间请用&分隔',
   private String bizManagedepart;// VARCHAR(12) NULL DEFAULT NULL COMMENT '接入应用主管部门名称',
   private String managedepartManager;// VARCHAR(16) NULL DEFAULT NULL COMMENT '接入应用主管部门主管人姓名',
   private String managedepartPhone;// VARCHAR(30) NULL DEFAULT NULL COMMENT '接入应用主管部门主管人联系电话',
   private String managedepartMail;// VARCHAR(30) NULL DEFAULT NULL COMMENT '接入应用主管部门主管人公安网E-mail',
   private String managedepartLink;// VARCHAR(60) NULL DEFAULT NULL,
   private String approveUnit;// VARCHAR(12) NULL DEFAULT NULL COMMENT '审批单位',
   private Timestamp approveTime;// TIMESTAMP NULL DEFAULT NULL COMMENT '审批时间',
   private String approveNo;// VARCHAR(60) NULL DEFAULT NULL COMMENT '审批批号',
   private String approveMaterial;// VARCHAR(40) NULL DEFAULT NULL COMMENT '审批材料名称',
   private Timestamp registerTime;// TIMESTAMP NULL DEFAULT NULL,
   private String realtime;// VARCHAR(1) NULL DEFAULT NULL COMMENT '是否有实时性要求是1,否0',
   private String dateexchangeDateflux;// VARCHAR(10) NULL DEFAULT NULL,
   private String isBackup;// VARCHAR(1) NULL DEFAULT NULL COMMENT '是否已备案1是或0否',
   private String backupUnitname;// VARCHAR(12) NULL DEFAULT NULL,
   private String topologyMap;// VARCHAR(40) NULL DEFAULT NULL,
   private Timestamp collecttime;// TIMESTAMP NULL DEFAULT NULL COMMENT '统计时间',

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdbiz() {
        return idbiz;
    }

    public void setIdbiz(int idbiz) {
        this.idbiz = idbiz;
    }

    public String getIdsystem() {
        return idsystem;
    }

    public void setIdsystem(String idsystem) {
        this.idsystem = idsystem;
    }

    public String getBizMode() {
        return bizMode;
    }

    public void setBizMode(String bizMode) {
        this.bizMode = bizMode;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizfunctype() {
        return bizfunctype;
    }

    public void setBizfunctype(String bizfunctype) {
        this.bizfunctype = bizfunctype;
    }

    public String getBizTerminal() {
        return bizTerminal;
    }

    public void setBizTerminal(String bizTerminal) {
        this.bizTerminal = bizTerminal;
    }

    public String getBizManagedepart() {
        return bizManagedepart;
    }

    public void setBizManagedepart(String bizManagedepart) {
        this.bizManagedepart = bizManagedepart;
    }

    public String getManagedepartManager() {
        return managedepartManager;
    }

    public void setManagedepartManager(String managedepartManager) {
        this.managedepartManager = managedepartManager;
    }

    public String getManagedepartPhone() {
        return managedepartPhone;
    }

    public void setManagedepartPhone(String managedepartPhone) {
        this.managedepartPhone = managedepartPhone;
    }

    public String getManagedepartMail() {
        return managedepartMail;
    }

    public void setManagedepartMail(String managedepartMail) {
        this.managedepartMail = managedepartMail;
    }

    public String getManagedepartLink() {
        return managedepartLink;
    }

    public void setManagedepartLink(String managedepartLink) {
        this.managedepartLink = managedepartLink;
    }

    public String getApproveUnit() {
        return approveUnit;
    }

    public void setApproveUnit(String approveUnit) {
        this.approveUnit = approveUnit;
    }

    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveNo() {
        return approveNo;
    }

    public void setApproveNo(String approveNo) {
        this.approveNo = approveNo;
    }

    public String getApproveMaterial() {
        return approveMaterial;
    }

    public void setApproveMaterial(String approveMaterial) {
        this.approveMaterial = approveMaterial;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public String getRealtime() {
        return realtime;
    }

    public void setRealtime(String realtime) {
        this.realtime = realtime;
    }

    public String getDateexchangeDateflux() {
        return dateexchangeDateflux;
    }

    public void setDateexchangeDateflux(String dateexchangeDateflux) {
        this.dateexchangeDateflux = dateexchangeDateflux;
    }

    public String getBackup() {
        return isBackup;
    }

    public void setBackup(String backup) {
        isBackup = backup;
    }

    public String getBackupUnitname() {
        return backupUnitname;
    }

    public void setBackupUnitname(String backupUnitname) {
        this.backupUnitname = backupUnitname;
    }

    public String getTopologyMap() {
        return topologyMap;
    }

    public void setTopologyMap(String topologyMap) {
        this.topologyMap = topologyMap;
    }

    public Timestamp getCollecttime() {
        return collecttime;
    }

    public void setCollecttime(Timestamp collecttime) {
        this.collecttime = collecttime;
    }
}
