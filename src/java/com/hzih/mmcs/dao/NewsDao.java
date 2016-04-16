package com.hzih.mmcs.dao;

import com.hzih.mmcs.domain.News;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-9
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public interface NewsDao {
    public ArrayList<News> findNews()throws Exception;
    public ArrayList<News> findNews(final int start, final int limit)throws Exception;
    public ArrayList<News> findNewsById(int id)throws Exception;
    public ArrayList<News> findNewsByIds(String ids)throws Exception;
    public void addNews(News news)throws Exception;
    public void delNews(News news)throws Exception;
    public void updNews(News news)throws Exception;
    public void delNewsList(ArrayList<News> list)throws Exception;
}
