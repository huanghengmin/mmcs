package com.hzih.mmcs.service;

import com.hzih.mmcs.domain.Account;

/**
 * Created by IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 12-6-11
 * Time: 下午1:02
 * To change this template use File | Settings | File Templates.
 */
public interface AccountService {

    public String select(String startDate,String endDate,
                         String userName, String status, int start, int limit) throws Exception;

    public String update(Account account, long[] rIds) throws Exception;

    public String delete(String[] id) throws Exception;

    public String insert(Account account, long[] rIds) throws Exception;

    public String checkUserName(String userName) throws Exception;

    public String selectUserNameKeyValue() throws Exception;

    /**
     * 组织部门名成为一组 {key:'',value:''}  列表
     * @return
     * @throws Exception
     */
    public String selectDepartmentNameKeyValue() throws Exception;

    /**
     * 通过userName字段查找Account对象及其关联的role等
     * @param userName   查找条件参数
     * @return
     * @throws Exception
     */
    public Account queryByUserName(String userName) throws Exception;

    /**
     * 通过userName字段查找权限并按一定格式返回数据
     * @param userName
     * @return
     * @throws Exception
     */
    public String queryPermissionByUserName(String userName) throws Exception;

    /**
     * 通过userName字段查找角色并按一定格式返回数据
     * @param userName
     * @return
     * @throws Exception
     */
    public String queryRoleByUserName(String userName) throws Exception;

    /**
     * 组织注册用json
     * @param account
     * @return
     * @throws Exception
     */
    public String makeRegisterJson(Account account) throws Exception;

    /**
     * 修改status和新增modified_time,标记成审核通过
     * @param userName
     * @return
     * @throws Exception
     */
    public String updateByUserName(String userName) throws Exception;
}
