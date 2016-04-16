package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-15
 * Time: 下午1:40
 * To change this template use File | Settings | File Templates.
 */
public interface SysterminalInfoDao extends BaseDao{
    public List list(String hql);

    PageResult listByPage(int pageIndex, int pageLength, String idsystem);
}
