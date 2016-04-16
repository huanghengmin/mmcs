package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-9
 * Time: 下午7:31
 * To change this template use File | Settings | File Templates.
 */
public interface StatSysterminalinfoDao extends BaseDao{
    public List list(String hql)throws Exception;


}
