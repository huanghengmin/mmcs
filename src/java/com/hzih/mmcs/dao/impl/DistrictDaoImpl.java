package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.DistrictDao;
import com.hzih.mmcs.domain.District;

import java.util.List;

public class DistrictDaoImpl extends MyDaoSupport implements DistrictDao {

	@Override
	public void setEntityClass() {
		this.entityClass = District.class;
	}

	@Override
	public List<District> findChildByParent(Long parentId)throws Exception {
		Long nextId = parentId+10000;
		String hql = new String("from District where id>="+parentId+" and id<"+nextId);
		List<District> list = super.getHibernateTemplate().find(hql);
		return list;
	}
	
	public List<District> findChildFirst()throws Exception {
		String hql = new String("from District where id=010101");
		List<District> list = super.getHibernateTemplate().find(hql);
		return list;
	}
	

	@Override
	public List<District> getAllParents() throws Exception{
		String hql = new String("from District where mod(id,10000)=0");
		List<District> list = super.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public District findById(Long id) throws Exception{
		String hql = new String("from District where id = "+id);
		List<District> list = super.getHibernateTemplate().find(hql);
		return list.get(0);
	}
    @Override
    public List list(String hql)throws Exception {

        return getHibernateTemplate().find(hql);
    }
}
