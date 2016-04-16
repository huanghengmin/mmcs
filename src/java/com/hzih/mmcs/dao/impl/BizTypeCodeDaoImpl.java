package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.BizTypeCodeDao;
import com.hzih.mmcs.domain.BizTypeCode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
public class BizTypeCodeDaoImpl extends MyDaoSupport implements BizTypeCodeDao {
    @Override
    public void setEntityClass() {
        this.entityClass=  BizTypeCode.class;
    }
}
