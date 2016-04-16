package com.hzih.mmcs.web.action.sysabnormal;

import com.hzih.mmcs.service.LogService;
import com.hzih.mmcs.service.SysabnormalService;
import com.hzih.mmcs.web.SessionUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-11
 * Time: 上午11:55
 * To change this template use File | Settings | File Templates.
 */
public class SysabnormalAction  extends ActionSupport {
    private static final Logger logger = Logger.getLogger(SysabnormalAction.class);
    private SysabnormalService sysabnormalService;
    private LogService logService;
    private int start;
    private int limit;
    private String idsystem;
    private String timetype;
    private String treadresult;

    public String getTimetype() {
        return timetype;
    }

    public void setTimetype(String timetype) {
        this.timetype = timetype;
    }

    public String getTreadresult() {
        return treadresult;
    }

    public void setTreadresult(String treadresult) {
        this.treadresult = treadresult;
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

    public String getIdsystem() {
        return idsystem;
    }

    public void setIdsystem(String idsystem) {
        this.idsystem = idsystem;
    }

    public SysabnormalService getSysabnormalService() {
        return sysabnormalService;
    }

    public void setSysabnormalService(SysabnormalService sysabnormalService) {
        this.sysabnormalService = sysabnormalService;
    }

    public String findAbnormaVos() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String jsons=null;
        try{
            jsons = sysabnormalService.findAbnormaVos(idsystem,start,limit);
            logService.newLog("INFO",SessionUtils.getAccount(request).getUserName(),"违规通报","违规通报详细列表查找成功");
        }catch (Exception e){
            logger.error("违规通报详细列表", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "违规通报", "违规通报详细列表查找失败");
        }
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    public String findAbnormaNum() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        String coldata="";
//        String zonecode = SessionUtils.getAccount(request).getDistrictCode();
        String zonecode = "220000";
//        System.out.println("aaaaaaaaaaaaaaaaaaaaa"+ SessionUtils.getAccount(request).getUserName() );
        String zoneflag = zonecode.substring(2, 6);
        try{
            if("0000".equals(zoneflag)){
                coldata = sysabnormalService.findAbnormaNum(zonecode);
            }else if("00".equals(zonecode.substring(4,6))) {
                coldata = sysabnormalService.findAbnormaNumForCity(zonecode);
            }else {
                coldata = sysabnormalService.findAbnormaNumForCounty(zonecode);
            }
            logService.newLog("INFO",SessionUtils.getAccount(request).getUserName(),"违规通报","违规通报数目查找成功");
        }catch (Exception e){
            logger.error("违规通报数目查询", e);
            logService.newLog("ERROR",SessionUtils.getAccount(request).getUserName(),"违规通报","违规通报数目查找失败");
        }
        response.getWriter().write(coldata);
        return null;

    }

    public String findAbnormaVosByTimeAndTreadresult() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String jsons=null;
        try{
            jsons = sysabnormalService.findAbnormaVosByTimeAndTreadresult(idsystem,start,limit,timetype,treadresult);
            logService.newLog("INFO",SessionUtils.getAccount(request).getUserName(),"违规通报","违规通报详细列表条件查询成功");
        }catch (Exception e){
            logger.error("违规通报详细列表条件查询",e);
            logService.newLog("ERROR",SessionUtils.getAccount(request).getUserName(),"违规通报","违规通报详细列表条件查询失败");
        }
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }
}
