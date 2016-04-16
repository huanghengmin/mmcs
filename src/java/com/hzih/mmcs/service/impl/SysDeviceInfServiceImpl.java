package com.hzih.mmcs.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.SysDeviceInfDao;
import com.hzih.mmcs.domain.SysDeviceInf;
import com.hzih.mmcs.service.SysDeviceInfService;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-10
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public class SysDeviceInfServiceImpl implements SysDeviceInfService {
    private SysDeviceInfDao SysDeviceInfDao;

    public SysDeviceInfDao getSysDeviceInfDao() {
        return SysDeviceInfDao;
    }

    public void setSysDeviceInfDao(SysDeviceInfDao SysDeviceInfDao) {
        this.SysDeviceInfDao = SysDeviceInfDao;
    }


    @Override
    public String findByIdSystem(int pageIndex, int limit, String idsystem) throws Exception {
        PageResult pageResult = SysDeviceInfDao.findByIdSystem(pageIndex, limit, idsystem);
        String json = null;
        StringBuilder json2=new StringBuilder();
        StringBuilder json1=new StringBuilder();
        json1.append( "{success:true,total:");
        int total=pageResult.getAllResultsAmount();
        List<SysDeviceInf> SysDeviceInfes = pageResult.getResults();
        for (SysDeviceInf sysDeviceInf : SysDeviceInfes) {
            json2.append("{id:'"+sysDeviceInf.getId()+"',idsystem:'"+sysDeviceInf.getIdsystem()+"',brandtyoe:'"+sysDeviceInf.getBrandtyoe());
            json2.append("',devicedesc:'"+sysDeviceInf.getDevicedesc());
            json2.append("',deviceIP:'"+sysDeviceInf.getDeviceIP());
            json2.append("',devicetypecode:'"+sysDeviceInf.getDevicetypecode());
            json2.append("',iddevice:'"+sysDeviceInf.getIddevice());
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String collecttime = df1.format(sysDeviceInf.getCollecttime());
            json2.append( "',collecttime:'"+collecttime+"'},");
        }
        json1.append(total+ ",rows:[");
        json=json1.toString()+json2.toString();
        json += "]}";
        json=json.replace("},]}","}]}");
        return json;
    }

}
