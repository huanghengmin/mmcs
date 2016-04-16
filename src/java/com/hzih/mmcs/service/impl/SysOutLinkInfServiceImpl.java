package com.hzih.mmcs.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.OutLinkTypeCodeDao;
import com.hzih.mmcs.dao.SysOutLinkInfDao;
import com.hzih.mmcs.domain.OutLinkTypeCode;
import com.hzih.mmcs.domain.SysOutLinkInf;
import com.hzih.mmcs.service.SysOutLinkInfService;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-10
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public class SysOutLinkInfServiceImpl implements SysOutLinkInfService {
    private SysOutLinkInfDao SysOutLinkInfDao;
    private OutLinkTypeCodeDao outLinkTypeCodeDao;

    public SysOutLinkInfDao getSysOutLinkInfDao() {
        return SysOutLinkInfDao;
    }

    public void setSysOutLinkInfDao(SysOutLinkInfDao SysOutLinkInfDao) {
        this.SysOutLinkInfDao = SysOutLinkInfDao;
    }


    public OutLinkTypeCodeDao getOutLinkTypeCodeDao() {
        return outLinkTypeCodeDao;
    }

    public void setOutLinkTypeCodeDao(OutLinkTypeCodeDao outLinkTypeCodeDao) {
        this.outLinkTypeCodeDao = outLinkTypeCodeDao;
    }

    @Override
    public String findByIdSystem(int pageIndex, int limit, String idsystem) throws Exception {
        PageResult pageResult = SysOutLinkInfDao.findByIdSystem(pageIndex, limit, idsystem);
        String json = null;
        StringBuilder json2=new StringBuilder();
        StringBuilder json1=new StringBuilder();
        json1.append( "{success:true,total:");
        int total=pageResult.getAllResultsAmount();
        List<SysOutLinkInf> SysOutLinkInfes = pageResult.getResults();
        for (SysOutLinkInf sysOutLinkInf : SysOutLinkInfes) {
            OutLinkTypeCode outLinkTypeCode= (OutLinkTypeCode) outLinkTypeCodeDao.getById(sysOutLinkInf.getOutlinkvender());
            json2.append("{id:'"+sysOutLinkInf.getId()+"',idsystem:'"+sysOutLinkInf.getIdsystem()+"',outlinkdisc:'"+sysOutLinkInf.getOutlinkdisc());
            json2.append("',outlinkvender:'"+outLinkTypeCode.getName());
            json2.append("',idoutconnlink:'"+sysOutLinkInf.getIdoutconnlink());
            json2.append("',outlinkbandwidth:'"+sysOutLinkInf.getOutlinkbandwidth());
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(sysOutLinkInf.getCollecttime()==null){
                json2.append( "',collecttime:'"+null+"'},");
            }else {
                String collecttime = df1.format(sysOutLinkInf.getCollecttime());
                json2.append( "',collecttime:'"+collecttime+"'},");
            }

        }
        json1.append(total+ ",rows:[");
        json=json1.toString()+json2.toString();
        json += "]}";
        json=json.replace("},]}","}]}");
        return json;
    }

}
