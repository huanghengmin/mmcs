package com.hzih.mmcs.service;


public interface SysRegInfService {

    public String findByIdSystem(int pageIndex, int limit, String idsystem)throws Exception;

    public String finbBuildDepart() throws Exception;
}
