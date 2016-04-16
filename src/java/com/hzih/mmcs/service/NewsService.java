package com.hzih.mmcs.service;

import com.hzih.mmcs.domain.News;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-9
 * Time: 下午5:15
 * To change this template use File | Settings | File Templates.
 */
public interface NewsService {
    public String findNews(int start, int limit) throws Exception;
    public News findNewsById(int id) throws Exception;
    public void addNews(String title, String content, String files, Date time, String classname) throws Exception;
    public void updNews(int nid, String title, String content, String files, Date time, String classname) throws Exception;
    public void delNewsByIds(String ids) throws Exception;
}
