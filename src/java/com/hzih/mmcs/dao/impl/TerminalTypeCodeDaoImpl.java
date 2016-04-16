package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.TerminalTypeCodeDao;
import com.hzih.mmcs.domain.TerminalTypeCode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午5:44
 * To change this template use File | Settings | File Templates.
 */
public class TerminalTypeCodeDaoImpl extends MyDaoSupport implements TerminalTypeCodeDao {
    @Override
    public void setEntityClass() {
        this.entityClass= TerminalTypeCode.class;
    }
}
