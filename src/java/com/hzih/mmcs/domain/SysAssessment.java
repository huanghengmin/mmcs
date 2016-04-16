package com.hzih.mmcs.domain;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-11
 * Time: 下午4:54
 * To change this template use File | Settings | File Templates.
 */
public class SysAssessment {
    private int id;
    private String  idsystem;
    private Double   regscore;
    private Double   abnscore;
    private Double   logscore;
    private Double   resscore;
    private Double   actscore;
    private Double   sumscore;
    private Timestamp updatetime;

    public SysAssessment() {
    }

    public SysAssessment(int id, String idsystem, Double regscore, Double abnscore, Double logscore, Double resscore, Double actscore, Double sumscore, Timestamp updatetime) {
        this.id = id;
        this.idsystem = idsystem;
        this.regscore = regscore;
        this.abnscore = abnscore;
        this.logscore = logscore;
        this.resscore = resscore;
        this.actscore = actscore;
        this.sumscore = sumscore;
        this.updatetime = updatetime;
    }

    public SysAssessment(String idsystem, Double regscore, Double abnscore, Double logscore, Double resscore, Double actscore, Double sumscore) {
        this.idsystem = idsystem;
        this.regscore = regscore;
        this.abnscore = abnscore;
        this.logscore = logscore;
        this.resscore = resscore;
        this.actscore = actscore;
        this.sumscore = sumscore;
    }

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

    public Double getRegscore() {
        return regscore;
    }

    public void setRegscore(Double regscore) {
        this.regscore = regscore;
    }

    public Double getAbnscore() {
        return abnscore;
    }

    public void setAbnscore(Double abnscore) {
        this.abnscore = abnscore;
    }

    public Double getLogscore() {
        return logscore;
    }

    public void setLogscore(Double logscore) {
        this.logscore = logscore;
    }

    public Double getResscore() {
        return resscore;
    }

    public void setResscore(Double resscore) {
        this.resscore = resscore;
    }

    public Double getActscore() {
        return actscore;
    }

    public void setActscore(Double actscore) {
        this.actscore = actscore;
    }

    public Double getSumscore() {
        return sumscore;
    }

    public void setSumscore(Double sumscore) {
        this.sumscore = sumscore;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }
}
