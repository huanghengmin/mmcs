package com.hzih.mmcs.web.action.common;

import com.hzih.mmcs.domain.InitSystem;
import com.hzih.mmcs.service.LogService;
import com.hzih.mmcs.service.impl.DistrictServiceImpl;
import com.hzih.mmcs.service.impl.InitSystemServiceImpl;
import com.hzih.mmcs.web.SessionUtils;
import com.hzih.mmcs.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-13
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
public class DistrictAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(DistrictAction.class);
    private LogService logService;
    private DistrictServiceImpl districtService;

    /**
     * 加载所有省份部级机构信息
     * @return
     */
    public String loadKeyValueParent() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = null;
        try {
            json = districtService.queryKeyValueAllParents();
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "初始化监管中心","加载省级机构信息成功");
        } catch (Exception e) {
            logger.error("初始化监管中心", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "初始化监管中心","加载省级机构信息失败");
            json = "{success:true,total:0,rows:[]}";
        }
        actionBase.actionEnd(response, json);
        return null;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public void setDistrictService(DistrictServiceImpl districtService) {
        this.districtService = districtService;
    }

}
