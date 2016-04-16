package com.hzih.mmcs.service;

import com.hzih.mmcs.domain.LogRecord;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-16
 * Time: 下午7:29
 * To change this template use File | Settings | File Templates.
 */
public interface LogRecordService {

    /**
     * 当天没有记录则新增,有则更新时间
     *
     *
     * @param logRecord
     * @param systemId
     * @throws Exception
     */
    public void newOrUpdateLog(String logRecord, String systemId) throws Exception;
}
