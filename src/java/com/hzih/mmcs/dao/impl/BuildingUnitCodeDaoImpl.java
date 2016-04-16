package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.BuildingUnitCodeDao;
import com.hzih.mmcs.domain.BuildingUnitCode;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-18
 * Time: 下午12:19
 * To change this template use File | Settings | File Templates.
 */
public class BuildingUnitCodeDaoImpl extends MyDaoSupport implements BuildingUnitCodeDao{
    @Override
    public void setEntityClass() {

        this.entityClass= BuildingUnitCode.class;
    }
}
