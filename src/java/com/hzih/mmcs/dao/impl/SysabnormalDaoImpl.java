package com.hzih.mmcs.dao.impl;

import com.hzih.mmcs.dao.SysabnormalDao;
import com.hzih.mmcs.vo.AbnormalVo;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-11
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class SysabnormalDaoImpl extends HibernateDaoSupport implements SysabnormalDao{

    @Override
    public ArrayList<AbnormalVo> findAbnormaVos(String idsystem) throws Exception{
        String hql = "select new com.hzih.mmcs.vo.AbnormalVo(s.id, s.idalertmatter, t.abnormaltype, o.abnormalobject, s.exceptiondesc, s.happentime, s.treattime, r.treadresult, s.ifcheckin) from com.hzih.mmcs.domain.Sysabnormal s, com.hzih.mmcs.domain.Abnormalobjectcode o, com.hzih.mmcs.domain.Abnormaltypecode t, com.hzih.mmcs.domain.Treadresultcode r" +
                " where s.abnormaltypecode=t.abnormaltypecode and s.connectobjectcode=o.abnormalobjectcode and s.treadresult=r.treadresultcode and s.idsystem like '"+idsystem+"%'";
        ArrayList<AbnormalVo> list = (ArrayList<AbnormalVo>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<AbnormalVo> findAbnormaVos(String idsystem,final int start, final int limit) throws Exception{
        final String hql = "select new com.hzih.mmcs.vo.AbnormalVo(s.id, s.idalertmatter, t.abnormaltype, o.abnormalobject, s.exceptiondesc, s.happentime, s.treattime, r.treadresult, s.ifcheckin) from com.hzih.mmcs.domain.Sysabnormal s, com.hzih.mmcs.domain.Abnormalobjectcode o, com.hzih.mmcs.domain.Abnormaltypecode t, com.hzih.mmcs.domain.Treadresultcode r" +
                " where s.abnormaltypecode=t.abnormaltypecode and s.connectobjectcode=o.abnormalobjectcode and s.treadresult=r.treadresultcode and s.idsystem like '"+idsystem+"%'";
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
        return (ArrayList<AbnormalVo>) list;
    }

    @Override
    public int findAbnormalNumByMonth(String zonelike,String treadresult) throws Exception{
        String sql="select count(*) num from sysabnormal where treadresult like '"+treadresult+"%' and" +
                " idsystem like '"+zonelike+"%' and" +
                " date_format(happentime,'%Y-%m')=date_format(now(),'%Y-%m')";
        Session session = getSession();
        Connection connection = session.connection();
        ResultSet rs=null;
        Statement statement=null;
        int rowCount = 0;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            if(rs.next()){
                rowCount = rs.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            try {
                rs.close();
                statement.close();
                connection.close();
                session.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return rowCount;
    }

    @Override
    public int findAbnormalNumByYear(String zonelike,String treadresult)throws Exception {
        String sql="select count(*) num from sysabnormal where treadresult like '"+treadresult+"%' and" +
                " idsystem like '"+zonelike+"%' and" +
                " YEAR(happentime)=YEAR(NOW())";
        Session session = getSession();
        Connection connection = session.connection();
        int rowCount = 0;
        ResultSet rs=null;
        Statement statement=null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            if(rs.next()){
                rowCount = rs.getInt("num");
            }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            try {
                rs.close();
                statement.close();
                connection.close();
                session.close();
            } catch (SQLException e) {
                throw e;
            }
        }
        return rowCount;
    }

    @Override
    public ArrayList<String> findIdsystemRS(String idsystem) throws SQLException {
        String sql="select idsystem from stat_sysabnormal group by idsystem having idsystem like '"+idsystem+"%'";
        Session session = getSession();
        Connection connection = session.connection();
        ResultSet rs=null;
        Statement statement=null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()){
                list.add(rs.getString("idsystem"));
            }
        } catch (SQLException e) {
            throw e;
        }finally {
            try {
                rs.close();
                statement.close();
                connection.close();
                session.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return list;
    }

    @Override
    public HashMap<String,Integer> findAbnormalAllNumByIdsystem(String idsystem) throws SQLException {
        String sql="select sum(monthabsum) mNum, sum(monthhandledabsum) mtreadNum, sum(monthunhandledabsum) mNtreadNum, sum(yearabsum) yNum, sum(yearhandledabsum) ytreadNum, sum(yearunhandledabsum) yNtreadNum from stat_sysabnormal where idsystem like '"+idsystem+"%'";
        Session session = getSession();
        Connection connection = session.connection();
        ResultSet rs=null;
        Statement statement=null;
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            if(rs.next()){
                map.put("mNum",rs.getInt("mNum"));
                map.put("mtreadNum",rs.getInt("mtreadNum"));
                map.put("mNtreadNum",rs.getInt("mNtreadNum"));
                map.put("yNum",rs.getInt("yNum"));
                map.put("ytreadNum",rs.getInt("ytreadNum"));
                map.put("yNtreadNum",rs.getInt("yNtreadNum"));
            }
            
        } catch (SQLException e) {
            throw e;
        }finally {
            try {
                rs.close();
                statement.close();
                connection.close();
                session.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return map;
    }

    public ArrayList<AbnormalVo> findAbnormaVosByMonth(String idsystem,String treadresult) throws Exception{
        String hql = "select new com.hzih.mmcs.vo.AbnormalVo(s.id, s.idalertmatter, t.abnormaltype, o.abnormalobject, s.exceptiondesc, s.happentime, s.treattime, r.treadresult, s.ifcheckin) from com.hzih.mmcs.domain.Sysabnormal s, com.hzih.mmcs.domain.Abnormalobjectcode o, com.hzih.mmcs.domain.Abnormaltypecode t, com.hzih.mmcs.domain.Treadresultcode r" +
                " where s.abnormaltypecode=t.abnormaltypecode and s.connectobjectcode=o.abnormalobjectcode and s.treadresult=r.treadresultcode and s.idsystem like '"+idsystem+"%' and  date_format(s.happentime,'%Y-%m')=date_format(now(),'%Y-%m') and s.treadresult like '"+treadresult+"%'";
        ArrayList<AbnormalVo> list = (ArrayList<AbnormalVo>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<AbnormalVo> findAbnormaVosByMonth(String idsystem,final int start, final int limit,String treadresult) throws Exception{
        final String hql = "select new com.hzih.mmcs.vo.AbnormalVo(s.id, s.idalertmatter, t.abnormaltype, o.abnormalobject, s.exceptiondesc, s.happentime, s.treattime, r.treadresult, s.ifcheckin) from com.hzih.mmcs.domain.Sysabnormal s, com.hzih.mmcs.domain.Abnormalobjectcode o, com.hzih.mmcs.domain.Abnormaltypecode t, com.hzih.mmcs.domain.Treadresultcode r" +
                " where s.abnormaltypecode=t.abnormaltypecode and s.connectobjectcode=o.abnormalobjectcode and s.treadresult=r.treadresultcode and s.idsystem like '"+idsystem+"%' and  date_format(s.happentime,'%Y-%m')=date_format(now(),'%Y-%m') and s.treadresult like '"+treadresult+"%'";
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
        return (ArrayList<AbnormalVo>) list;
    }

    @Override
    public ArrayList<AbnormalVo> findAbnormaVosByYear(String idsystem,String treadresult) throws Exception{
        String hql = "select new com.hzih.mmcs.vo.AbnormalVo(s.id, s.idalertmatter, t.abnormaltype, o.abnormalobject, s.exceptiondesc, s.happentime, s.treattime, r.treadresult, s.ifcheckin) from com.hzih.mmcs.domain.Sysabnormal s, com.hzih.mmcs.domain.Abnormalobjectcode o, com.hzih.mmcs.domain.Abnormaltypecode t, com.hzih.mmcs.domain.Treadresultcode r" +
                " where s.abnormaltypecode=t.abnormaltypecode and s.connectobjectcode=o.abnormalobjectcode and s.treadresult=r.treadresultcode and s.idsystem like '"+idsystem+"%' and  YEAR(s.happentime)=YEAR(NOW()) and s.treadresult like '"+treadresult+"%'";
        ArrayList<AbnormalVo> list = (ArrayList<AbnormalVo>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<AbnormalVo> findAbnormaVosByYear(String idsystem,final int start, final int limit,String treadresult) throws Exception{
        final String hql = "select new com.hzih.mmcs.vo.AbnormalVo(s.id, s.idalertmatter, t.abnormaltype, o.abnormalobject, s.exceptiondesc, s.happentime, s.treattime, r.treadresult, s.ifcheckin) from com.hzih.mmcs.domain.Sysabnormal s, com.hzih.mmcs.domain.Abnormalobjectcode o, com.hzih.mmcs.domain.Abnormaltypecode t, com.hzih.mmcs.domain.Treadresultcode r" +
                " where s.abnormaltypecode=t.abnormaltypecode and s.connectobjectcode=o.abnormalobjectcode and s.treadresult=r.treadresultcode and s.idsystem like '"+idsystem+"%' and  YEAR(s.happentime)=YEAR(NOW()) and s.treadresult like '"+treadresult+"%'";
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
        return (ArrayList<AbnormalVo>) list;
    }
}
