package com.hzih.mmcs.service.impl;

import com.hzih.mmcs.dao.NewsDao;
import com.hzih.mmcs.domain.News;
import com.hzih.mmcs.service.NewsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-9
 * Time: 下午5:19
 * To change this template use File | Settings | File Templates.
 */
public class NewsServiceImpl implements NewsService{
    
    private NewsDao newsDao;

    public NewsDao getNewsDao() {
        return newsDao;
    }

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public String findNews(int start, int limit) throws Exception {
        ArrayList<News> alllist = newsDao.findNews();
        ArrayList<News> list = newsDao.findNews(start,limit);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'newslist':").append(alllist.size()).append(",'newsrow':[");
        for(News s : list){
            jsonb.append("{nid:").append(s.getNid()).append(",title:'").append(s.getTitle());
            jsonb.append("',content:'").append(s.getContent()).append("',files:'").append(s.getFiles());
            jsonb.append("',time:'").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getTime())).append("',classname:'").append(s.getClassname()).append("'},");
        }
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public News findNewsById(int id) throws Exception {
        ArrayList<News> list = newsDao.findNewsById(id);
        return list.get(0); 
    }

    @Override
    public void addNews(String title, String content, String files, Date time, String classname) throws Exception {
        newsDao.addNews(new News(title, content, files, time, classname));
    }

    @Override
    public void updNews(int nid, String title, String content, String files, Date time, String classname) throws Exception {
        News news = findNewsById(nid);
        news.setTitle(title);
        news.setContent(content);
        news.setFiles(files);
        news.setTime(time);
        news.setClassname(classname);
        newsDao.updNews(news);
    }

    @Override
    public void delNewsByIds(String ids) throws Exception {
        ArrayList<News> list = newsDao.findNewsByIds(ids);
        newsDao.delNewsList(list);
    }
}
