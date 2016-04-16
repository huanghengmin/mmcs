package com.hzih.mmcs.service;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-11
 * Time: 上午11:34
 * To change this template use File | Settings | File Templates.
 */
public interface SysabnormalService {

    public String findAbnormaVos(String idsystem, int start, int limit) throws Exception;
    public String findAbnormaNum(String idsystem)throws Exception;
    public String findAbnormaNumForCity(String idsystem)throws Exception;
    public String findAbnormaNumForCounty(String idsystem)throws Exception;
    public String findAbnormaVosByTimeAndTreadresult(String idsystem, int start, int limit,String timetype, String treadresult) throws Exception;
}
