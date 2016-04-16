package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.BizFuncTypeCodeDao;
import com.hzih.mmcs.domain.BizFuncTypeCode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 */
public class BizFuncTypeCodeDaoImpl extends MyDaoSupport implements BizFuncTypeCodeDao {
    @Override
    public void setEntityClass() {
        this.entityClass= BizFuncTypeCode.class;
    }
}
