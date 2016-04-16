package com.hzih.mmcs.web.action.monitor;

import com.hzih.mmcs.service.LogService;
import com.hzih.mmcs.service.TerminalMonitorService;
import com.hzih.mmcs.web.SessionUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-9
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
public class TerminalMonitorAction extends ActionSupport {
    private TerminalMonitorService terminalMonitorService;
    private static final Logger logger = Logger.getLogger(TerminalMonitorAction.class);
    private LogService logService;

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public void setTerminalMonitorService(TerminalMonitorService terminalMonitorService) {
        this.terminalMonitorService = terminalMonitorService;
    }

    public String index() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
//        String zonecode = SessionUtils.getAccount(request).getDistrictCode();
        String zonecode = "220000";
        String s = "";
        try{
            if ("310000".equalsIgnoreCase(zonecode) || "120000".equalsIgnoreCase(zonecode) || "500000".equalsIgnoreCase(zonecode) || "110000".equalsIgnoreCase(zonecode)) {
                s = terminalMonitorService.ZhixiacitySelect(zonecode);
            } else if (zonecode.contains("0000")) {
                s = terminalMonitorService.indexAll(zonecode);
            } else {
                s = terminalMonitorService.citySelect(zonecode);
            }
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(),"终端监控","终端监控查找成功");
        } catch (Exception e) {
            logger.error("终端监控查找出错",e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"终端监控","终端监控查找失败");
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println(s);
        writer.close();
        return null;
    }

    public String detail() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String pageSize = request.getParameter("limit");
        String start = request.getParameter("start");
        String idsystem = request.getParameter("idsystem");
        String json = null;
        try {
            json = terminalMonitorService.detail(idsystem, start, pageSize);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(),"终端监控","终端监控详细查找成功");
        } catch (Exception e) {
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"终端监控","终端监控详细查找失败");
            logger.error("终端监控详细查找出错" , e);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println(json);
        writer.close();
        return null;
    }

}
