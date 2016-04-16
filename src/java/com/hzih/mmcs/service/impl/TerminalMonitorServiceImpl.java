package com.hzih.mmcs.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.dao.*;
import com.hzih.mmcs.domain.District;
import com.hzih.mmcs.domain.Orgcode;
import com.hzih.mmcs.domain.StatSysterminalinfo;
import com.hzih.mmcs.domain.Systerminalinfo;
import com.hzih.mmcs.service.TerminalMonitorService;

import java.util.List;

public class TerminalMonitorServiceImpl implements TerminalMonitorService {
    private StatSysterminalinfoDao statSysterminalinfoDao;
    private DistrictDao districtDao;
    private SysterminalInfoDao systerminalInfoDao;
    private OrgcodeDao orgcodeDao;
    private SysRegInfDao sysRegInfDao;

    public void setSysRegInfDao(SysRegInfDao sysRegInfDao) {
        this.sysRegInfDao = sysRegInfDao;
    }

    public void setStatSysterminalinfoDao(StatSysterminalinfoDao statSysterminalinfoDao) {
        this.statSysterminalinfoDao = statSysterminalinfoDao;
    }

    public void setOrgcodeDao(OrgcodeDao orgcodeDao) {
        this.orgcodeDao = orgcodeDao;
    }

    public void setSysterminalInfoDao(SysterminalInfoDao systerminalInfoDao) {
        this.systerminalInfoDao = systerminalInfoDao;
    }

    public void setDistrictDao(DistrictDao districtDao) {
        this.districtDao = districtDao;
    }

