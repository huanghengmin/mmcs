package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import com.hzih.mmcs.domain.Role;

public interface RoleDao extends BaseDao {

    public Role findByName(String name) throws Exception;
}
