package com.hzih.mmcs.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-10
 * Time: 下午8:21
 * To change this template use File | Settings | File Templates.
 */
public class Sysabnormal {
    private long id;
    private String idsystem;
    private Long idalertmatter;
    private String abnormaltypecode;
    private String connectobjectcode;
    private String exceptiondesc;
    private Date happentime;
    private Date treattime;
    private String treadresult;
    private String ifcheckin;
    private String abProcessId;

    public Sysabnormal() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdsystem() {
        return idsystem;
    }

    public void setIdsystem(String idsystem) {
        this.idsystem = idsystem;
    }

    public Long getIdalertmatter() {
        return idalertmatter;
    }

    public void setIdalertmatter(Long idalertmatter) {
        this.idalertmatter = idalertmatter;
    }

    public String getAbnormaltypecode() {
        return abnormaltypecode;
    }

    public void setAbnormaltypecode(String abnormaltypecode) {
        this.abnormaltypecode = abnormaltypecode;
    }

    public String getConnectobjectcode() {
        return connectobjectcode;
    }

    public void setConnectobjectcode(String connectobjectcode) {
        this.connectobjectcode = connectobjectcode;
    }

    public String getExceptiondesc() {
        return exceptiondesc;
    }

    public void setExceptiondesc(String exceptiondesc) {
        this.exceptiondesc = exceptiondesc;
    }

    public Date getHappentime() {
        return happentime;
    }

    public void setHappentime(Date happentime) {
        this.happentime = happentime;
    }

    public Date getTreattime() {
        return treattime;
    }

    public void setTreattime(Date treattime) {
        this.treattime = treattime;
    }

    public String getTreadresult() {
        return treadresult;
    }

    public void setTreadresult(String treadresult) {
        this.treadresult = treadresult;
    }

    public String getIfcheckin() {
        return ifcheckin;
    }

    public void setIfcheckin(String ifcheckin) {
        this.ifcheckin = ifcheckin;
    }

    public String getAbProcessId() {
        return abProcessId;
    }

    public void setAbProcessId(String abProcessId) {
        this.abProcessId = abProcessId;
    }
}