    @Override
    public String selectAll(String zonecode) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public String indexAll(String zonecode) throws Exception {
        StringBuffer json = new StringBuffer();
        json.append("[");
        boolean provinceToCity = false;
        String provincecode = zonecode.substring(0, 2);
        String zonesql = " from District where district_id   like '" + provincecode + "%' ";
        List zonelist = districtDao.list(zonesql);
        District z = (District) zonelist.get(0);
        json.append("{zone:'" + z.getDistrictName() + "',uiProvider:'col',expanded:true,");
        totalProvince(json, zonecode);
        json.append("children:[");
        for (int i = 0; i < zonelist.size(); i++) {
            District zc = (District) zonelist.get(i);
            String zonename = String.valueOf(zc.getId());
            if (zonename.contains("0000")) {
                String hql = "from StatSysterminalinfo where idsystem like '" + zonename + "%' order by idsystem asc ";
                List terminalList = statSysterminalinfoDao.list(hql);
                if (terminalList.size() == 0) {

                } else {
                    for (int j = 0; j < terminalList.size(); j++) {
                        StatSysterminalinfo s = (StatSysterminalinfo) terminalList.get(j);
                        String idsystem = s.getIdsystem();
                        String teriminalName = sysRegInfDao.findSystemNameLmy(idsystem);
                        if (teriminalName == null) {
                            teriminalName = z.getDistrictName();
                        }
                        //省厅数据
                        json.append("{zone:'" + teriminalName + "',");
                        json.append("terminalsum:'" + s.getTerminalsum() + "',");
                        json.append("vehiclesum:'" + s.getCarterminalsum() + "',");
                        json.append("phonesum:'" + s.getHandterminalsum() + "',");
                        json.append("notebooksum:'" + s.getNotebookterminalsum() + "',");
                        json.append("othersum:'" + s.getOthertypeterminalsum() + "',");
                        json.append("mobile:'" + s.getYidonglinksum() + "',");
                        json.append("unicom:'" + s.getLiantonglinksum() + "',");
                        json.append("telecom:'" + s.getDianxinlinksum() + "',");
                        json.append("idsystem:'" + s.getIdsystem() + "',");
                        json.append(" uiProvider:'col'," +
                                "                cls:'master-task'," +
                                "                 leaf:true," +
                                "                iconCls:'task-folder'");
                        json.append("},");
                    }
                }
            } else if (zonename.endsWith("00")) { //市
                boolean city = false;
                if (provinceToCity) {
                    json.delete(json.length() - 1, json.length());
                    json.append("]},");
                }
                provinceToCity = true;
                String hql = "from StatSysterminalinfo where idsystem like '" + zonename + "%' order by idsystem asc ";
                List terminalList = statSysterminalinfoDao.list(hql);
                if (terminalList.size() == 0) {
                    json.append("{zone:'" + zc.getDistrictName() + "',");
                    totalCity(json, zonename);
                    json.append("  uiProvider:'col'," +
                            "            cls:'master-task'," +
                            "            leaf:false," +
                            "            iconCls:'task-1'," +
                            "            children:[");

                } else {
                    //一个市的循环开始
                    for (int j = 0; j < terminalList.size(); j++) {
                        StatSysterminalinfo s = (StatSysterminalinfo) terminalList.get(j);
                        String idsystem = s.getIdsystem();
                        String teriminalName = sysRegInfDao.findSystemNameLmy(idsystem);
                        if (teriminalName == null) {
                            teriminalName = zc.getDistrictName();
                        }
                        if (!city) {
                            json.append("{zone:'" + zc.getDistrictName() + "',");
                            totalCity(json, zonename);
                            json.append("  uiProvider:'col'," +
                                    "            cls:'master-task'," +
                                    "            leaf:false," +
                                    "            iconCls:'task-1'," +
                                    "            children:[");
                            json = StatSysterminalinfoJson(json, s, teriminalName);
                            json.append(" uiProvider:'col'," +
                                    "                cls:'master-task'," +
                                    "                 leaf:true," +
                                    "                iconCls:'task-folder'");
                            json.append("},");
                            city = true;
                        } else {
                            json = StatSysterminalinfoJson(json, s, teriminalName);
                            json.append(" uiProvider:'col'," +
                                    "                cls:'master-task'," +
                                    "                 leaf:true," +
                                    "                iconCls:'task-folder'");
                            json.append("},");
                        }

                    }
                }

            } else {// 县区的数据
                String hql = "from StatSysterminalinfo where idsystem like '" + zonename + "%' order by idsystem asc ";
                List terminalList = statSysterminalinfoDao.list(hql);
                if (terminalList.size() == 0) {
                    json.append("{zone:'" + zc.getDistrictName() + "',");
                    json.append("idsystem:'" + zonename + "',");
                    json.append("  uiProvider:'col'," +
                            "            cls:'master-task'," +
                            "            leaf:true," +
                            "            iconCls:'task-folder'," +
                            "            children:[]},");
                } else {
                    for (int j = 0; j < terminalList.size(); j++) {
                        StatSysterminalinfo s = (StatSysterminalinfo) terminalList.get(j);
                        String idsystem = s.getIdsystem();
                        String teriminalName = sysRegInfDao.findSystemNameLmy(idsystem);
                        if (teriminalName == null) {
                            teriminalName = zc.getDistrictName();
                        }
                        json = StatSysterminalinfoJson(json, s, teriminalName);
                        json.append(" uiProvider:'col'," +
                                "                cls:'master-task'," +
                                "                 leaf:true," +
                                "                iconCls:'task-folder'");
                        json.append("}");
                        if (j == terminalList.size()) {

                        } else {
                            json.append(",");
                        }
                    }
                }

            }

        }
        json.delete(json.length() - 1, json.length());
        json.append("]}]}]");
        return json.toString();
    }

    @Override
    public String detail(String idsystem, String start, String pageSize) throws Exception {

        int page = Integer.parseInt(start) / Integer.parseInt(pageSize) + 1;
        PageResult pageResult = systerminalInfoDao.listByPage(Integer.parseInt(pageSize), page, idsystem);

        StringBuffer json = new StringBuffer();
        json.append("{\"amount\":" + pageResult.getAllResultsAmount() + ",");
        json.append("\"result\":[");
        List list = pageResult.getResults();
        for (int i = 0; i < list.size(); i++) {
            Systerminalinfo systerminalInfo = (Systerminalinfo) list.get(i);
            json.append("{terminalName:'" + systerminalInfo.getIdsystem() + "',");
            json.append("terminaltype:'" + systerminalInfo.getTerminaltype() + "',");
            json.append("terminalOutlink:'" + systerminalInfo.getTermianlOutlink() + "',");
            json.append("terminalos:'" + systerminalInfo.getTermianlos() + "',");
            json.append("terminalband:'" + systerminalInfo.getTermianlband() + "',");
            json.append("cardtype:'" + systerminalInfo.getCardtype() + "',");
            json.append("username:'" + systerminalInfo.getUsername() + "',");
            json.append("userid:'" + systerminalInfo.getUserid() + "',");
            json.append("policenumber:'" + systerminalInfo.getPolicenumber() + "',");
            Orgcode orgcode;
            if (systerminalInfo.getUserdepart() != null) {
                orgcode = orgcodeDao.findOrgcode(systerminalInfo.getUserdepart());
                json.append("userdepart:'" + orgcode.getOrgname() + "',");
            } else {
                json.append("userdepart:'" + systerminalInfo.getUserdepart() + "',");
            }
            json.append("ifcancel:'" + systerminalInfo.getIfcancel() + "'");
            if (i == (list.size() - 1)) {
                json.append("}");
            } else {
                json.append("},");
            }
        }
        json.append("]}");

        return json.toString();
    }

