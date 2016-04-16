package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.BizModeCodeDao;
import com.hzih.mmcs.domain.BizModeCode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午4:28
 * To change this template use File | Settings | File Templates.
 */
public class BizModeCodeDaoImpl extends MyDaoSupport implements BizModeCodeDao {
    @Override
    public void setEntityClass() {
        this.entityClass= BizModeCode.class;
    }
}
