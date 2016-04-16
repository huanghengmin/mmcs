package com.hzih.mmcs.web.action.user;

import com.hzih.mmcs.domain.Account;
import com.hzih.mmcs.domain.InitSystem;
import com.hzih.mmcs.domain.Role;
import com.hzih.mmcs.service.AccountService;
import com.hzih.mmcs.service.InitSystemService;
import com.hzih.mmcs.service.LogRecordService;
import com.hzih.mmcs.service.LogService;

import com.hzih.mmcs.web.SessionUtils;
import com.hzih.mmcs.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-14
 * Time: 下午1:40
 * To change this template use File | Settings | File Templates.
 */
public class RegisterAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(RegisterAction.class);
    private LogService logService;
    private AccountService accountService;
    private InitSystemService initSystemService;
    private LogRecordService logRecordService;
    private Account account;
    private Role role;

    public String load() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String json = null;
        try {
            Account account = SessionUtils.getAccount(request);
            json = accountService.makeRegisterJson(account);
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "注册申请","用户加载证书信息用于注册成功");
        } catch (Exception e) {
            logger.error("注册申请", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "注册申请","用户加载证书信息用于注册失败");
        }
        actionBase.actionEnd(response,json);
        return null;
    }

    /**
     * 注册申请
     * @return
     * @throws IOException
     */
    public String register() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String msg = null;
        try {
            long[] rIds = {role.getId()};
            account.setRegisterTime(new Date());
            msg = accountService.insert(account,rIds);
            Account a = accountService.queryByUserName(account.getUserName());
            if(a!=null){
                SessionUtils.setAccount(request, a);
                logRecordService.newOrUpdateLog(a.getUserName(), account.getSystemId());
            }
            InitSystem initSystem = initSystemService.query();
            if(initSystem!=null){
                SessionUtils.setInitSystem(request,initSystem);
            }
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "注册申请","用户注册申请提交成功");
        } catch (Exception e) {
            logger.error("注册申请错误", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "注册申请","用户注册申请提交失败");
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response,json);
        return null;
    }

    /**
     * 注册审核
     * @return
     * @throws IOException
     */
    public String registerAllow() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String msg = null;
        try {
            String userName = request.getParameter("userName");
            msg = accountService.updateByUserName(userName);
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "注册申请审核","审核通过成功");
        } catch (Exception e) {
            logger.error("注册申请审核错误", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "注册申请审核","审核通过失败");
            msg = "审核失败";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response,json);
        return null;
    }

    public String delete() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String msg = null;
        try {
            String [] ids = request.getParameterValues("ids");
            msg = accountService.delete(ids);
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "注册管理","删除用户注册信息数据成功");
        } catch (Exception e) {
            logger.error("注册管理", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "注册管理","删除用户注册信息数据失败");
            msg = "删除失败";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response,json);
        return null;
    }


    public String update() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
		String msg = null;
        try {
            long[] rIds = {role.getId()};
            msg = accountService.update(account,rIds);
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "注册管理","修改用户注册信息数据成功");
        } catch (Exception e) {
            logger.error("注册管理", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "注册管理","修改用户注册信息数据失败");
            msg = "修改失败";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response,json);
        return null;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public void setInitSystemService(InitSystemService initSystemService) {
        this.initSystemService = initSystemService;
    }

    public void setLogRecordService(LogRecordService logRecordService) {
        this.logRecordService = logRecordService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
