package com.hzih.mmcs.service;



public interface StatSysStatusService {

    public String selectAll(String zonecode)throws Exception;
    public String findStatSysStatus(String zonecode)throws Exception;
    public String findCityCode(String code)throws Exception;
    public String findCountyCode(String code)throws Exception;
}
