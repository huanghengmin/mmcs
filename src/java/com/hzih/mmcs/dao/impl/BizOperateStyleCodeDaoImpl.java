package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.BizOperateStyleCodeDao;
import com.hzih.mmcs.domain.BizOperateStyleCode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
public class BizOperateStyleCodeDaoImpl extends MyDaoSupport implements BizOperateStyleCodeDao {
    @Override
    public void setEntityClass() {
        this.entityClass= BizOperateStyleCode.class;
    }
}
