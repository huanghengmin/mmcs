package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.OutLinkTypeCodeDao;
import com.hzih.mmcs.domain.OutLinkTypeCode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class OutLinkTypeCodeDaoImpl extends MyDaoSupport implements OutLinkTypeCodeDao {
    @Override
    public void setEntityClass() {
        this.entityClass= OutLinkTypeCode.class ;
    }
}
