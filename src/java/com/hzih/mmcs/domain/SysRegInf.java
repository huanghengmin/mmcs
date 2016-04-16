package com.hzih.mmcs.domain;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-15
 * Time: 下午2:41
 * To change this template use File | Settings | File Templates.
 */
public class SysRegInf {
   private int id;
   private String systemname; //     '系统名称',
   private String idsystem; //     '系统标识',
   private String systemclass; //'系统类型',
   private String address; //    '物理位置信息',
   private String manager; //    '系统管理员姓名',
   private String managerPhone; //  '系统管理员联系电话',
   private String managerMail; //  '系统管理员公安网邮箱地址',
   private String managerOtherlink; //    '系统管理员其他联系方式',
   private String remoteaccessIp; //     '本监控系统公安网IP地址',
   private String constructUnit; //   '建设单位',
   private String buildingUnitCode; //     '主要承建单位代码',
   private Timestamp buildingTime; //TIMESTAMP     '建设时间',
   private String signSecrecyprotocol; //     '是否签署保密协议是1,否0',
   private String approveUnit; //   '审批单位',
   private Timestamp approveTime;// TIMESTAMP     '审批时间',
   private String approveNo; //     '审批批号',
   private String approveMaterial; //   '审批材料名称',
   private String securityProject; //   '安全技术方案',
   private String secrecyProtocol; //   '保密协议',
   private String maintainUnit; //   '运维单位名称',
   private String maintainManager; //     '上级主管姓名',
   private String maintainManagerPhone; //  '上级主管联系电话',
   private String maintainManagerMail; //  '上级主管公安网邮箱地址',
   private String maintainManagerLink; //上级主管其他联系方式',
   private String systemMap; //   '系统拓扑图',
   private Timestamp collecttime;// '统计时间',
   private String ifpassed; //是否已审批通过；通过1，未通过0',

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSystemname() {
        return systemname;
    }

    public void setSystemname(String systemname) {
        this.systemname = systemname;
    }

    public String getIdsystem() {
        return idsystem;
    }

    public void setIdsystem(String idsystem) {
        this.idsystem = idsystem;
    }

    public String getSystemclass() {
        return systemclass;
    }

    public void setSystemclass(String systemclass) {
        this.systemclass = systemclass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getManagerMail() {
        return managerMail;
    }

    public void setManagerMail(String managerMail) {
        this.managerMail = managerMail;
    }

    public String getManagerOtherlink() {
        return managerOtherlink;
    }

    public void setManagerOtherlink(String managerOtherlink) {
        this.managerOtherlink = managerOtherlink;
    }

    public String getRemoteaccessIp() {
        return remoteaccessIp;
    }

    public void setRemoteaccessIp(String remoteaccessIp) {
        this.remoteaccessIp = remoteaccessIp;
    }

    public String getConstructUnit() {
        return constructUnit;
    }

    public void setConstructUnit(String constructUnit) {
        this.constructUnit = constructUnit;
    }

    public String getBuildingUnitCode() {
        return buildingUnitCode;
    }

    public void setBuildingUnitCode(String buildingUnitCode) {
        this.buildingUnitCode = buildingUnitCode;
    }

    public Timestamp getBuildingTime() {
        return buildingTime;
    }

    public void setBuildingTime(Timestamp buildingTime) {
        this.buildingTime = buildingTime;
    }

    public String getSignSecrecyprotocol() {
        return signSecrecyprotocol;
    }

    public void setSignSecrecyprotocol(String signSecrecyprotocol) {
        this.signSecrecyprotocol = signSecrecyprotocol;
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

    public String getSecurityProject() {
        return securityProject;
    }

    public void setSecurityProject(String securityProject) {
        this.securityProject = securityProject;
    }

    public String getSecrecyProtocol() {
        return secrecyProtocol;
    }

    public void setSecrecyProtocol(String secrecyProtocol) {
        this.secrecyProtocol = secrecyProtocol;
    }

    public String getMaintainUnit() {
        return maintainUnit;
    }

    public void setMaintainUnit(String maintainUnit) {
        this.maintainUnit = maintainUnit;
    }

    public String getMaintainManager() {
        return maintainManager;
    }

    public void setMaintainManager(String maintainManager) {
        this.maintainManager = maintainManager;
    }

    public String getMaintainManagerPhone() {
        return maintainManagerPhone;
    }

    public void setMaintainManagerPhone(String maintainManagerPhone) {
        this.maintainManagerPhone = maintainManagerPhone;
    }

    public String getMaintainManagerMail() {
        return maintainManagerMail;
    }

    public void setMaintainManagerMail(String maintainManagerMail) {
        this.maintainManagerMail = maintainManagerMail;
    }

    public String getMaintainManagerLink() {
        return maintainManagerLink;
    }

    public void setMaintainManagerLink(String maintainManagerLink) {
        this.maintainManagerLink = maintainManagerLink;
    }

    public String getSystemMap() {
        return systemMap;
    }

    public void setSystemMap(String systemMap) {
        this.systemMap = systemMap;
    }

    public Timestamp getCollecttime() {
        return collecttime;
    }

    public void setCollecttime(Timestamp collecttime) {
        this.collecttime = collecttime;
    }

    public String getIfpassed() {
        return ifpassed;
    }

    public void setIfpassed(String ifpassed) {
        this.ifpassed = ifpassed;
    }
}
