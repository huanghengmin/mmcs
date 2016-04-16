package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.InitSystemDao;
import com.hzih.mmcs.domain.InitSystem;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-14
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public class InitSystemDaoImpl extends MyDaoSupport implements InitSystemDao {
    @Override
    public void setEntityClass() {
        this.entityClass = InitSystem.class;
    }
}
