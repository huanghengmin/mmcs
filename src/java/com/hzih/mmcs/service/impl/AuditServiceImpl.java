package com.hzih.mmcs.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.BusinessLogDao;
import com.hzih.mmcs.dao.EquipmentLogDao;
import com.hzih.mmcs.dao.SysLogDao;
import com.hzih.mmcs.dao.UserOperLogDao;
import com.hzih.mmcs.domain.*;
import com.hzih.mmcs.service.AuditService;
import com.hzih.mmcs.utils.DateUtils;
import com.hzih.mmcs.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 12-6-19
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
public class AuditServiceImpl implements AuditService{
    private BusinessLogDao businessLogDao;
    private SysLogDao sysLogDao;
    private UserOperLogDao userOperLogDao;
    private EquipmentLogDao equipmentLogDao;

    /**
     * 分页读取用户日志--并以json形式返回
     */
    public String selectUserAudit(int pageIndex, int limit, Date startDate, Date endDate,
                                  String logLevel, String userName) throws Exception {
        PageResult pageResult = userOperLogDao.listLogsByParams(pageIndex,limit,startDate, endDate, logLevel, userName);
        List<UserOperLog> userOperLogs = pageResult.getResults();
        int total = pageResult.getAllResultsAmount();
        String json = "{success:true,total:"+ total + ",rows:[";
        for (UserOperLog u : userOperLogs) {
            json +="{id:'"+u.getId()+"',userName:'"+u.getUserName()+"',level:'"+u.getLevel()+
                    "',auditModule:'"+u.getAuditModule()+"',auditInfo:'"+u.getAuditInfo()+
                    "',logTime:'"+DateUtils.formatDate(u.getLogTime(),"yy-MM-dd HH:mm:ss")+"'},";
        }
        json += "]}";
        return json;
    }

    /**
     * 删除符合条件的数据,如果没有条件则清空设备日志表的日志
     * @param startDate
     * @param endDate
     * @param logLevel
     * @param equipmentName
     * @throws Exception
     */
    public void deleteEquipment(String startDate, String endDate, String logLevel, String equipmentName) throws Exception {
        if(StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate) &&
                StringUtils.isBlank(logLevel) && StringUtils.isBlank(equipmentName)) {
            equipmentLogDao.truncate();
        } else {
            equipmentLogDao.delete(startDate,endDate,logLevel,equipmentName);
        }
    }

    /**
     * 删除符合条件的数据,如果没有条件则清空业务日志表的日志
     */
    public void deleteBusiness(String startDate, String endDate, String businessType, String businessName) throws Exception {
        if(StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate) &&
                StringUtils.isBlank(businessType) && StringUtils.isBlank(businessName)) {
            businessLogDao.truncate();
        } else {
            businessLogDao.delete(startDate,endDate,businessType,businessName);
        }
    }

    @Override
    public String selectOSAudit(int pageIndex, int limit, Date startDate, Date endDate, String logLevel) throws Exception {
        PageResult pageResult = sysLogDao.listLogsByParams(pageIndex,limit,startDate, endDate, logLevel);
        List<SysLog> list = pageResult.getResults();
        int total = pageResult.getAllResultsAmount();
        String json = "{success:true,total:"+ total + ",rows:[";
        for (SysLog o : list) {
            json +="{id:'"+o.getId()+"',auditAction:'"+o.getAuditAction()+"',level:'"+o.getLevel()+
                    "',auditModule:'"+o.getAuditModule()+"',auditInfo:'"+o.getAuditInfo()+
                    "',logTime:'"+DateUtils.formatDate(o.getLogTime(),"yy-MM-dd HH:mm:ss")+"'},";
        }
        json += "]}";
        return json;
    }

    /**
     *  分页读取用户日志--并以json形式返回
     */
    public String selectBusinessAudit(int pageIndex, int limit, Date startDate,
                                      Date endDate, String businessType, String businessName) throws Exception {
        PageResult pageResult = businessLogDao.listLogsByParams(pageIndex,limit,startDate,endDate,businessType,businessName);
        int total = pageResult.getAllResultsAmount();
        List<BusinessLog> list = pageResult.getResults();
        String json = "{success:true,total:" + total + ",rows:[";
        for (BusinessLog log : list) {
            json +="{id:"+log.getId()+",businessName:'"+log.getBusinessName()+"',level:'"+log.getLevel()+
                    "',businessType:'"+log.getBusinessType()+"',businessDesc:'"+log.getBusinessDesc()+
                    "',auditCount:"+log.getAuditCount()+",logTime:'"+ DateUtils.formatDate(log.getLogTime(), "yy-MM-dd HH:mm:ss")+
                    "',sourceJdbc:'"+log.getSourceJdbc()+"',destJdbc:'"+log.getDestJdbc()+
                    "',sourceIp:'"+log.getSourceIp()+"',sourcePort:'"+log.getSourcePort()+
                    "',destIp:'"+log.getDestIp()+"',destPort:'"+log.getDestPort()+
                    "',fileName:'"+log.getFileName()+"',plugin:'"+log.getPlugin()+"'},";
        }
        json += "]}";
        return json;
    }

    /**
     *    分页读取设备日志--并以json形式返回
     */
    public String selectEquipmentAudit(int pageIndex, int limit, Date startDate, Date endDate,
                                       String logLevel, String equipmentName) throws Exception {
        PageResult pageResult = equipmentLogDao.listLogsByParams(pageIndex,limit,startDate,endDate,logLevel,equipmentName);
        int total = pageResult.getAllResultsAmount();
        List<EquipmentLog> list = pageResult.getResults();
        String json = "{success:true,total:" + total + ",rows:[";
        for (EquipmentLog log : list) {
            json +="{id:'"+log.getId()+"',equipmentName:'"+log.getEquipmentName()+"',level:'"+log.getLevel()+
                        "',linkName:'"+log.getLinkName()+"',auditInfo:'"+log.getLogInfo()+
                        "',logTime:'"+DateUtils.formatDate(log.getLogTime(),"yy-MM-dd HH:mm:ss")+"'},";
        }
        json += "]}";
        return json;
    }

    public BusinessLogDao getBusinessLogDao() {
        return businessLogDao;
    }

    public void setBusinessLogDao(BusinessLogDao businessLogDao) {
        this.businessLogDao = businessLogDao;
    }

    public SysLogDao getSysLogDao() {
        return sysLogDao;
    }

    public void setSysLogDao(SysLogDao sysLogDao) {
        this.sysLogDao = sysLogDao;
    }

    public UserOperLogDao getUserOperLogDao() {
        return userOperLogDao;
    }

    public void setUserOperLogDao(UserOperLogDao userOperLogDao) {
        this.userOperLogDao = userOperLogDao;
    }

    public EquipmentLogDao getEquipmentLogDao() {
        return equipmentLogDao;
    }

    public void setEquipmentLogDao(EquipmentLogDao equipmentLogDao) {
        this.equipmentLogDao = equipmentLogDao;
    }
}
