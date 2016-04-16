package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;

import java.util.Date;
import java.util.List;

public interface StatSysStatusDao extends BaseDao {
	/**
	 * 分页查询SysLog
	 *
	 */
	public PageResult listLogsByParams(int pageIndex, int pageLength,
                                       Date startDate, Date endDate, String logLevel) throws Exception;
    public List list(String hql) throws Exception;
}
