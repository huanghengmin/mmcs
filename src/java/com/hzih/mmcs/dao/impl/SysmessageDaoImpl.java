package com.hzih.mmcs.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.SysmessageDao;
import com.hzih.mmcs.domain.Sysmessage;
import com.hzih.mmcs.utils.StringUtils;
import com.hzih.mmcs.vo.MessageUserVo;
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
 * Date: 13-1-10
 * Time: 下午2:30
 * To change this template use File | Settings | File Templates.
 */
public class SysmessageDaoImpl extends MyDaoSupport implements SysmessageDao{

    @Override
    public ArrayList<MessageUserVo> findMessageUserVos()throws Exception {
//        String hql = "select new com.hzih.mmcs.vo.MessageUserVo(s.id, s.msgName, s.msgType, u.username, u.unit, u.roleids, s.ifchecked, s.sendTime, s.checkedTime) from com.hzih.mmcs.domain.Sysmessage s,com.hzih.mmcs.domain.User u where u.id=cast(s.msgAdminId as integer)";
        String hql = "select new com.hzih.mmcs.vo.MessageUserVo(s.id, s.msgName, s.msgType, u.userName, o.orgname, r.name, s.ifchecked, s.sendTime, s.checkedTime) from com.hzih.mmcs.domain.AccountRole ar,com.hzih.mmcs.domain.Role r,com.hzih.mmcs.domain.Sysmessage s,com.hzih.mmcs.domain.Account u,com.hzih.mmcs.domain.Orgcode o where r.id=ar.roleId and ar.accountId=u.id and u.orgCode=o.orgcode and s.msgAdminId=cast(u.id as string) order by s.sendTime desc";
        ArrayList<MessageUserVo> list = (ArrayList<MessageUserVo>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<MessageUserVo> findMessageUserVos(final int start, final int limit)throws Exception {
//        final String hql = "select new com.hzih.mmcs.vo.MessageUserVo(s.id, s.msgName, s.msgType, u.username, u.zonename, u.roleids, s.ifchecked, s.sendTime, s.checkedTime) from com.hzih.mmcs.domain.Sysmessage s,com.hzih.mmcs.domain.User u where u.id=cast(s.msgAdminId as integer) order by s.sendTime desc";
        final String hql = "select new com.hzih.mmcs.vo.MessageUserVo(s.id, s.msgName, s.msgType, u.userName, o.orgname, r.name, s.ifchecked, s.sendTime, s.checkedTime) from com.hzih.mmcs.domain.AccountRole ar,com.hzih.mmcs.domain.Role r,com.hzih.mmcs.domain.Sysmessage s,com.hzih.mmcs.domain.Account u,com.hzih.mmcs.domain.Orgcode o where r.id=ar.roleId and ar.accountId=u.id and u.orgCode=o.orgcode and s.msgAdminId=cast(u.id as string) order by s.sendTime desc";
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
        return (ArrayList<MessageUserVo>) list;
    }

    @Override
    public ArrayList<MessageUserVo> findMessageUserVosByMsgAdminId(String msgAdminId) throws Exception{
//        String hql = "select new com.hzih.mmcs.vo.MessageUserVo(s.id, s.msgName, s.msgType, u.username, u.unit, u.roleids, s.ifchecked, s.sendTime, s.checkedTime) from com.hzih.mmcs.domain.Sysmessage s,com.hzih.mmcs.domain.User u where u.id=cast(s.msgAdminId as integer)";
        String hql = "select new com.hzih.mmcs.vo.MessageUserVo(s.id, s.msgName, s.msgType, u.userName, o.orgname, r.name, s.ifchecked, s.sendTime, s.checkedTime) from com.hzih.mmcs.domain.AccountRole ar,com.hzih.mmcs.domain.Role r,com.hzih.mmcs.domain.Sysmessage s,com.hzih.mmcs.domain.Account u,com.hzih.mmcs.domain.Orgcode o where r.id=ar.roleId and ar.accountId=u.id and u.orgCode=o.orgcode and s.msgAdminId='"+msgAdminId+"' and s.msgAdminId=cast(u.id as string) order by s.sendTime desc";
        ArrayList<MessageUserVo> list = (ArrayList<MessageUserVo>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ArrayList<MessageUserVo> findMessageUserVosByMsgAdminId(String msgAdminId,final int start, final int limit) throws Exception{
//        final String hql = "select new com.hzih.mmcs.vo.MessageUserVo(s.id, s.msgName, s.msgType, u.username, u.zonename, u.roleids, s.ifchecked, s.sendTime, s.checkedTime) from com.hzih.mmcs.domain.Sysmessage s,com.hzih.mmcs.domain.User u where u.id=cast(s.msgAdminId as integer) order by s.sendTime desc";
        final String hql = "select new com.hzih.mmcs.vo.MessageUserVo(s.id, s.msgName, s.msgType, u.userName, o.orgname, r.name, s.ifchecked, s.sendTime, s.checkedTime) from com.hzih.mmcs.domain.AccountRole ar,com.hzih.mmcs.domain.Role r,com.hzih.mmcs.domain.Sysmessage s,com.hzih.mmcs.domain.Account u,com.hzih.mmcs.domain.Orgcode o where r.id=ar.roleId and ar.accountId=u.id and u.orgCode=o.orgcode and s.msgAdminId='"+msgAdminId+"' and s.msgAdminId=cast(u.id as string) order by s.sendTime desc";
//        List list = this.getHibernateTemplate().find(hql);
//        if( list.size()!=0 ){
//        }
        List list = getHibernateTemplate().executeFind(
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
        return (ArrayList<MessageUserVo>) list;
    }

    @Override
    public ArrayList<Sysmessage> findSysmessagesById(int id) throws Exception{
        String hql = "select t from com.hzih.mmcs.domain.Sysmessage t where t.id=?";
        ArrayList<Sysmessage> list = (ArrayList<Sysmessage>) this.getHibernateTemplate().find(hql,id);
        return list;
    }

    @Override
    public ArrayList<Sysmessage> findSysmessagesByIds(String ids) throws Exception{
        String hql = "select t from com.hzih.mmcs.domain.Sysmessage t where t.id in ("+ids+")";
        ArrayList<Sysmessage> list = (ArrayList<Sysmessage>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public void addSysmessage(Sysmessage sysmessage) throws Exception{
        this.getHibernateTemplate().save(sysmessage);
    }

    @Override
    public void delSysmessagesList(ArrayList<Sysmessage> list)throws Exception {
        this.getHibernateTemplate().deleteAll(list);
    }

    @Override
    public void updSysmessage(Sysmessage sysmessage) throws Exception{
        this.getHibernateTemplate().update(sysmessage);
    }

    @Override
    public PageResult listSendByPage(Long id, int pageIndex, int limit)throws Exception {
        String hql = "from Sysmessage where 1=1 and idalertmatter="+id+" order by sendTime desc";
		List paramsList = new ArrayList(1);
		String countHql = "select count(*) " + hql;

		PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
				pageIndex, limit);
		return ps;
    }

    @Override
    public PageResult listGetByPageBy(Long id, int pageIndex, int limit) throws Exception{
        String hql = "from Sysmessage where 1=1 and msgAdminId="+id+" order by sendTime desc";
        List paramsList = new ArrayList(1);
        String countHql = "select count(*) " + hql;

        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
                pageIndex, limit);
        return ps;
    }

    @Override
    public String findAccountByNotGet(long getid) throws Exception{
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteBySendOrReceiveId(String id) throws Exception {
        String hql = new String("from Sysmessage where idalertmatter="+id+" or msgAdminId="+id);
        ArrayList<Sysmessage> list = (ArrayList<Sysmessage>) getHibernateTemplate().find(hql);
        this.delSysmessagesList(list);
    }

    public void setEntityClass() {
		this.entityClass = Sysmessage.class;
	}


}
