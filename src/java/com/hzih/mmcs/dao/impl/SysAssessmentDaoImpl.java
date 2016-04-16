package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.SysAssessmentDao;
import com.hzih.mmcs.domain.SysAssessment;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-11
 * Time: 下午5:05
 * To change this template use File | Settings | File Templates.
 */
public class SysAssessmentDaoImpl  extends MyDaoSupport implements SysAssessmentDao{
    @Override
    public void setEntityClass() {
        this.entityClass = SysAssessment.class;
    }

    @Override
    public List<SysAssessment> findALL() throws Exception{
        String hql = new String(" from SysAssessment");
        List<SysAssessment> list =null;
        list = getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public List<SysAssessment> findLikeCode(String likeCode) throws Exception{
        String hql = new String("from SysAssessment where idsystem like '"+likeCode+"%' order by idsystem asc ");
        List<SysAssessment> sysAssessments = null;
        try {
            sysAssessments = super.getHibernateTemplate().find(hql);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return   sysAssessments;
    }

    @Override
    public List<SysAssessment> findLikeCode(String likeCode,String notLikeCode)throws Exception {
        String hql = new String("from SysAssessment where idsystem like '"+likeCode+"%' and idsystem not like '"+notLikeCode+"%' order by idsystem asc ");
        List<SysAssessment> sysAssessments = null;
        try {
            sysAssessments = super.getHibernateTemplate().find(hql);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return   sysAssessments;
    }
    
}