    @Override
    public String citySelect(String zonecode) throws Exception {
        StringBuffer json = new StringBuffer();
        json.append("[");
        boolean provinceToCity = false;
        String cityecode = zonecode.substring(0, 4);
        String zonesql = " from District where district_id   like '" + cityecode + "%' ";
        List zonelist = districtDao.list(zonesql);
        for (int i = 0; i < zonelist.size(); i++) {
            District zc = (District) zonelist.get(i);
            String zonename = String.valueOf(zc.getId());
            if (zonename.endsWith("00")) { //市
                boolean city = false;
                if (provinceToCity) {
                    json.delete(json.length() - 1, json.length());
                    json.append("]},");
                }
                provinceToCity = true;
                String hql = "from StatSysterminalinfo where idsystem like '" + zonename + "%' order by idsystem asc ";
                List terminalList = statSysterminalinfoDao.list(hql);
                if (terminalList.size() == 0) {
                    json.append("{zone:'" + zc.getDistrictName() + "',");
                    totalCity(json, zonename);
                    json.append("  uiProvider:'col'," +
                            "            cls:'master-task'," +
                            "            leaf:false," +
                            "            expanded:true," +
                            "            iconCls:'task-1'," +
                            "            children:[");

                } else {
                    //一个市的循环开始
                    for (int j = 0; j < terminalList.size(); j++) {
                        StatSysterminalinfo s = (StatSysterminalinfo) terminalList.get(j);
                        String idsystem = s.getIdsystem();
                        String teriminalName = sysRegInfDao.findSystemNameLmy(idsystem);
                        if (teriminalName == null) {
                            teriminalName = zc.getDistrictName();
                        }
                        if (!city) {
                            json.append("{zone:'" + zc.getDistrictName() + "',");
                            totalCity(json, zonename);
                            json.append("  uiProvider:'col'," +
                                    "            cls:'master-task'," +
                                    "            leaf:false," +
                                    "            iconCls:'task-1'," +
                                    "            expanded:true," +
                                    "            children:[");
                            json = StatSysterminalinfoJson(json, s, teriminalName);
                            json.append(" uiProvider:'col'," +
                                    "                cls:'master-task'," +
                                    "                 leaf:true," +
                                    "                iconCls:'task-folder'");
                            json.append("},");
                            city = true;
                        } else {
                            json = StatSysterminalinfoJson(json, s, teriminalName);
                            json.append(" uiProvider:'col'," +
                                    "                cls:'master-task'," +
                                    "                 leaf:true," +
                                    "                iconCls:'task-folder'");
                            json.append("},");
                        }

                    }
                }
            } else {// 县区的数据
                String hql = "from StatSysterminalinfo where idsystem like '" + zonename + "%' order by idsystem asc ";
                List terminalList = statSysterminalinfoDao.list(hql);
                if (terminalList.size() == 0) {
                    json.append("{zone:'" + zc.getDistrictName() + "',");
                    json.append("idsystem:'" + zonename + "',");
                    json.append("  uiProvider:'col'," +
                            "            cls:'master-task'," +
                            "            leaf:true," +
                            "            iconCls:'task-folder'," +
                            "            children:[]},");
                } else {
                    for (int j = 0; j < terminalList.size(); j++) {
                        StatSysterminalinfo s = (StatSysterminalinfo) terminalList.get(j);
                        String idsystem = s.getIdsystem();
                        String teriminalName = sysRegInfDao.findSystemNameLmy(idsystem);
                        if (teriminalName == null) {
                            teriminalName = zc.getDistrictName();
                        }
                        json = StatSysterminalinfoJson(json, s, teriminalName);
                        json.append(" uiProvider:'col'," +
                                "                cls:'master-task'," +
                                "                 leaf:true," +
                                "                iconCls:'task-folder'");
                        json.append("}");
                        if (j == terminalList.size()) {

                        } else {
                            json.append(",");
                        }
                    }
                }

            }
        }
        json.delete(json.length() - 1, json.length());
        json.append("]}]");
        return json.toString();
    }

