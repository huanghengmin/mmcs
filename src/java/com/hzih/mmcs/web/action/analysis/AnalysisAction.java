package com.hzih.mmcs.web.action.analysis;

import com.hzih.mmcs.service.LogService;
import com.hzih.mmcs.service.SysRegInfService;
import com.hzih.mmcs.web.SessionUtils;
import com.hzih.mmcs.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-20
 * Time: 下午7:46
 * To change this template use File | Settings | File Templates.
 */
public class AnalysisAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(AnalysisAction.class);
    private LogService logService;
    private SysRegInfService sysRegInfService;

    public void setSysRegInfService(SysRegInfService sysRegInfService) {
        this.sysRegInfService = sysRegInfService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public String selectBuilder() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String userName = SessionUtils.getAccount(request).getUserName();
        String json = null;
        try {
            String rows = sysRegInfService.finbBuildDepart();
            json = "{success:true,total:1,rows:["+rows+"]}";
            logService.newLog("INFO", userName, "统计分析", "统计分析查找成功");
        } catch (Exception e) {
            logger.error("统计分析查找出错", e);
            logService.newLog("ERROE", userName, "登录系统", "登录系统失败");
        }
        new ActionBase().actionEnd(response,json);
        return null;
    }
}
