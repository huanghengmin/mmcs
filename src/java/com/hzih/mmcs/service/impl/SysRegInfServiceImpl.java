package com.hzih.mmcs.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.*;
import com.hzih.mmcs.domain.BuildingUnitCode;
import com.hzih.mmcs.domain.Department;
import com.hzih.mmcs.domain.District;
import com.hzih.mmcs.domain.SysRegInf;
import com.hzih.mmcs.service.SysRegInfService;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-10
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public class SysRegInfServiceImpl implements SysRegInfService {
    private SysRegInfDao SysRegInfDao;
    private DepartmentDao departmentDao;
    private DistrictDao districtDao;
    private BuildingUnitCodeDao buildingUnitCodeDao;

    public SysRegInfDao getSysRegInfDao() {
        return SysRegInfDao;
    }

    public void setSysRegInfDao(SysRegInfDao SysRegInfDao) {
        this.SysRegInfDao = SysRegInfDao;
    }


    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }

    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public DistrictDao getDistrictDao() {
        return districtDao;
    }

    public void setDistrictDao(DistrictDao districtDao) {
        this.districtDao = districtDao;
    }

    public BuildingUnitCodeDao getBuildingUnitCodeDao() {
        return buildingUnitCodeDao;
    }

    public void setBuildingUnitCodeDao(BuildingUnitCodeDao buildingUnitCodeDao) {
        this.buildingUnitCodeDao = buildingUnitCodeDao;
    }

    @Override
    public String findByIdSystem(int pageIndex, int limit, String idsystem) throws Exception {
        PageResult pageResult = SysRegInfDao.findByIdSystem(pageIndex, limit, idsystem);
        String json = null;
        StringBuilder json2=new StringBuilder();
        StringBuilder json1=new StringBuilder();
        json1.append( "{success:true,total:");
        List<SysRegInf> SysRegInfes = pageResult.getResults();
        for (SysRegInf sysRegInf : SysRegInfes) {
            District approveunit=(District)districtDao.getById(Long.valueOf(sysRegInf.getApproveUnit()));
            District constructunit=(District)districtDao.getById(Long.valueOf(sysRegInf.getConstructUnit()));
            District maintainunit=(District)districtDao.getById(Long.valueOf(sysRegInf.getMaintainUnit()));
            Department systemclass= (Department) departmentDao.getById(sysRegInf.getSystemclass());
            BuildingUnitCode buildingUnitCode= (BuildingUnitCode) buildingUnitCodeDao.getById(sysRegInf.getBuildingUnitCode());
            json2.append("{id:'"+sysRegInf.getId()+"',idsystem:'"+sysRegInf.getIdsystem()+"',address:'"+sysRegInf.getAddress());
            json2.append("',approvematerial:'"+sysRegInf.getApproveMaterial());    //审批材料下载地址
            json2.append("',approveno:'"+sysRegInf.getApproveNo());
            if(approveunit!=null){
                json2.append("',approveunit:'"+approveunit.getDistrictName());
            }   else {
                json2.append("',approveunit:'未知单位");
            }
            if(buildingUnitCode!=null){
                json2.append("',buildingunitcode:'"+buildingUnitCode.getBuildingunitname());
            }else {
                json2.append("',buildingunitcode:'未知承建单位");
            }
            if(constructunit!=null){
                json2.append("',constructunit:'"+constructunit.getDistrictName());
            }  else {
                json2.append("',constructunit:'未知单位");
            }
            json2.append("',ifpassed:'"+sysRegInf.getIfpassed());
            json2.append("',maintainmanager:'"+sysRegInf.getMaintainManager());
            json2.append("',maintainmanagerLink:'"+sysRegInf.getMaintainManagerLink());
            json2.append("',maintainmanagerMail:'"+sysRegInf.getMaintainManagerMail());
            json2.append("',maintainmanagerPhone:'"+sysRegInf.getMaintainManagerPhone());
            if(maintainunit!=null){
                json2.append("',maintainunit:'"+maintainunit.getDistrictName());
            } else {
                json2.append("',maintainunit:'未知单位");
            }
            json2.append("',manager:'"+sysRegInf.getManager());
            json2.append("',managermail:'"+sysRegInf.getManagerMail());
            json2.append("',managerotherlink:'"+sysRegInf.getManagerOtherlink());
            json2.append("',managerphone:'"+sysRegInf.getManagerPhone());
            json2.append("',remoteaccessip:'"+sysRegInf.getRemoteaccessIp());
            json2.append("',secrecyprotocol:'"+sysRegInf.getSecrecyProtocol());
            json2.append("',securityproject:'"+sysRegInf.getSecurityProject());
            json2.append("',signsecrecyprotocol:'"+sysRegInf.getSignSecrecyprotocol());
            if(systemclass!=null){
                json2.append("',systemclass:'"+systemclass.getName());
            }else {
                json2.append("',systemclass:'未知类型");
            }
            json2.append("',systemmap:'"+sysRegInf.getSystemMap());
            json2.append("',systemname:'"+sysRegInf.getSystemname());
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String approvetime = df1.format(sysRegInf.getApproveTime());
            String buildingtime = df1.format(sysRegInf.getBuildingTime());
            String collecttime = df1.format(sysRegInf.getCollecttime());
            json2.append( "',approvetime:'"+approvetime);
            json2.append( "',buildingtime:'"+buildingtime);
            json2.append( "',collecttime:'"+collecttime+"'},");
        }
        json1.append(SysRegInfes.size()+ ",rows:[");
        json=json1.toString()+json2.toString();
        json += "]}";
        json=json.replace("},]}","}]}");
        json=json.replace("null","未知");
        return json;
    }

    public String finbBuildDepart() throws Exception{
        int ys = SysRegInfDao.findBuildDepart("YS");
        int xd = SysRegInfDao.findBuildDepart("XD");
        int cr = SysRegInfDao.findBuildDepart("CR");
        int sum = SysRegInfDao.findBuildDepart("");
        String json = "{builder:'一所',count:"+ys+"},{builder:'信大',count:"+xd+"},{builder:'辰锐',count:"+cr+"},{builder:'其他',count:"+(sum-ys-xd-cr)+"}";
        return json;
    }

}
