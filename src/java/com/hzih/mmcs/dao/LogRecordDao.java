package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import com.hzih.mmcs.domain.LogRecord;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-16
 * Time: 下午7:44
 * To change this template use File | Settings | File Templates.
 */
public interface LogRecordDao extends BaseDao {

    /**
     * 通过用户名查找当天的登录记录
     * @param userName
     * @param logTime
     * @return
     * @throws Exception
     */
    public LogRecord findByUserNameAndLogTime(String userName,Date logTime) throws Exception;

}
