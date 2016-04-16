package com.hzih.mmcs.vo;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-11
 * Time: 上午10:38
 * To change this template use File | Settings | File Templates.
 */
public class AbnormalVo {
    private long id;
    private long idalertmatter;
    private String abnormaltype;
    private String abnormalobject;
    private String exceptiondesc;
    private Date happentime;
    private Date treattime;
    private String treadresult;
    private String ifcheckin;

    public AbnormalVo() {
    }

    public AbnormalVo(long id, long idalertmatter, String abnormaltype, String abnormalobject, String exceptiondesc, Date happentime, Date treattime, String treadresult, String ifcheckin) {
        this.id = id;
        this.idalertmatter = idalertmatter;
        this.abnormaltype = abnormaltype;
        this.abnormalobject = abnormalobject;
        this.exceptiondesc = exceptiondesc;
        this.happentime = happentime;
        this.treattime = treattime;
        this.treadresult = treadresult;
        this.ifcheckin = ifcheckin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdalertmatter() {
        return idalertmatter;
    }

    public void setIdalertmatter(long idalertmatter) {
        this.idalertmatter = idalertmatter;
    }

    public String getAbnormaltype() {
        return abnormaltype;
    }

    public void setAbnormaltype(String abnormaltype) {
        this.abnormaltype = abnormaltype;
    }

    public String getAbnormalobject() {
        return abnormalobject;
    }

    public void setAbnormalobject(String abnormalobject) {
        this.abnormalobject = abnormalobject;
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
}
