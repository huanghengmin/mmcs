package com.hzih.mmcs.web.action.score;

import com.hzih.mmcs.domain.*;
import com.hzih.mmcs.service.InitSystemService;
import com.hzih.mmcs.service.LogService;
import com.hzih.mmcs.service.SysAssessmentService;
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
 * Date: 13-1-11
 * Time: 下午6:12
 * To change this template use File | Settings | File Templates.
 */
public class SysAssessmentAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(SysAssessmentAction.class);
    private LogService logService;
    private SysAssessmentService sysAssessmentService;
    private InitSystemService initSystemService;

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public void setInitSystemService(InitSystemService initSystemService) {
        this.initSystemService = initSystemService;
    }

    public SysAssessmentService getSysAssessmentService() {
        return sysAssessmentService;
    }

    public void setSysAssessmentService(SysAssessmentService sysAssessmentService) {
        this.sysAssessmentService = sysAssessmentService;
    }

    
    
    public String findSingleAssessment()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        //        String code = SessionUtils.getAccount(request).getDistrictCode();
        String code ="220000";
        String json = null;
        try{
            if(code!=null){
                json = sysAssessmentService.findSingleAssessment(code);
                logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(),"考核评比","考核评比查找根成功");
            } else {
                logger.warn("考核评比查找根出错,没有根");
            }
        } catch (Exception e) {
            logger.error("考核评比查找根出错",e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"考核评比","考核评比查找根失败");
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }
    
    
    public String findSysAssessment() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
//        String code = SessionUtils.getAccount(request).getDistrictCode();
//        String code = SessionUtils.getInitSystem(request).getRootCode();
        String code = null;
        StringBuilder json = new StringBuilder();
        try{
            String levelCode = request.getParameter("levelCode");
            if(levelCode!=null&&!levelCode.equals("")){
                code =levelCode;
            }
            json.append("[");
            if(code!=null){
                json.append(sysAssessmentService.findSysAssessment(code));
                logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(),"考核评比","考核评比查找子节点成功");
            } else {
                logger.warn("考核评比查找子节点出错,没有子节点");
            }
            json.append("]");

        } catch (Exception e) {
            logger.error("考核评比查找子节点出错",e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"考核评比","考核评比查找子节点失败");
        }
        actionBase.actionEnd(response, json.toString(), result);
        return null;
    }
}
