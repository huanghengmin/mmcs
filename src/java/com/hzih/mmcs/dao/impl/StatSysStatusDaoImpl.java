package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.StatSysStatusDao;
import com.hzih.mmcs.domain.StatSysStatus;
import com.hzih.mmcs.utils.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatSysStatusDaoImpl extends MyDaoSupport implements StatSysStatusDao {
	private final static Logger logger = Logger.getLogger(StatSysStatusDaoImpl.class);

	@Override
	public void setEntityClass() {
		this.entityClass = StatSysStatus.class;
	}

	/**
	 * 分页查询SysLog
	 *
	 */
	public PageResult listLogsByParams(int pageIndex, int pageLength,
			Date startDate, Date endDate, String logLevel) throws Exception{
		StringBuffer sb = new StringBuffer(" from StatSysStatus s where 1=1");
		List params = new ArrayList(3);// 手动指定容量，避免多次扩容
		if(startDate!=null){
			sb.append(" and date_format(logTime,'%Y-%m-%d')>= date_format(?,'%Y-%m-%d')");
			params.add(startDate);
		}
		if(endDate!=null){
			sb.append(" and date_format(logTime,'%Y-%m-%d')<= date_format(?,'%Y-%m-%d')");
			params.add(endDate);
		}

		if (StringUtils.isNotBlank(logLevel)) {
			sb.append(" and level = ?");
			params.add(logLevel);
		}
		String countString = "select count(*) " + sb.toString();

		String queryString = sb.toString();

		PageResult ps = this.findByPage(queryString, countString, params
				.toArray(), pageIndex, pageLength);
		logger.debug(ps == null ? "ps=null" : "ps.results.size:"
				+ ps.getResults().size());
		return ps;
	}

    @Override
    public List list(String hql) throws Exception{
        return getHibernateTemplate().find(hql);
    }

}