    public String ZhixiacitySelect(String zonecode) throws Exception {
        StringBuffer json = new StringBuffer();
        json.append("[");
        String teriminalzonecode = "";
        String zonecodeFlag = "";
        boolean provinceToCity = false;
        String cityecode = zonecode.substring(0, 2);
        String zonesql = " from District where district_id  like '" + cityecode + "%' ";
        List zonelist = districtDao.list(zonesql);
        for (int i = 0; i < zonelist.size(); i++) {
            District zc = (District) zonelist.get(i);
            String zonename = String.valueOf(zc.getId());
            if (zonename.endsWith("00")) { //市
                boolean city = false;
                if (provinceToCity) {
                    json.delete(json.length() - 1, json.length());
                    json.append("]},");
                }
                provinceToCity = true;
                String hql = "from StatSysterminalinfo where idsystem like '" + zonename + "%' order by idsystem asc ";
                List terminalList = statSysterminalinfoDao.list(hql);
                if (terminalList.size() == 0) {
                    json.append("{zone:'" + zc.getDistrictName() + "',");
                    totalCity(json, zonename);
                    json.append("  uiProvider:'col'," +
                            "            cls:'master-task'," +
                            "            leaf:false," +
                            "            expanded:true," +
                            "            iconCls:'task-1'," +
                            "            children:[");

                } else {
                    //一个市的循环开始
                    for (int j = 0; j < terminalList.size(); j++) {
                        StatSysterminalinfo s = (StatSysterminalinfo) terminalList.get(j);
                        teriminalzonecode = s.getIdsystem().substring(0, 6);
                        String idsystem = s.getIdsystem();
                        String teriminalName = sysRegInfDao.findSystemNameLmy(idsystem);
                        if (teriminalName == null) {
                            teriminalName = zc.getDistrictName();
                        }
                        //判断之前有没有相同层次的数据
                        if (teriminalzonecode.substring(0, 4).equalsIgnoreCase(zonecodeFlag)) {
                            json.append(",");
                        }
                        zonecodeFlag = teriminalzonecode;
                        if (!city) {
                            json.append("{zone:'" + zc.getDistrictName() + "',");
                            totalCity(json, zonename);
                            json.append("  uiProvider:'col'," +
                                    "            cls:'master-task'," +
                                    "            leaf:false," +
                                    "            iconCls:'task-1'," +
                                    "            expanded:true," +
                                    "            children:[");
                            json = StatSysterminalinfoJson(json, s, teriminalName);
                            json.append(" uiProvider:'col'," +
                                    "                cls:'master-task'," +
                                    "                 leaf:true," +
                                    "                iconCls:'task-folder'");
                            json.append("},");
                            city = true;
                        } else {
                            json = StatSysterminalinfoJson(json, s, teriminalName);
                            json.append(" uiProvider:'col'," +
                                    "                cls:'master-task'," +
                                    "                 leaf:true," +
                                    "                iconCls:'task-folder'");
                            json.append("},");
                        }

                    }
                }
            } else {// 县区的数据
                String hql = "from StatSysterminalinfo where idsystem like '" + zonename + "%' order by idsystem asc ";
                List terminalList = statSysterminalinfoDao.list(hql);
                if (terminalList.size() == 0) {
                    json.append("{zone:'" + zc.getDistrictName() + "',");
                    json.append("idsystem:'" + zonename + "',");
                    json.append("  uiProvider:'col'," +
                            "            cls:'master-task'," +
                            "            leaf:true," +
                            "            iconCls:'task-folder'," +
                            "            children:[]},");
                } else {
                    for (int j = 0; j < terminalList.size(); j++) {
                        StatSysterminalinfo s = (StatSysterminalinfo) terminalList.get(j);
                        teriminalzonecode = s.getIdsystem().substring(0, 6);
                        String idsystem = s.getIdsystem();
                        String teriminalName = sysRegInfDao.findSystemNameLmy(idsystem);
                        if (teriminalName == null) {
                            teriminalName = zc.getDistrictName();
                        }
                        //判断之前有没有相同层次的数据
                        if (teriminalzonecode.substring(0, 4).equalsIgnoreCase(zonecodeFlag)) {
                            json.append(",");
                        }
                        zonecodeFlag = teriminalzonecode;
                        json = StatSysterminalinfoJson(json, s, teriminalName);
                        json.append(" uiProvider:'col'," +
                                "                cls:'master-task'," +
                                "                 leaf:true," +
                                "                iconCls:'task-folder'");
                        json.append("}");
                        if (j == terminalList.size()) {

                        } else {
                            json.append(",");
                        }
                    }
                }

            }
        }
        json.delete(json.length() - 1, json.length());
        json.append("]}]");


        return json.toString();
    }


