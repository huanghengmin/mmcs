package com.hzih.mmcs.domain;


import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-9
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
public class News {
    private int nid;
    private String title;
    private String content;
    private String files;
    private Date time;
    private String classname;

    public News() {
    }

    public News(String title, String content, String files, Date time, String classname) {
        this.title = title;
        this.content = content;
        this.files = files;
        this.time = time;
        this.classname = classname;
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

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }
}
