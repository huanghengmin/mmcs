package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import com.hzih.mmcs.domain.Orgcode;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public interface OrgcodeDao extends BaseDao{
    public List list(String hql)throws Exception;
    
    public Orgcode findOrgcode(String code)throws Exception;
}