    public StringBuffer StatSysterminalinfoJson(StringBuffer json, StatSysterminalinfo s, String teriminalName) throws Exception {
        json.append("{zone:'" + teriminalName + "',");
        json.append("terminalsum:'" + s.getTerminalsum() + "',");
        json.append("vehiclesum:'" + s.getCarterminalsum() + "',");
        json.append("phonesum:'" + s.getHandterminalsum() + "',");
        json.append("notebooksum:'" + s.getNotebookterminalsum() + "',");
        json.append("othersum:'" + s.getOthertypeterminalsum() + "',");
        json.append("mobile:'" + s.getYidonglinksum() + "',");
        json.append("unicom:'" + s.getLiantonglinksum() + "',");
        json.append("telecom:'" + s.getDianxinlinksum() + "',");
        json.append("idsystem:'" + s.getIdsystem() + "',");
        return json;
    }

    public void totalProvince(StringBuffer json, String zonecode) throws Exception {
        int terminalsum = 0;
        int vehiclesum = 0;
        int phonesum = 0;
        int notebooksum = 0;
        int othersum = 0;
        int mobile = 0;
        int unicom = 0;
        int telecom = 0;
        String hql = "from StatSysterminalinfo where idsystem like '" + zonecode.substring(0, 2) + "%'";
        List terminalList = statSysterminalinfoDao.list(hql);
        for (int i = 0; i < terminalList.size(); i++) {
            StatSysterminalinfo statSysterminalinfo = (StatSysterminalinfo) terminalList.get(i);
            terminalsum += statSysterminalinfo.getTerminalsum();
            vehiclesum += statSysterminalinfo.getCarterminalsum();
            phonesum += statSysterminalinfo.getHandterminalsum();
            notebooksum += statSysterminalinfo.getNotebookterminalsum();
            othersum += statSysterminalinfo.getOtherlinksum();
            mobile += statSysterminalinfo.getYidonglinksum();
            unicom += statSysterminalinfo.getLiantonglinksum();
            telecom += statSysterminalinfo.getDianxinlinksum();
        }

        json.append("terminalsum:'" + terminalsum + "',");
        json.append("vehiclesum:'" + vehiclesum + "',");
        json.append("phonesum:'" + phonesum + "',");
        json.append("notebooksum:'" + notebooksum + "',");
        json.append("othersum:'" + othersum + "',");
        json.append("mobile:'" + mobile + "',");
        json.append("unicom:'" + unicom + "',");
        json.append("telecom:'" + telecom + "',");
    }

