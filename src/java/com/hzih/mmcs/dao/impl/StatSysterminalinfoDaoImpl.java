package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.StatSysterminalinfoDao;
import com.hzih.mmcs.domain.StatSysterminalinfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-9
 * Time: 下午7:35
 * To change this template use File | Settings | File Templates.
 */
public class StatSysterminalinfoDaoImpl extends MyDaoSupport implements StatSysterminalinfoDao {
    @Override
    public void setEntityClass() {
        this.entityClass= StatSysterminalinfo.class;
    }

    @Override
    public Object retrieveById(Serializable serializable) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object retrieveById(Class aClass, Serializable serializable) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getById(Serializable serializable) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getById(Class aClass, Serializable serializable) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void create(Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Serializable serializable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Class aClass, Serializable serializable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteWithIndependent(String[] strings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteWithIndependent(String s, String[] strings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteWithDependent(String[] strings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteWithDependent(Class aClass, String[] strings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List list(String hql) throws Exception{
        List list = null;
        try {
            list = getHibernateTemplate().find(hql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
