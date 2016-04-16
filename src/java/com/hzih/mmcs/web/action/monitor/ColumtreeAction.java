package com.hzih.mmcs.web.action.monitor;

import com.hzih.mmcs.service.LogService;
import com.hzih.mmcs.service.StatSysStatusService;
import com.hzih.mmcs.web.SessionUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-10
 * Time: 上午11:15
 * To change this template use File | Settings | File Templates.
 */
public class ColumtreeAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(ColumtreeAction.class);
    private LogService logService;
    private StatSysStatusService statSysStatusService;

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public StatSysStatusService getStatSysStatusService() {
        return statSysStatusService;
    }

    public void setStatSysStatusService(StatSysStatusService statSysStatusService) {
        this.statSysStatusService = statSysStatusService;
    }

    public String selectall2() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
//        String idsystem = SessionUtils.getAccount(request).getDistrictCode();
        String idsystem ="220000";
        String testJson = "";
        try {
            if(idsystem.substring(2,6).equals("0000")){
                testJson =statSysStatusService.findStatSysStatus(idsystem) ;
            }else if(idsystem.substring(4,6).equals("00")){
                testJson =statSysStatusService.findCityCode(idsystem) ;
            }else {
                testJson =statSysStatusService.findCountyCode(idsystem) ;
            }
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "运行监控", "查询运行监控列表成功");
        } catch (Exception e) {
            logger.error("运行监控", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "运行监控","查询运行监控列表失败 ");
        }
//        System.out.println("ColumtreeAction  selectall2=="+testJson);
        response.getWriter().write(testJson);
        return null;
    }
}
