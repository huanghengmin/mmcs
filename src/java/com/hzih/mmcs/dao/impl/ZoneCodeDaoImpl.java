package com.hzih.mmcs.dao.impl;

import com.hzih.mmcs.dao.ZoneCodeDao;
import com.hzih.mmcs.domain.ZoneCode;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-14
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public class ZoneCodeDaoImpl extends HibernateDaoSupport implements ZoneCodeDao {

    @Override
    public ArrayList<ZoneCode> findZoneByZonecodeLike(String codelike){
        String hql = new String("select z from com.hzih.mmcs.domain.ZoneCode z where z.zonecode like '"+codelike+"%'");
        ArrayList<ZoneCode> list = (ArrayList<ZoneCode>) this.getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public ZoneCode findProvince(String code) {
        String province = code.substring(0,code.length()-4);
        String hql = new String("from ZoneCode where zonecode=?");
        List<ZoneCode> list = getHibernateTemplate().find(hql,new String[] { province+"0000" });
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    @Override
    public List<ZoneCode> findProvinceCity(String provinceCode){
        String province = provinceCode.substring(0,2);
        String hql = "from ZoneCode zoneCode where zoneCode.zonecode like '"+province+"%00'";
        List<ZoneCode> list =null;
        list = getHibernateTemplate().find(hql);
        return list;
    }

    @Override
    public List<ZoneCode> findCityCounty(String code,String notLikeCode) {
        String hql = "from ZoneCode zoneCode where zoneCode.zonecode like '"+code+"%' and zoneCode.zonecode not like '"+notLikeCode+"%'";
        List<ZoneCode> list =null;
        list = getHibernateTemplate().find(hql);
        return list;
    }


    @Override
    public ZoneCode findCity(String code) {
        String province = code.substring(0,code.length()-2);
        String hql = new String("from ZoneCode where zonecode=?");
        List<ZoneCode> list = getHibernateTemplate().find(hql,new String[] { province+"00" });
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


    @Override
    public String  getZoneCodeName(String code) {
        String hql = new String("from ZoneCode where zonecode=?");
        List<ZoneCode> list = getHibernateTemplate().find(hql,new String[] { code});
        if (list != null && list.size() > 0) {
            return list.get(0).getZonename();
        } else {
            return null;
        }
    }
}
