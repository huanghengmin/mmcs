package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.SysterminalInfoDao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-15
 * Time: 下午1:41
 * To change this template use File | Settings | File Templates.
 */
public class SysterminalInfoDaoImpl extends MyDaoSupport implements SysterminalInfoDao {
    @Override
    public List list(String hql) {
        List list = null;
        try {
            list = getHibernateTemplate().find(hql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public PageResult listByPage(int pageLength, int pageIndex, String idsystem) {
        String hql = "from Systerminalinfo where idsystem = '"+idsystem+"'";
        String countHql = "select count(*) " + hql;
        PageResult ps = this.findByPage(hql, countHql, pageIndex, pageLength);
        return ps;
    }


    @Override
    public void setEntityClass() {
        //To change body of implemented methods use File | Settings | File Templates.
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
}
