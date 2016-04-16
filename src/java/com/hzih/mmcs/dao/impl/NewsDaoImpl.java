package com.hzih.mmcs.dao.impl;

import com.hzih.mmcs.dao.NewsDao;
import com.hzih.mmcs.domain.News;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-9
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
public class NewsDaoImpl extends HibernateDaoSupport implements NewsDao{

    @Override
    public ArrayList<News> findNews() throws Exception{
        String hql = "select t from com.hzih.mmcs.domain.News t order by t.time desc";
        ArrayList<News> list = (ArrayList<News>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<News> findNews(final int start, final int limit) throws Exception{
        final String hql = "select t from com.hzih.mmcs.domain.News t order by t.time desc";
        List list = this.getHibernateTemplate().find(hql);
        if( list.size()!=0 ){
            list = getHibernateTemplate().executeFind(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session) throws HibernateException, SQLException {
                            Query query = session.createQuery(hql);
                            query.setFirstResult(start);
                            query.setMaxResults(limit);
                            List list = query.list();
                            return list;
                        }
                    }
            );
        }
        return (ArrayList<News>) list;
    }

    @Override
    public ArrayList<News> findNewsById(int id) throws Exception{
        String hql = "select t from com.hzih.mmcs.domain.News t where t.nid=?";
        ArrayList<News> list = (ArrayList<News>) this.getHibernateTemplate().find(hql,id);
        return list;
    }

    @Override
    public ArrayList<News> findNewsByIds(String ids) throws Exception{
        String hql = "select t from com.hzih.mmcs.domain.News t where t.nid in ("+ids+")";
        ArrayList<News> list = (ArrayList<News>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public void addNews(News news) throws Exception{
        this.getHibernateTemplate().save(news);
    }

    @Override
    public void delNews(News news) throws Exception{
        this.getHibernateTemplate().delete(news);
    }

    @Override
    public void updNews(News news)throws Exception {
        this.getHibernateTemplate().update(news);
    }

    @Override
    public void delNewsList(ArrayList<News> list) throws Exception{
        this.getHibernateTemplate().deleteAll(list);
    }
}
