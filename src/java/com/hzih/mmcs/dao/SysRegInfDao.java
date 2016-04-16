package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;

public interface SysRegInfDao extends BaseDao {

    public PageResult findByIdSystem(int pageIndex, int limit, String idsystem)throws Exception;

    public String findSystemName(String idsystem)throws Exception;

    public String findSystemNameLmy(String idsystem)throws Exception;

    public int findBuildDepart(String builder) throws Exception;
}
