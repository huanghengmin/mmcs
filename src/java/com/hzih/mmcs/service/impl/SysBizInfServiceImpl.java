package com.hzih.mmcs.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.*;
import com.hzih.mmcs.domain.*;
import com.hzih.mmcs.service.SysBizInfService;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-10
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public class SysBizInfServiceImpl implements SysBizInfService {
    private SysBizInfDao SysBizInfDao;
    private BizModeCodeDao bizModeCodeDao;
    private BizFuncTypeCodeDao bizFuncTypeCodeDao;
    private BizTypeCodeDao bizTypeCodeDao;
    private DepartmentDao departmentDao;
    private DeviceTypeCodeDao deviceTypeCodeDao;
    private TerminalTypeCodeDao terminalTypeCodeDao;
    private DistrictDao districtDao;

    public DistrictDao getDistrictDao() {
        return districtDao;
    }

    public void setDistrictDao(DistrictDao districtDao) {
        this.districtDao = districtDao;
    }

    public SysBizInfDao getSysBizInfDao() {
        return SysBizInfDao;
    }

    public void setSysBizInfDao(SysBizInfDao SysBizInfDao) {
        this.SysBizInfDao = SysBizInfDao;
    }

    public BizModeCodeDao getBizModeCodeDao() {
        return bizModeCodeDao;
    }

    public void setBizModeCodeDao(BizModeCodeDao bizModeCodeDao) {
        this.bizModeCodeDao = bizModeCodeDao;
    }

    public BizFuncTypeCodeDao getBizFuncTypeCodeDao() {
        return bizFuncTypeCodeDao;
    }

    public void setBizFuncTypeCodeDao(BizFuncTypeCodeDao bizFuncTypeCodeDao) {
        this.bizFuncTypeCodeDao = bizFuncTypeCodeDao;
    }

    public BizTypeCodeDao getBizTypeCodeDao() {
        return bizTypeCodeDao;
    }

    public void setBizTypeCodeDao(BizTypeCodeDao bizTypeCodeDao) {
        this.bizTypeCodeDao = bizTypeCodeDao;
    }

    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }

    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public DeviceTypeCodeDao getDeviceTypeCodeDao() {
        return deviceTypeCodeDao;
    }

    public void setDeviceTypeCodeDao(DeviceTypeCodeDao deviceTypeCodeDao) {
        this.deviceTypeCodeDao = deviceTypeCodeDao;
    }

    public TerminalTypeCodeDao getTerminalTypeCodeDao() {
        return terminalTypeCodeDao;
    }

    public void setTerminalTypeCodeDao(TerminalTypeCodeDao terminalTypeCodeDao) {
        this.terminalTypeCodeDao = terminalTypeCodeDao;
    }

    @Override
    public String findByIdSystem(int pageIndex, int limit, String idsystem) throws Exception {
        PageResult pageResult = SysBizInfDao.findByIdSystem(pageIndex, limit, idsystem);
        String json = null;
        StringBuilder json2=new StringBuilder();
        StringBuilder json1=new StringBuilder();
        json1.append( "{success:true,total:");
        int total=pageResult.getAllResultsAmount();
        List<SysBizInf> SysBizInfes = pageResult.getResults();
        for (SysBizInf sysBizInf : SysBizInfes) {
            BizModeCode bizModeCode= (BizModeCode) bizModeCodeDao.getById(sysBizInf.getBizMode());
            BizFuncTypeCode bizFuncTypeCode=null;
            String bizfunctype="";
            if(sysBizInf.getBizfunctype().contains("&")){
                String[] list=sysBizInf.getBizfunctype().split("&");
                for (int i=0;i<list.length;i++){
                    bizFuncTypeCode=(BizFuncTypeCode)bizFuncTypeCodeDao.getById(list[i]);
                    bizfunctype+=bizFuncTypeCode.getBizfunctypename()+",";
                }
                bizFuncTypeCode.setBizfunctypename(bizfunctype.substring(0,bizfunctype.length()-1));
            }else {
                bizFuncTypeCode=(BizFuncTypeCode)bizFuncTypeCodeDao.getById(sysBizInf.getBizfunctype());
            }
            BizTypeCode bizTypeCode=(BizTypeCode)bizTypeCodeDao.getById(sysBizInf.getBizType());
            Department department=(Department)departmentDao.getById(sysBizInf.getBackupUnitname());
            Department departmentBiz=(Department)departmentDao.getById(sysBizInf.getBizManagedepart());
            DeviceTypeCode deviceTypeCode=(DeviceTypeCode)deviceTypeCodeDao.getById(String.valueOf(sysBizInf.getIdbiz()));
            District approveunit=(District)districtDao.getById(Long.valueOf(sysBizInf.getApproveUnit()));
            TerminalTypeCode bizTerminal=null;
            String bizt="";
            if(sysBizInf.getBizTerminal().contains("&")){
                String[] list=sysBizInf.getBizTerminal().split("&");
                for (int i=0;i<list.length;i++){
                    bizTerminal=(TerminalTypeCode)terminalTypeCodeDao.getById(list[i]);
                    bizt+=bizTerminal.getTerminaltype()+",";
                }
                bizTerminal.setTerminaltype(bizt.substring(0,bizt.length()-1));
            }else {
                bizTerminal=(TerminalTypeCode)terminalTypeCodeDao.getById(sysBizInf.getBizTerminal());
            }

            json2.append("{id:'"+sysBizInf.getId()+"',idsystem:'"+sysBizInf.getIdsystem()+"',approveMaterial:'"+sysBizInf.getApproveMaterial());
            json2.append("',approveNo:'"+sysBizInf.getApproveNo());
            if(approveunit!=null){
                json2.append("',approveUnit:'"+approveunit.getDistrictName());
            }
            json2.append("',backup:'"+sysBizInf.getBackup());
            if(bizFuncTypeCode!=null){
                json2.append("',bizfunctype:'"+bizFuncTypeCode.getBizfunctypename());
            }
            if(departmentBiz!=null){
                json2.append("',bizManagedepart:'"+departmentBiz.getName());
            }
            if(bizModeCode!=null){
                json2.append("',bizMode:'"+bizModeCode.getBizmodename());
            }
            if(bizTerminal!=null){
                json2.append("',bizTerminal:'"+bizTerminal.getTerminaltype());
            }
            if(bizTypeCode!=null){
                json2.append("',bizType:'"+bizTypeCode.getBiztype());
            }
            if(department!=null){
                json2.append("',backupUnitname:'"+department.getName());
            }
            json2.append("',dateexchangeDateflux:'"+sysBizInf.getDateexchangeDateflux());
            json2.append("',managedepartLink:'"+sysBizInf.getManagedepartLink());
            json2.append("',managedepartMail:'"+sysBizInf.getManagedepartMail());
            json2.append("',managedepartManager:'"+sysBizInf.getManagedepartManager());
            json2.append("',managedepartPhone:'"+sysBizInf.getManagedepartPhone());
            json2.append("',realtime:'"+sysBizInf.getRealtime());
            json2.append("',getTopologyMap:'"+sysBizInf.getTopologyMap());
            if(deviceTypeCode!=null){
                json2.append("',getIdbiz:'"+deviceTypeCode.getDevicetypename());
            }
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(sysBizInf.getRegisterTime()==null){
                json2.append( "',registerTime:'"+null);
            }else {
                String registerTime = df1.format(sysBizInf.getRegisterTime());
                json2.append( "',registerTime:'"+registerTime);
            }
            if(sysBizInf.getApproveTime()==null){
                json2.append( "',approveTime:'"+null);
            }else {
                String approveTime = df1.format(sysBizInf.getApproveTime());
                json2.append( "',approveTime:'"+approveTime);
            }
            if(sysBizInf.getCollecttime()==null){
                json2.append( "',collecttime:'"+null+"'},");
            }else {
                String collecttime = df1.format(sysBizInf.getCollecttime());
                json2.append( "',collecttime:'"+collecttime+"'},");
            }
        }
        json1.append(total+ ",rows:[");
        json=json1.toString()+json2.toString();
        json += "]}";
        json=json.replace("},]}","}]}");
        json=json.replace("null","未知");
        return json;
    }

}
