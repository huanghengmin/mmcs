package com.hzih.mmcs.web.action.monitor;

import com.hzih.mmcs.service.*;
import com.hzih.mmcs.utils.FileUtils;
import com.hzih.mmcs.utils.constant.ConstantUtils;
import com.hzih.mmcs.web.SessionUtils;
import com.hzih.mmcs.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-16
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
public class SelectAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(SelectAction.class);
    private LogService logService;
    private SysRegInfService sysRegInfService;
    private SysDeviceInfService sysDeviceInfService;
    private SysOutLinkInfService sysOutLinkInfService;
    private SysBizInfService sysBizInfService;

    public String selectByIdsystem() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        String json="";
        try {
//        int start = Integer.valueOf(request.getParameter("start"));
//        int limit = Integer.valueOf(request.getParameter("limit"));
                String idsystem=request.getParameter("idsystem");
//            String idsystem="41010001" ;
//        String json=sysRegInfService.findByIdSystem(start/limit+1, limit,idsystem);
            json=sysRegInfService.findByIdSystem(1, 15,idsystem);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "运行监控","查看注册表详细成功");
        } catch (Exception e) {
            logger.error("运行监控", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "运行监控","查看注册表详细失败 ");
        }
        json=json.replace("\n"," ");
        response.getWriter().write(json);
        return null;
    }
    public String selectByIdsystemDevice() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        int start = Integer.valueOf(request.getParameter("start"));
        int limit = Integer.valueOf(request.getParameter("limit"));
        String json="";
        try {
                String idsystem=request.getParameter("idsystem");
//            String idsystem="41010001" ;
            json=sysDeviceInfService.findByIdSystem(start/limit+1, limit,idsystem);
            json=json.replace("\n"," ");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "运行监控","查看设备表详细成功");
        } catch (Exception e) {
            logger.error("运行监控", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "运行监控","查看设备表详细失败 ");
        }
        response.getWriter().write(json);
        return null;
    }
    public String selectByIdsystemOutLink() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        int start = Integer.valueOf(request.getParameter("start"));
        int limit = Integer.valueOf(request.getParameter("limit"));
        String json="";
        try {
                String idsystem=request.getParameter("idsystem");
//            String idsystem="41010001" ;
             json=sysOutLinkInfService.findByIdSystem(start/limit+1, limit,idsystem);
            json=json.replace("\n"," ");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "运行监控","查看链路表详细成功");
        } catch (Exception e) {
            logger.error("运行监控", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "运行监控","查看链路表详细失败 ");
        }
        response.getWriter().write(json);
        return null;
    }
    public String selectByIdsystemBiz() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        int start = Integer.valueOf(request.getParameter("start"));
        int limit = Integer.valueOf(request.getParameter("limit"));
        String json="";
        try {
        String idsystem=request.getParameter("idsystem");
//            String idsystem="41010001" ;
            json=sysBizInfService.findByIdSystem(start/limit+1, limit,idsystem);
            json=json.replace("\n"," ");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "运行监控","查看接入应用表详细成功");
        } catch (Exception e) {
            logger.error("运行监控", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "运行监控","查看接入应用表详细失败 ");
        }
        response.getWriter().write(json);
        return null;
    }

    public String downloadByFilename() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = "{success:false}";
        try {
            String fileName = request.getParameter("fileName");
            String Agent = request.getHeader("User-Agent");
            StringTokenizer st = new StringTokenizer(Agent,";");
            st.nextToken();
            //得到用户的浏览器名  MSIE  Firefox
            String userBrowser = st.nextToken();
            String path = null;
            path = ConstantUtils.UPLOADPATH + fileName;
            File source = new File(path);
            String name = source.getName();
            FileUtils.downType(response, name, userBrowser);
            response = FileUtils.copy(source, response);
            logger.info("下载成功!");
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "下载","用户下载"+fileName+"成功");
            json = "{success:true}";
        } catch (Exception e) {
            logger.error("下载失败", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "下载","用户下载失败");
        }
        actionBase.actionEnd(response,json,result);
        return null;
    }

    public SysRegInfService getSysRegInfService() {
        return sysRegInfService;
    }

    public void setSysRegInfService(SysRegInfService sysRegInfService) {
        this.sysRegInfService = sysRegInfService;
    }

    public SysDeviceInfService getSysDeviceInfService() {
        return sysDeviceInfService;
    }

    public void setSysDeviceInfService(SysDeviceInfService sysDeviceInfService) {
        this.sysDeviceInfService = sysDeviceInfService;
    }

    public SysOutLinkInfService getSysOutLinkInfService() {
        return sysOutLinkInfService;
    }

    public void setSysOutLinkInfService(SysOutLinkInfService sysOutLinkInfService) {
        this.sysOutLinkInfService = sysOutLinkInfService;
    }

    public SysBizInfService getSysBizInfService() {
        return sysBizInfService;
    }

    public void setSysBizInfService(SysBizInfService sysBizInfService) {
        this.sysBizInfService = sysBizInfService;
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }
}
