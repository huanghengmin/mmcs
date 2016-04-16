package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.OrgcodeDao;
import com.hzih.mmcs.domain.Orgcode;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public class OrgcodeDaoImpl extends MyDaoSupport implements OrgcodeDao{
    @Override
    public void setEntityClass() {

    }


    @Override
    public List list(String hql)throws Exception {
        List list = null;
        try {
            list = getHibernateTemplate().find(hql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Orgcode findOrgcode(String code) throws Exception{
        String hql = new String("from Orgcode where orgcode ='"+code+"'");
        List list = null;
        try {
            list = getHibernateTemplate().find(hql);
        } catch (Exception e) {
            logger.error("findOrgcode查找错误",e);
        }
        Orgcode orgcode = (Orgcode) list.get(0);
        return orgcode;
    }
}
