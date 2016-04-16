package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import com.hzih.mmcs.domain.District;

import java.util.List;

public interface DistrictDao extends BaseDao {
	
	List<District> getAllParents() throws Exception;
	
	List<District> findChildByParent(Long parentId) throws Exception;
	
	List<District> findChildFirst() throws Exception;
	
	District findById(Long id) throws Exception;
    public List list(String hql) throws Exception ;
}
