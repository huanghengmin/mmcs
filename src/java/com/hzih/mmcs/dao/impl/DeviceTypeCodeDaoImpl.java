package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.DeviceTypeCodeDao;
import com.hzih.mmcs.domain.DeviceTypeCode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午6:08
 * To change this template use File | Settings | File Templates.
 */
public class DeviceTypeCodeDaoImpl extends MyDaoSupport implements DeviceTypeCodeDao {
    @Override
    public void setEntityClass() {
        this.entityClass= DeviceTypeCode.class;
    }
}
