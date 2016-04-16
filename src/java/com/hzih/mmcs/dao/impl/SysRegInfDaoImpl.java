package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.SysRegInfDao;
import com.hzih.mmcs.domain.SysRegInf;
import com.hzih.mmcs.utils.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SysRegInfDaoImpl extends MyDaoSupport implements SysRegInfDao {
	private final static Logger logger = Logger.getLogger(SysRegInfDaoImpl.class);

	@Override
	public void setEntityClass() {
		this.entityClass = SysRegInf.class;
	}

    @Override
    public PageResult findByIdSystem(int pageIndex, int limit, String idsystem)throws Exception {
        StringBuffer sb = new StringBuffer(" from SysRegInf s where 1=1");
        List params = new ArrayList(1);// 手动指定容量，避免多次扩容
        if (StringUtils.isNotBlank(idsystem)) {
            sb.append(" and idsystem = ?");
            params.add(idsystem);
        }
        String countString = "select count(*) " + sb.toString();

        String queryString = sb.toString();

        PageResult ps = this.findByPage(queryString, countString, params
                .toArray(), pageIndex, limit);
        logger.debug(ps == null ? "ps=null" : "ps.results.size:"
                + ps.getResults().size());
        return ps;
    }

    public String findSystemName(String idsystem)throws Exception {
        String hql = new String("from SysRegInf  where idsystem=?");
        List<SysRegInf> list = getHibernateTemplate().find(hql,new String[] {idsystem});
        if (list != null && list.size() > 0) {
            return list.get(0).getSystemname();
        } else {
            return "没有"+idsystem+"的注册信息";
        }
    }

    @Override
    public String findSystemNameLmy(String idsystem)throws Exception {
        String hql = new String("from SysRegInf  where idsystem=?");
        List<SysRegInf> list = getHibernateTemplate().find(hql,new String[] {idsystem});
        if (list != null && list.size() > 0) {
            return list.get(0).getSystemname();
        } else {
            return null;
        }
    }

    public int findBuildDepart(String builder) throws Exception{
        String hql;
        if(builder.equalsIgnoreCase("")){
            hql = "select count(*) num from sysreginf";
        } else{
            hql = "select count(*) num from sysreginf where buildingUnitCode='"+builder+"'";
        }
        Session session = getSession();
        Connection connection = session.connection();
        ResultSet rs=null;
        Statement statement=null;
        int sum = 0;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(hql);
            if(rs.next()){
                sum = rs.getInt("num");
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
        return sum;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
