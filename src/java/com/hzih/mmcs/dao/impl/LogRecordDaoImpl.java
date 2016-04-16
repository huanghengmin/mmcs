package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.utils.DateUtils;
import com.hzih.mmcs.dao.LogRecordDao;
import com.hzih.mmcs.domain.LogRecord;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-16
 * Time: 下午7:49
 * To change this template use File | Settings | File Templates.
 */
public class LogRecordDaoImpl extends MyDaoSupport implements LogRecordDao {
    @Override
    public void setEntityClass() {
        this.entityClass = LogRecord.class;
    }

    @Override
    public LogRecord findByUserNameAndLogTime(String userName, Date logTime) throws Exception {
        String hql = new String("from LogRecord where userName=? and date_format(logTime,'%Y-%m-%d') = date_format(?,'%Y-%m-%d')");
		List list = getHibernateTemplate().find(hql,
				new String[] { userName, DateUtils.format(logTime,"yyyy-MM-dd")});
		if (list != null && list.size() > 0) {
			return (LogRecord) list.get(0);
		} else {
			return null;
		}
    }
}
