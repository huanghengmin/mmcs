package com.hzih.mmcs.domain;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-11
 * Time: 下午5:33
 * To change this template use File | Settings | File Templates.
 */
public class StatSysStatus {
    private int id;
	private String idsystem;
    private Long bizsum;
	private Long access;
	private Long terminalnum;
	private Long influx;
	private Long outflux;
	private Long accesssum;
	private Long monthaccess;
	private Long monthterminalnum;
	private Long monthinflux;
	private Long monthoutflux;
	private String sysrunpresent;
	private String ifprovincesum;
	private String ifinit;
	private Timestamp updatetime;

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

    public Long getBizsum() {
        return bizsum;
    }

    public void setBizsum(Long bizsum) {
        this.bizsum = bizsum;
    }

    public Long getAccess() {
        return access;
    }

    public void setAccess(Long access) {
        this.access = access;
    }

    public Long getTerminalnum() {
        return terminalnum;
    }

    public void setTerminalnum(Long terminalnum) {
        this.terminalnum = terminalnum;
    }

    public Long getInflux() {
        return influx;
    }

    public void setInflux(Long influx) {
        this.influx = influx;
    }

    public Long getOutflux() {
        return outflux;
    }

    public void setOutflux(Long outflux) {
        this.outflux = outflux;
    }

    public Long getAccesssum() {
        return accesssum;
    }

    public void setAccesssum(Long accesssum) {
        this.accesssum = accesssum;
    }

    public Long getMonthaccess() {
        return monthaccess;
    }

    public void setMonthaccess(Long monthaccess) {
        this.monthaccess = monthaccess;
    }

    public Long getMonthterminalnum() {
        return monthterminalnum;
    }

    public void setMonthterminalnum(Long monthterminalnum) {
        this.monthterminalnum = monthterminalnum;
    }

    public Long getMonthinflux() {
        return monthinflux;
    }

    public void setMonthinflux(Long monthinflux) {
        this.monthinflux = monthinflux;
    }

    public Long getMonthoutflux() {
        return monthoutflux;
    }

    public void setMonthoutflux(Long monthoutflux) {
        this.monthoutflux = monthoutflux;
    }

    public String getSysrunpresent() {
        return sysrunpresent;
    }

    public void setSysrunpresent(String sysrunpresent) {
        this.sysrunpresent = sysrunpresent;
    }

    public String getIfprovincesum() {
        return ifprovincesum;
    }

    public void setIfprovincesum(String ifprovincesum) {
        this.ifprovincesum = ifprovincesum;
    }

    public String getIfinit() {
        return ifinit;
    }

    public void setIfinit(String ifinit) {
        this.ifinit = ifinit;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }
}
