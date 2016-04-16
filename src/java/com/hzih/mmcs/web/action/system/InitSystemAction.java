package com.hzih.mmcs.web.action.system;

import com.hzih.mmcs.domain.InitSystem;
import com.hzih.mmcs.service.LogService;
import com.hzih.mmcs.service.impl.DistrictServiceImpl;
import com.hzih.mmcs.service.impl.InitSystemServiceImpl;
import com.hzih.mmcs.utils.StringUtils;
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
 * Date: 13-1-13
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
public class InitSystemAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(InitSystemAction.class);
    private LogService logService;
    private InitSystemServiceImpl initSystemService;
    private DistrictServiceImpl districtService;
    private InitSystem initSystem;

    /**
     * 加载初始化监管中心信息
     * @return
     */
    public String load() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = null;
        try {
            json = initSystemService.load();
//            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "初始化监管中心","用户读取系统日志审计信息成功 ");
        } catch (Exception e) {
            logger.error("初始化监管中心", e);
//            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "初始化监管中心","用户读取系统日志审计信息失败 ");
            json = "{success:true,total:0,rows:[]}";
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    /**
     * 初始化监管中心,指定监管中心最顶级部门:部,省,市
     * @return
     */
    public String initSystem() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        try {
            InitSystem initSystemOld = initSystemService.query();
            if(initSystemOld==null){
                msg = initSystemService.insert(initSystem);
            } else {
                initSystemOld.setRootCode(initSystem.getRootCode());
                msg = initSystemService.update(initSystemOld);
            }
//            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "初始化监管中心","用户读取系统日志审计信息成功 ");
        } catch (Exception e) {
            logger.error("初始化监管中心", e);
//            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "初始化监管中心","用户读取系统日志审计信息失败 ");
            msg = "初始化失败";
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public void setInitSystemService(InitSystemServiceImpl initSystemService) {
        this.initSystemService = initSystemService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public void setDistrictService(DistrictServiceImpl districtService) {
        this.districtService = districtService;
    }

    public InitSystem getInitSystem() {
        return initSystem;
    }

    public void setInitSystem(InitSystem initSystem) {
        this.initSystem = initSystem;
    }
}
