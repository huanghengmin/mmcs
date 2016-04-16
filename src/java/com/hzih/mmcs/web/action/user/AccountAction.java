package com.hzih.mmcs.web.action.user;

import com.hzih.mmcs.domain.Account;
import com.hzih.mmcs.service.AccountService;
import com.hzih.mmcs.service.LogService;
import com.hzih.mmcs.web.SessionUtils;
import com.hzih.mmcs.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 12-6-8
 * Time: 下午12:24
 * To change this template use File | Settings | File Templates.
 */
public class AccountAction extends ActionSupport {
    private Logger logger = Logger.getLogger(AccountAction.class);
    private AccountService accountService;
    private LogService logService;
    private int start;
    private int limit;

    public String loadPermission() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String msg = null;
        int status = 0;
        try {
            String userName = request.getParameter("userName");
            Account account = accountService.queryByUserName(userName);
            if(account!=null){
                status = Integer.parseInt(account.getStatus()); //0:未通过审核;1:通过审核
                if(status==0){
                    msg = "shError*审核结果*error/sh_error.jsp";
                } else {
                    msg = accountService.queryRoleByUserName(userName);
                }
            } else {
                status = 0;
                msg = "register*注册申请*system/register.jsp";
            }
//            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "加载模块","加载模块成功");
        } catch (Exception e) {
            logger.error("加载模块", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "加载模块","加载模块失败");
            msg = "查找用户失败";
        }
        String json = "{success:true,status:"+status+",msg:'"+msg+"'}";
        actionBase.actionEnd(response,json);
        return null;
    }

    /**
     * 加载所有用户key:value
     * @return
     * @throws Exception
     */
    public String queryKeyValueUserName() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String json = null;
        try {
            json = accountService.selectUserNameKeyValue();
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "用户日志审计","加载用户审计日志数据成功");
        } catch (Exception e) {
            logger.error("用户日志审计", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "用户日志审计","加载用户审计日志数据失败");
            json = "{success:true,total:0,rows[]}";
        }
        actionBase.actionEnd(response,json);
        return null;
    }

    /**
     * 加载所有部门key:value
     * @return
     * @throws Exception
     */
    public String queryKeyValueDepartment() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String json = null;
        try {
            json = accountService.selectDepartmentNameKeyValue();
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "注册申请","加载部门列表成功");
        } catch (Exception e) {
            logger.error("注册申请", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "注册申请","加载部门列表失败");
            json = "{success:true,total:0,rows[]}";
        }
        actionBase.actionEnd(response,json);
        return null;
    }

    public String load() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String json = null;
        try {
            String userName = request.getParameter("userName");
            String status = request.getParameter("status");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            json = accountService.select(startDate,endDate,userName,status,start,limit);
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "注册管理","加载用户信息数据成功");
        } catch (Exception e) {
            logger.error("注册管理", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "注册管理","加载用户信息数据失败");
            json = "{success:true,total:0,rows[]}";
        }
        actionBase.actionEnd(response,json);
        return null;
    }


    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
