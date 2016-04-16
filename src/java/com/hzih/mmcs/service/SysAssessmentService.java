package com.hzih.mmcs.service;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-16
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */
public interface SysAssessmentService {
    public String findSysAssessment(String code) throws Exception;

    public String findSingleAssessment(String code) throws Exception;
}
