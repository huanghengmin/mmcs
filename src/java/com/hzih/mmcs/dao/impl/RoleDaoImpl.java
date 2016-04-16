package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.mmcs.dao.RoleDao;
import com.hzih.mmcs.domain.Role;

import java.util.List;

public class RoleDaoImpl extends MyDaoSupport implements RoleDao {

	@Override
	public void setEntityClass() {
		this.entityClass = Role.class;
	}

    /**
     * 根据角色名查找角色
     * @param name
     * @return
     * @throws Exception
     */
    public Role findByName(String name) throws Exception {
        String hql = new String("from Role where name = ?");
        List<Role> list = getHibernateTemplate().find(hql,new String[]{name});
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
