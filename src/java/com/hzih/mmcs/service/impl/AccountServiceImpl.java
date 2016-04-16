package com.hzih.mmcs.service.impl;

import cn.collin.commons.domain.PageResult;
import cn.collin.commons.utils.DateUtils;
import com.hzih.mmcs.dao.*;
import com.hzih.mmcs.domain.*;
import com.hzih.mmcs.service.AccountService;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 12-6-11
 * Time: 下午1:01
 * To change this template use File | Settings | File Templates.
 * 用户管理业务逻辑处理
 */
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;
    private RoleDao roleDao;
    private DepartmentDao departmentDao;
    private DistrictDao districtDao;
    private OrgcodeDao orgcodeDao;
    private InitSystemDao initSystemDao;
    private SysmessageDao sysmessageDao;

    /**
     * 分页查找用户，并以json形式返回
     * @param start   开始页
     * @param limit   每页大小
     * @return
     */
    public String select(String startDate,String endDate,String userName, String status,int start,int limit) throws Exception {
        PageResult pageResult = accountDao.listByPage(startDate,endDate,userName,status,(start/limit+1),limit);
        int total = pageResult.getAllResultsAmount();
        List<Account> accounts = pageResult.getResults();
        String json = "{success:true,total:" + total + ",rows:[";
        for (Account a : accounts) {
            Set<Role> roles = a.getRoles();
            Iterator<Role> iterator = roles.iterator();
            String roleName = null;
            Long roleId = null;
            while (iterator.hasNext()){
                Role role = iterator.next();
                roleName = role.getName();
                roleId = role.getId();
            }
            District d = districtDao.findById(Long.valueOf(a.getDistrictCode()));
            Orgcode orgcode = orgcodeDao.findOrgcode(a.getOrgCode());
            json +="{id:'"+a.getId()+"',userName:'"+a.getUserName()+
                    "',status:'"+a.getStatus()+"',phone:'"+a.getPhone()+
                    "',email:'"+a.getEmail()+"',roleId:'"+roleId+"',roleName:'"+roleName+
                    "',userId:'"+a.getUserId()+"',districtCode:'"+a.getDistrictCode()+
                    "',districtName:'"+d.getDistrictName()+
                    "',orgCode:'"+a.getOrgCode()+"',orgName:'"+orgcode.getOrgname()+
                    "',mobilePhone:'"+a.getMobilePhone()+"',systemId:'"+a.getSystemId()+
                    "',registerTime:'"+ DateUtils.format(a.getRegisterTime(),"yyyy-MM-dd HH:mm:ss")+
                    "',modifiedTime:'"+(a.getModifiedTime()==null?"":DateUtils.format(a.getModifiedTime(), "yyyy-MM-dd HH:mm:ss"))+"'},";
        }
        json += "]}";
        return json;
    }

    /**
     * 更新用户信息，并以json形式返回处理结果
     * @param account  用户信息
     * @param rIds     用户对应角色ID
     * @return
     * @throws Exception
     */
    public String update(Account account,long[] rIds) throws Exception {
        Account old = (Account) accountDao.getById(account.getId());

        if(rIds.length > 0){
            Set<Role> roles = new HashSet<Role>();
            for (int i = 0; i < rIds.length; i++) {
                Role role = (Role) roleDao.getById(rIds[i]);
                roles.add(role);
            }
            old.setRoles(roles);
        }
        old.setEmail(account.getEmail());
        old.setPhone(account.getPhone());
        old.setMobilePhone(account.getMobilePhone());
        old.setSystemId(account.getSystemId());
        old.setUserId(account.getUserId());
        accountDao.update(old);
        return "<font color=\"green\">修改成功,点击[确定]返回列表!</font>";
    }

    /**
     * 删除用户信息，并以json形式返回处理结果
     *
     * @param ids   用户ID
     * @return
     * @throws Exception
     */
    public String delete(String[] ids) throws Exception {
        accountDao.deleteWithDependent(ids);
        for (int i=0;i<ids.length;i++) {
            sysmessageDao.deleteBySendOrReceiveId(ids[i]);
        }
        return "<font color=\"green\">删除成功,点击[确定]返回列表!</font>";
    }

    /**
     * 新增用户信息，并以json形式返回处理结果
     * @param account  新增用户信息
     * @param rIds     用户对应角色ID
     * @return
     * @throws Exception
     */
    public String insert(Account account, long[] rIds) throws Exception {
        Set<Role> roles = new HashSet<Role>();
        for (int i = 0; i < rIds.length; i++) {
            Role role = (Role) roleDao.getById(rIds[i]);
            roles.add(role);
        }
        account.setRoles(roles);
        accountDao.create(account);
        return "<font color=\"green\">提交成功,点击[确定]返回!</font>";
    }

    /**
     * 新增信息时，检查用户名是否已经使用，并以json形式返回处理结果
     * @param userName    用户名
     * @return
     */
    public String checkUserName(String userName) throws Exception{
        String msg = null;
        Account account = accountDao.findByName(userName);
        if(account!=null){
            msg = "用户名已经存在";
        } else {
            msg = "true";
        }
        return "{success:true,msg:'"+msg+"'}";
    }

    /**
     * 组织账号名成为一组 {key:'',value:''}  列表
     * @return
     * @throws Exception
     */
    public String selectUserNameKeyValue() throws Exception {
        List<Account> accounts = accountDao.findAll();
        int total = accounts.size();
        String json = "{success:true,total:"+total + ",rows:[";
        for (Account a : accounts) {
            json += "{key:'"+a.getUserName()+"',value:'"+a.getUserName()+"'},";
        }
        json += "]}";
        return json;
    }

    /**
     * 组织部门名成为一组 {key:'',value:''}  列表
     * @return
     * @throws Exception
     */
    public String selectDepartmentNameKeyValue() throws Exception {
        List<Department> list = departmentDao.findAll();
        int total = list.size();
        String json = "{success:true,total:"+total + ",rows:[";
        for (Department d : list) {
            json += "{key:'"+d.getName()+"',value:'"+d.getCode()+"'},";
        }
        json += "]}";
        return json;
    }

    @Override
    public Account queryByUserName(String userName) throws Exception {
        return accountDao.findByName(userName);
    }

    @Override
    public String queryPermissionByUserName(String userName) throws Exception {
        String msg = "";
        Account account = accountDao.findByName(userName);
        Set<Role> roles = account.getRoles();
        for(Iterator<Role> iterators = roles.iterator();iterators.hasNext();){
            Role role = iterators.next();
            Set<Permission> permissions = role.getPermissions();
            int index = 0;
            for(Iterator<Permission> ites = permissions.iterator();ites.hasNext();){
                index ++;
                Permission p = ites.next();
                if(index==permissions.size()){
                    msg += p.getCode() +"*"+ p.getName() + "*" + p.getDescription();
                } else {
                    msg += p.getCode() +"*"+ p.getName() + "*" + p.getDescription() + "##";
                }
            }
        }

        return msg;
    }

    @Override
    public String queryRoleByUserName(String userName) throws Exception {
        String roleType = "";
        Account account = accountDao.findByName(userName);
        Set<Role> roles = account.getRoles();
        for(Iterator<Role> iterators = roles.iterator();iterators.hasNext();){
            Role role = iterators.next();
            roleType = String.valueOf(role.getType());
        }
        return roleType;
    }

    @Override
    public String makeRegisterJson(Account account) throws Exception {
        List<InitSystem> list = initSystemDao.findAll();
        String json = "{success:true,total:0,rows:[]}";
        if(list.size()>0){
            InitSystem initSystem = list.get(0);
            boolean isIn = isInRoot(initSystem.getRootCode(),account.getDistrictCode());
            if(isIn) {
                Orgcode orgcode = orgcodeDao.findOrgcode(account.getOrgCode());
                District d = districtDao.findById(Long.valueOf(account.getDistrictCode()));
                json = "{success:true,total:" + 1 + ",rows:[" +
                        "{userName:'"+account.getUserName()+
                        "',status:'0',phone:'"+account.getPhone()+
                        "',orgCode:'"+account.getOrgCode()+"',orgName:'"+orgcode.getOrgname()+
                        "',email:'"+account.getEmail()+"',systemId:'"+account.getDistrictCode()+
                        "',userId:'"+account.getUserId()+"',districtCode:'"+account.getDistrictCode()+
                        "',districtName:'"+d.getDistrictName()+"'}]}";
            }
        }
        return json;
    }

    private boolean isInRoot(String rootCode, String districtCode) {
        String root = rootCode.substring(0,2);
        if(root.equals(districtCode.substring(0,2))){
            return true;
        }
        return false;
    }

    @Override
    public String updateByUserName(String userName) throws Exception {
        Account account = accountDao.findByName(userName);
        account.setModifiedTime(new Date());
        account.setStatus("1");
        accountDao.update(account);
        return "审核通过成功,点击[确定]返回页面!";
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setDistrictDao(DistrictDao districtDao) {
        this.districtDao = districtDao;
    }

    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public void setOrgcodeDao(OrgcodeDao orgcodeDao) {
        this.orgcodeDao = orgcodeDao;
    }

    public void setInitSystemDao(InitSystemDao initSystemDao) {
        this.initSystemDao = initSystemDao;
    }

    public void setSysmessageDao(SysmessageDao sysmessageDao) {
        this.sysmessageDao = sysmessageDao;
    }
}
