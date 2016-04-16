package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.domain.Account;

public interface AccountDao extends BaseDao {

    /**
     * 分页查询
     * @param startDate
     * @param endDate
     * @param userName
     * @param status
     * @param pageIndex
     * @param limit
     * @return
     */
	PageResult listByPage(String startDate,String endDate,String userName, String status, int pageIndex, int limit)throws Exception;

	Account findByNameAndPwd(String name, String pwd)throws Exception;

    Account findByName(String userName) throws Exception;
}