    public void totalCity(StringBuffer json, String zonecode) throws Exception {
        int terminalsum = 0;
        int vehiclesum = 0;
        int phonesum = 0;
        int notebooksum = 0;
        int othersum = 0;
        int mobile = 0;
        int unicom = 0;
        int telecom = 0;
        String hql = "from StatSysterminalinfo where idsystem like '" + zonecode.substring(0, 4) + "%'";
        List terminalList = statSysterminalinfoDao.list(hql);
        for (int i = 0; i < terminalList.size(); i++) {
            StatSysterminalinfo statSysterminalinfo = (StatSysterminalinfo) terminalList.get(i);
            terminalsum += statSysterminalinfo.getTerminalsum();
            vehiclesum += statSysterminalinfo.getCarterminalsum();
            phonesum += statSysterminalinfo.getHandterminalsum();
            notebooksum += statSysterminalinfo.getNotebookterminalsum();
            othersum += statSysterminalinfo.getOtherlinksum();
            mobile += statSysterminalinfo.getYidonglinksum();
            unicom += statSysterminalinfo.getLiantonglinksum();
            telecom += statSysterminalinfo.getDianxinlinksum();
        }

        json.append("terminalsum:'" + terminalsum + "',");
        json.append("vehiclesum:'" + vehiclesum + "',");
        json.append("phonesum:'" + phonesum + "',");
        json.append("notebooksum:'" + notebooksum + "',");
        json.append("othersum:'" + othersum + "',");
        json.append("mobile:'" + mobile + "',");
        json.append("unicom:'" + unicom + "',");
        json.append("telecom:'" + telecom + "',");
    }

//    public String selectAll(String zonecode) {
//        StringBuffer json = new StringBuffer();
//        json.append("[");
//        String teriminalzonecode="";
//        String zonecodeFlag="";
//        boolean firstCity = false;
//        boolean province = false;
//        boolean city = false;
//        boolean provinceToCity = false;
//
//
//        String  provincecode = zonecode.substring(0,2);
//        String zonesql = " from District where district_id = '"+zonecode+"'";
//        List zonelist = districtDao.list(zonesql);
//        Zonecode z = (Zonecode) zonelist.get(0);
//        json.append("{zone:'"+z.getZonename()+"',uiProvider:'col',");
//        String hql = "from StatSysterminalinfo where idsystem like '"+zonecode.substring(0,2)+"%' order by idsystem asc ";
//        List terminalList = statSysterminalinfoDao.list(hql);
//        for(int i = 0;i<terminalList.size();i++){
//            StatSysterminalinfo s = (StatSysterminalinfo) terminalList.get(i);
//            teriminalzonecode = s.getIdsystem().substring(0,6);
//
//            //判断是否是省厅的数据
//            if(teriminalzonecode.equalsIgnoreCase(zonecode)){
//                //判断之前有没有相同层次的数据
//                if(teriminalzonecode.equalsIgnoreCase(zonecodeFlag)){
//                    json.append(",");
//                }
//                zonecodeFlag = teriminalzonecode;
//                //判断是否有省头
//                if(!province){
//                    //省头
//                    json.append("children:[{zone:'"+z.getZonename()+"省厅',");
//
//                    json.append("uiProvider:'col',");
//
//                    //省厅数据
//                    json.append("children:[{zone:'"+z.getZonename()+s.getIdsystem().substring(6,8)+"',");
//                    json.append("terminalsum:'"+s.getTerminalsum()+"',");
//                    json.append("vehiclesum:'"+s.getCarterminalsum()+"',");
//                    json.append("phonesum:'"+s.getHandterminalsum()+"',");
//                    json.append("notebooksum:'"+s.getNotebookterminalsum()+"',");
//                    json.append("othersum:'"+s.getOthertypeterminalsum()+"',");
//                    json.append("mobile:'"+s.getYidonglinksum()+"',");
//                    json.append("unicom:'"+s.getLiantonglinksum()+"',");
//                    json.append("telecom:'"+s.getDianxinlinksum()+"',");
//                    json.append(" uiProvider:'col'," +
//                            "                cls:'master-task'," +
//                            "                 leaf:true," +
//                            "                iconCls:'task-folder'");
//                    json.append("}");
//                    province = true;
//                }else{
//                    json = StatSysterminalinfoJson(json,s,z);
//                    json.append(" uiProvider:'col'," +
//                            "                cls:'master-task'," +
//                            "                 leaf:true," +
//                            "                iconCls:'task-folder'");
//                    json.append("}");
//                }
//
//            }else{
//                if(!provinceToCity){
//                    //判断之前有没有相同层次的数据
//                    if(zonecodeFlag.equalsIgnoreCase(teriminalzonecode.substring(0,6))){
//                        if(province){
//                            if(firstCity){
//                                json.append(",");
//                            }
//                            firstCity = true;
//
//                        }
//                    }else {
//
//                        json.append("]" +
//                                "        },");
//
//                        city = false;
//                        firstCity = false;
//                    }
//                    provinceToCity = true;
//                    zonecodeFlag = teriminalzonecode.substring(0,4);
//                } else{
//                    //判断之前有没有相同层次的数据
//                    if(zonecodeFlag.equalsIgnoreCase(teriminalzonecode.substring(0,4))){
//                        if(province){
//                            if(firstCity){
//                                json.append(",");
//                            }
//                            firstCity = true;
//
//                        }
//                    }else {
//                        json.append("]" +
//                                "        },");
//                        city = false;
//                        firstCity = false;
//                    }
//                    zonecodeFlag = teriminalzonecode.substring(0,4);
//                }
//
//
//                String cityzonesql = " from District where district_id = '"+teriminalzonecode+"'";
//                List  cityzonelist = districtDao.list(cityzonesql);
//                Zonecode cityzone = (Zonecode) cityzonelist.get(0);
//                if(!province){
//                    json.append("children:[{zone:'"+z.getZonename()+"省厅',");
//                    json.append("uiProvider:'col'," +
//                            "            cls:'master-task'," +
//                            "            iconCls:'task-1'," +
//                            "             leaf:false,");
//                    //省厅数据
//                    json.append("children:[{},");
//                    province = true;
//                }
//                if(!city ){
//                    if(s.getIdsystem().substring(0,6).endsWith("00")){
//                        json.append("{zone:'"+cityzone.getZonename()+"',");
//                        json.append("terminalsum:'"+s.getTerminalsum()+"',");
//                        json.append("vehiclesum:'"+s.getCarterminalsum()+"',");
//                        json.append("phonesum:'"+s.getHandterminalsum()+"',");
//                        json.append("notebooksum:'"+s.getNotebookterminalsum()+"',");
//                        json.append("othersum:'"+s.getOthertypeterminalsum()+"',");
//                        json.append("mobile:'"+s.getYidonglinksum()+"',");
//                        json.append("unicom:'"+s.getLiantonglinksum()+"',");
//                        json.append("telecom:'"+s.getDianxinlinksum()+"',");
//                        json.append("  uiProvider:'col'," +
//                                "            cls:'master-task'," +
//                                "            leaf:false," +
//                                "            iconCls:'task-1'," +
//                                "            children:[");
//                    } else{
//                        json.append("{zone:'"+cityzone.getZonename()+"',");
//                        json.append("  uiProvider:'col'," +
//                                "            cls:'master-task'," +
//                                "            leaf:false," +
//                                "            iconCls:'task-1'," +
//                                "            children:[");
//                    }
//
//                    city = true;
//                }else{
//                    json = StatSysterminalinfoJson(json,s,cityzone);
//                    json.append(" uiProvider:'col'," +
//                            "                cls:'master-task'," +
//                            "                 leaf:true," +
//                            "                iconCls:'task-folder'");
//                    json.append("}");
//                }
//
//            }
//        }
////        }else{               //市        然后取前两位 还要把省名查出来
////            zone = zonecode.substring(0,2);
////            String zonesql = " from Zonecode where zonecode = '"+zone+"00'";
////            List zonelist = districtDao.list(zonesql);
////            Zonecode z = (Zonecode) zonelist.get(0);
////            json.append("{zone:'"+z.getZonename()+"',uiProvider:'col',");
////            String hql = "from StatSysterminalinfo where idsystem like '"+zone.substring(0,4)+"%' order by idsystem asc ";
////            List terminalList = statSysterminalinfoDao.list(hql);
////            for(int i = 0;i<terminalList.size();i++){
////                StatSysterminalinfo s = (StatSysterminalinfo) terminalList.get(i);
////                teriminalzonecode = s.getIdsystem().substring(0,6);
////            }
////
////        }
//        json.append("   ]" +    "    }" +       "        ]" +            //市内容皆为
//                "    }" +      //省内容结尾
//                "]") ;
//
//
//
//        return json.toString();
//    }

}
