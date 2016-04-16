package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.AccountDao;
import com.hzih.mmcs.domain.Account;
import com.hzih.mmcs.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl extends MyDaoSupport implements AccountDao {

	@Override
	public void setEntityClass() {
		this.entityClass = Account.class;
	}

    /**
     * 分页查询用户列表
     * @param startDate
     * @param endDate
     * @param userName
     * @param status
     * @param pageIndex
     * @param limit
     * @return
     */
	public PageResult listByPage(String startDate,String endDate,String userName, String status, int pageIndex, int limit) throws Exception{
		String hql = "from Account where 1=1 ";
		List paramsList = new ArrayList(4);
        if(startDate!=null){
			hql += " and date_format(registerTime,'%Y-%m-%d')>= date_format(?,'%Y-%m-%d')";
			paramsList.add(startDate);
		}
		if(endDate!=null){
			hql += " and date_format(registerTime,'%Y-%m-%d')<= date_format(?,'%Y-%m-%d')";
			paramsList.add(endDate);
		}
		if (StringUtils.isNotBlank(userName)) {
			hql += " and userName like ?";
			paramsList.add("%" + userName + "%");
		}
		if (StringUtils.isNotBlank(status)
				&& !status.equalsIgnoreCase("全部")) {
			hql += " and status=?";
			paramsList.add(status);
		}
		String countHql = "select count(*) " + hql;

		PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
				pageIndex, limit);
		return ps;
	}

    /**
     * 通过用户名、密码和状态查找用户
     * @param name
     * @param pwd
     * @return
     */
	public Account findByNameAndPwd(String name, String pwd)throws Exception {
		String hql = new String("from Account where userName=? and password=? and status=?");
		List list = getHibernateTemplate().find(hql,
				new String[] { name, pwd, "有效" });
		if (list != null && list.size() > 0) {
			return (Account) list.get(0);
		} else {
			return null;
		}
	}

    /**
     * 通过用户名查找用户
     * @param userName
     * @return
     */
    public Account findByName(String userName)throws Exception {
        String hql = new String("from Account where userName=?");
		List<Account> list = getHibernateTemplate().find(hql,new String[] { userName });
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
    }

}
