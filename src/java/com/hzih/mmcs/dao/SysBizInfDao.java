package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;

import java.util.Date;

public interface SysBizInfDao extends BaseDao {

    public PageResult findByIdSystem(int pageIndex, int limit, String idsystem)throws Exception;

}
