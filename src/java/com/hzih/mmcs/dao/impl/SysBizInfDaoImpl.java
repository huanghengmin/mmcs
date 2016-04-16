package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.SysBizInfDao;
import com.hzih.mmcs.domain.SysBizInf;
import com.hzih.mmcs.utils.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SysBizInfDaoImpl extends MyDaoSupport implements SysBizInfDao {
	private final static Logger logger = Logger.getLogger(SysBizInfDaoImpl.class);

	@Override
	public void setEntityClass() {
		this.entityClass = SysBizInf.class;
	}

    @Override
    public PageResult findByIdSystem(int pageIndex, int limit, String idsystem) throws Exception{
        StringBuffer sb = new StringBuffer(" from SysBizInf s where 1=1");
        List params = new ArrayList(1);// 手动指定容量，避免多次扩容
        if (StringUtils.isNotBlank(idsystem)) {
            sb.append(" and idsystem = ?");
            params.add(idsystem);
        }
        String countString = "select count(*) " + sb.toString();

        String queryString = sb.toString();

        PageResult ps = this.findByPage(queryString, countString, params
                .toArray(), pageIndex, limit);
        logger.debug(ps == null ? "ps=null" : "ps.results.size:"
                + ps.getResults().size());
        return ps;
    }

}
