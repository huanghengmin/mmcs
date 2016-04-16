package com.hzih.mmcs.web.action.News;

import com.hzih.mmcs.service.LogService;
import com.hzih.mmcs.service.NewsService;
import com.hzih.mmcs.web.SessionUtils;
import com.hzih.mmcs.web.action.ActionBase;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-9
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
public class NewsAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(NewsAction.class);

    private NewsService newsService;
    private LogService logService;
    private int start;
    private int limit;
    private String ids;
    private int nid;
    private String title;
    private String content;
    private String files;
    private Date time;
    private String classname;

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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public NewsService getNewsService() {
        return newsService;
    }

    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public String findNews() throws Exception {
        String jsons = newsService.findNews(start,limit);
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().getContext().get(ServletActionContext.HTTP_RESPONSE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            logger.error("查找公告信息失败",e);
        }
        out.println(jsons);
        out.flush();
        out.close();
        return null;
    }

    public String addNews() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        try{
            newsService.addNews(title, content, files, new Date(), classname);
            msg = "增加信息成功";
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(),"公告信息","增加公告信息成功");
        } catch (Exception e){
            logger.error("增加公告信息失败",e);
            msg = "增加信息失败";
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"公告信息","增加公告信息失败");
        }
        json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delNews() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        try{
            newsService.delNewsByIds(ids);
            msg = "删除成功";
            logger.info("删除公告信息成功"+SessionUtils.getAccount(request).getUserName());
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(),"公告信息","删除公告信息成功");
        } catch (Exception e){
            msg = "删除失败";
            logger.error("删除公告信息失败",e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"公告信息","删除公告信息失败");
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);

        return null;
    }

    public String updNews() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String msg = null;
        try{
            newsService.updNews(nid,title, content, files, new Date(), classname);
            msg = "修改信息成功";
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(),"公告信息","修改公告信息成功");
        } catch (Exception e){
            msg = "修改信息失败";
            logger.error("修改公告信息失败", e);
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(),"公告信息","修改公告信息失败");
        }
        String json = "{success:true,msg:'"+msg+"'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }


}
