package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;

import java.util.Date;

public interface SysDeviceInfDao extends BaseDao {
	/**
	 * 分页查询SysLog
	 *
	 */
    public PageResult findByIdSystem(int pageIndex, int limit, String idsystem)throws Exception;

}
