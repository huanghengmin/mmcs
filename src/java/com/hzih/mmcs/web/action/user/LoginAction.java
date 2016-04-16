package com.hzih.mmcs.web.action.user;

import com.hzih.mmcs.domain.Account;
import com.hzih.mmcs.service.AccountService;
import com.hzih.mmcs.service.InitSystemService;
import com.hzih.mmcs.service.LogRecordService;
import com.hzih.mmcs.service.LogService;
import com.hzih.mmcs.web.SessionUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: 钱晓� * Date: 12-6-8
 * Time: 下午12:24
 * To change this template use File | Settings | File Templates.
 */
public class LoginAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(LoginAction.class);
    private AccountService accountService;
    private InitSystemService initSystemService;
    private LogRecordService logRecordService;
    private LogService logService;

    public String login() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String userName = request.getParameter("userName");
        try {
            Account account = accountService.queryByUserName(userName);
            if(account!=null){
                SessionUtils.setAccount(request,account);
                logRecordService.newOrUpdateLog(userName,account.getSystemId());
                logService.newLog("INFO",  userName, "登录系统","登录系统成功");
            }
            response.setStatus(HttpServletResponse.SC_OK);
//            InitSystem initSystem = initSystemService.query();
//            if(initSystem!=null){
//                SessionUtils.setInitSystem(request,initSystem);
//                initSystem = SessionUtils.getInitSystem(request);
//                int i =0;
//            }
        } catch (Exception e) {
            logger.error("登录系统", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logService.newLog("ERROE", userName, "登录系统","登录系统失败");
        }
        return null;
    }
    public String logout() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        request.getCharacterEncoding();
        String userName = SessionUtils.getAccount(request).getUserName();
        try {
            SessionUtils.removeAccount(request);
            SessionUtils.removeInitSystem(request);
            SessionUtils.invalidateSession(request);
            logService.newLog("INFO", userName, "退出系统", "退出系统成功");
        } catch (Exception e) {
            logger.error("退出系统", e);
            logService.newLog("ERROE", userName, "退出系统","退出系统失败");
        }
        response.setCharacterEncoding("UTF-8");
        response.sendRedirect(request.getContextPath() +"/logout.jsp");
        return null;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setInitSystemService(InitSystemService initSystemService) {
        this.initSystemService = initSystemService;
    }

    public void setLogRecordService(LogRecordService logRecordService) {
        this.logRecordService = logRecordService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }
}
