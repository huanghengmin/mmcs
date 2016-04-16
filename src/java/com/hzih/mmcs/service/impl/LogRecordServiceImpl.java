package com.hzih.mmcs.service.impl;

import com.hzih.mmcs.dao.LogRecordDao;
import com.hzih.mmcs.domain.LogRecord;
import com.hzih.mmcs.service.LogRecordService;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-16
 * Time: 下午7:44
 * To change this template use File | Settings | File Templates.
 */
public class LogRecordServiceImpl implements LogRecordService {
    private LogRecordDao logRecordDao;

    public void setLogRecordDao(LogRecordDao logRecordDao) {
        this.logRecordDao = logRecordDao;
    }

    @Override
    public void newOrUpdateLog(String userName,String idSystem) throws Exception {
        LogRecord logRecord = new LogRecord();
        logRecord.setLogTime(new Date());
        logRecord.setInfo("登录成功");
        logRecord.setUserName(userName);
        LogRecord l = logRecordDao.findByUserNameAndLogTime(userName,logRecord.getLogTime());
        if(l!=null) {
            l.setCount(l.getCount()+1);
            l.setLogTime(logRecord.getLogTime());
            logRecordDao.update(l);
        } else {
            logRecord.setIdSystem(idSystem);
            logRecord.setCount(1);
            logRecordDao.create(logRecord);
        }

    }
}
