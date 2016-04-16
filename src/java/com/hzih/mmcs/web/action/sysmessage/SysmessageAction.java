package com.hzih.mmcs.web.action.sysmessage;

import com.hzih.mmcs.domain.Account;
import com.hzih.mmcs.service.AccountService;
import com.hzih.mmcs.service.LogService;
import com.hzih.mmcs.service.SysmessageService;
import com.hzih.mmcs.service.impl.LogServiceImpl;
import com.hzih.mmcs.web.SessionUtils;
import com.hzih.mmcs.web.action.ActionBase;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-10
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
public class SysmessageAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(SysmessageAction.class);
    private SysmessageService sysmessageService;
    private AccountService accountService;

    private int start;
    private int limit;
    private String ids;
    private String msgName;
    private String msgType;
    private long msgAdminId;
    private int id;
    private String ifchecked;
    private LogService logService;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIfchecked() {
        return ifchecked;
    }

    public void setIfchecked(String ifchecked) {
        this.ifchecked = ifchecked;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public long getMsgAdminId() {
        return msgAdminId;
    }

    public void setMsgAdminId(long msgAdminId) {
        this.msgAdminId = msgAdminId;
    }

    public String getMsgName() {
        return msgName;
    }

    public void setMsgName(String msgName) {
        this.msgName = msgName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public SysmessageService getSysmessageService() {
        return sysmessageService;
    }
    public void setSysmessageService(SysmessageService sysmessageService) {
        this.sysmessageService = sysmessageService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
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

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public String findMessageUserVos() throws Exception{
//        String jsons = sysmessageService.findMessageUserVos(start,limit);
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Account account = SessionUtils.getAccount(request);
        String jsons = null;
        try{
            long id = accountService.queryByUserName(account.getUserName()).getId();
            jsons = sysmessageService.findMessageUserVosByMsgAdminId(String.valueOf(id), start, limit);

        } catch (Exception e) {

        }
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    /**
     * 发送人查找
     * @return
     * @throws Exception
     */
    public String findMessageSend() throws Exception{
//        String jsons = sysmessageService.findMessageUserVos(start,limit);
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Account account = SessionUtils.getAccount(request);
        String jsons = null;
        try{
            Account a = accountService.queryByUserName(account.getUserName());
            jsons = sysmessageService.findMessageUserSendById(a, start, limit);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(),"收件箱","查找省厅管理员收件箱信息成功");
        } catch (Exception e) {
            logger.error("查找省厅管理员收件箱信息失败",e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"收件箱","查找省厅管理员收件箱信息失败");
        }
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }



    public String findMessageGet() throws Exception{
//        String jsons = sysmessageService.findMessageUserVos(start,limit);
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Account account = SessionUtils.getAccount(request);
        String jsons = null;
        try{
            Account a = accountService.queryByUserName(account.getUserName());
            jsons = sysmessageService.findMessageUserGetById(a, start, limit);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(),"收件箱","查找地市区县管理员收件箱信息成功");
        } catch (Exception e) {
            logger.error("查找地市区县管理员收件箱信息失败",e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"收件箱","查找地市区县管理员收件箱信息失败");
        }
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    public String addSysMessage() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        Account account = SessionUtils.getAccount(request);
        try{
            Account a = accountService.queryByUserName(account.getUserName());
            sysmessageService.addSysmessage(msgName, msgType, a.getId(), msgAdminId, null, "0", new Date(), null);
            msg = "增加成功";
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(),"收件箱","省厅管理员发送信息成功");
        } catch (Exception e){
            logger.error("省厅管理员发送信息失败",e);
            msg = "增加失败";
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"收件箱","省厅管理员发送信息失败");
        }
        json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delSysMessages() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        try{
            sysmessageService.delSysmessagesByIds(ids);
            msg = "删除成功";
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(),"收件箱","省厅管理员删除信息成功");
        } catch (Exception e){
            msg = "删除失败";
            logger.error("删除收件箱信息失败",e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"收件箱","省厅管理员删除信息失败");
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String findUsersJson() throws Exception{
        String jsons = sysmessageService.findUsers();
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    public String updSysmessage() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        try{
            sysmessageService.updSysmessageByIdForIfchecked(id,ifchecked);
            msg = "自动修改阅读标记成功";
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"收件箱","地市区县管理员阅读后自动修改阅读标记成功");
        } catch (Exception e){
            msg = "自动修改阅读标记失败";
            logger.error("地市区县管理员阅读后自动修改阅读标记失败",e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"收件箱","地市区县管理员阅读后自动修改阅读标记失败");
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }
}
