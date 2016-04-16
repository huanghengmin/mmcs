package com.hzih.mmcs.service.impl;

import com.hzih.mmcs.dao.DistrictDao;
import com.hzih.mmcs.dao.SysRegInfDao;
import com.hzih.mmcs.dao.SysabnormalDao;
import com.hzih.mmcs.dao.ZoneCodeDao;
import com.hzih.mmcs.domain.ZoneCode;
import com.hzih.mmcs.service.SysabnormalService;
import com.hzih.mmcs.vo.AbnormalVo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-11
 * Time: 上午11:34
 * To change this template use File | Settings | File Templates.
 */
public class SysabnormalServiceImpl implements SysabnormalService {
    private SysabnormalDao sysabnormalDao;
    private ZoneCodeDao zonecodeDao;
    private SysRegInfDao sysRegInfDao;
    private DistrictDao districtDao;

    public DistrictDao getDistrictDao() {
        return districtDao;
    }

    public void setDistrictDao(DistrictDao districtDao) {
        this.districtDao = districtDao;
    }

    public SysRegInfDao getSysRegInfDao() {
        return sysRegInfDao;
    }

    public void setSysRegInfDao(SysRegInfDao sysRegInfDao) {
        this.sysRegInfDao = sysRegInfDao;
    }

    public ZoneCodeDao getZonecodeDao() {
        return zonecodeDao;
    }

    public void setZonecodeDao(ZoneCodeDao zonecodeDao) {
        this.zonecodeDao = zonecodeDao;
    }

    public SysabnormalDao getSysabnormalDao() {
        return sysabnormalDao;
    }

    public void setSysabnormalDao(SysabnormalDao sysabnormalDao) {
        this.sysabnormalDao = sysabnormalDao;
    }

    @Override
    public String findAbnormaVos(String idsystem, int start, int limit) throws Exception {
        ArrayList<AbnormalVo> alllist = sysabnormalDao.findAbnormaVos(idsystem);
        ArrayList<AbnormalVo> list = sysabnormalDao.findAbnormaVos(idsystem,start,limit);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'ablist':").append(alllist.size()).append(",'abrow':[");
        for(AbnormalVo s : list){
            jsonb.append("{id:").append(s.getId()).append(",idalertmatter:'").append(s.getIdalertmatter());
            jsonb.append("',abnormaltype:'").append(s.getAbnormaltype()).append("',abnormalobject:'").append(s.getAbnormalobject());
            jsonb.append("',exceptiondesc:'").append(s.getExceptiondesc()).append("',happentime:'").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getHappentime()));
            Date checkedTime = s.getTreattime();
            String chetimes = "无";
            if(null!=checkedTime){
                chetimes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(checkedTime);
            }
            jsonb.append("',treattime:'").append(chetimes).append("',treadresult:'").append(s.getTreadresult());
            jsonb.append("',ifcheckin:'").append(s.getIfcheckin()).append("'},");
        }
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findAbnormaVosByTimeAndTreadresult(String idsystem, int start, int limit,String timetype, String treadresult) throws Exception {
        ArrayList<AbnormalVo> alllist=null;
        ArrayList<AbnormalVo> list=null;
        if("1".equals(timetype)){
            alllist = sysabnormalDao.findAbnormaVosByMonth(idsystem, treadresult);
            list = sysabnormalDao.findAbnormaVosByMonth(idsystem,  start, limit, treadresult);
        }else {
            alllist = sysabnormalDao.findAbnormaVosByYear(idsystem, treadresult);
            list = sysabnormalDao.findAbnormaVosByYear(idsystem,  start, limit, treadresult);
        }
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'ablist':").append(alllist.size()).append(",'abrow':[");
        for(AbnormalVo s : list){
            jsonb.append("{id:").append(s.getId()).append(",idalertmatter:'").append(s.getIdalertmatter());
            jsonb.append("',abnormaltype:'").append(s.getAbnormaltype()).append("',abnormalobject:'").append(s.getAbnormalobject());
            jsonb.append("',exceptiondesc:'").append(s.getExceptiondesc()).append("',happentime:'").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getHappentime()));
            Date checkedTime = s.getTreattime();
            String chetimes = "无";
            if(null!=checkedTime){
                chetimes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(checkedTime);
            }
            jsonb.append("',treattime:'").append(chetimes).append("',treadresult:'").append(s.getTreadresult());
            jsonb.append("',ifcheckin:'").append(s.getIfcheckin()).append("'},");
        }
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findAbnormaNum(String idsystem) throws Exception {
        String oo=idsystem.substring(0,2);

        HashMap<String,Integer> map = sysabnormalDao.findAbnormalAllNumByIdsystem(oo);
        int mNum = map.get("mNum");
        int mtreadNum = map.get("mtreadNum");
        int mNtreadNum = map.get("mNtreadNum");
        int yNum = map.get("yNum");
        int ytreadNum=map.get("ytreadNum");
        int yNtreadNum=map.get("yNtreadNum");

//        int mNum = sysabnormalDao.findAbnormalNumByMonth(oo,"%");
//        int mtreadNum = sysabnormalDao.findAbnormalNumByMonth(oo,"001");
//        int mNtreadNum = sysabnormalDao.findAbnormalNumByMonth(oo,"003");
//        int yNum = sysabnormalDao.findAbnormalNumByYear(oo,"%");
//        int ytreadNum=sysabnormalDao.findAbnormalNumByYear(oo,"001");
//        int yNtreadNum=sysabnormalDao.findAbnormalNumByYear(oo,"003");

        ArrayList<ZoneCode> ooList = zonecodeDao.findZoneByZonecodeLike(oo+"__00");
        String ooName = ooList.get(0).getZonename();
        String ooCode = ooList.get(0).getZonecode();

        StringBuffer jsonb = new StringBuffer();
        jsonb.append("[" +
                "    {   zonename:'"+ooName+"',\n" +
                "        zonecode:'"+ooCode+"',\n" +
                "        mNum:'"+mNum+"',\n" +
                "        mtreadNum:'"+mtreadNum+"',\n" +
                "        mNtreadNum:'"+mNtreadNum+"',\n" +
                "        yNum:'"+yNum+"',\n" +
                "        ytreadNum:'"+ytreadNum+"',\n" +
                "        yNtreadNum:'"+yNtreadNum+"',\n" +
                "        uiProvider:'col',\n" +
                "        expanded: true,\n" +
                "        children:[" );

        ArrayList<String> list1 = sysabnormalDao.findIdsystemRS(ooCode);
        for(String soo:list1){
            HashMap<String,Integer> map1 = sysabnormalDao.findAbnormalAllNumByIdsystem(soo);
            mNum = map1.get("mNum");
            mtreadNum = map1.get("mtreadNum");
            mNtreadNum = map1.get("mNtreadNum");
            yNum = map1.get("yNum");
            ytreadNum = map1.get("ytreadNum");
            yNtreadNum = map1.get("yNtreadNum");
            jsonb.append("    {   zonename:'"+sysRegInfDao.findSystemName(soo)+"',\n" +
                    "        zonecode:'"+soo+"',\n" +
                    "        mNum:'"+mNum+"',\n" +
                    "        mtreadNum:'"+mtreadNum+"',\n" +
                    "        mNtreadNum:'"+mNtreadNum+"',\n" +
                    "        yNum:'"+yNum+"',\n" +
                    "        ytreadNum:'"+ytreadNum+"',\n" +
                    "        yNtreadNum:'"+yNtreadNum+"',\n" +
                    "        uiProvider:'col',\n " +
                    "        cls:'master-task',\n" +
                    "        iconCls:'task-folder',\n" +
                    "        leaf:true },");

        }

        ArrayList<ZoneCode> ooooList = new ArrayList<ZoneCode>();
        String ooooName="";
        String ooooCode="";

        for(int i=1;i<ooList.size();i++) {
            ooName = ooList.get(i).getZonename();
            ooCode = ooList.get(i).getZonecode();

            oo = ooCode.substring(0, 4);

            HashMap<String,Integer> map2 = sysabnormalDao.findAbnormalAllNumByIdsystem(oo);
            mNum = map2.get("mNum");
            mtreadNum = map2.get("mtreadNum");
            mNtreadNum = map2.get("mNtreadNum");
            yNum = map2.get("yNum");
            ytreadNum = map2.get("ytreadNum");
            yNtreadNum = map2.get("yNtreadNum");
            jsonb.append("    {   zonename:'"+ooName+"',\n" +
                    "        zonecode:'"+ooCode+"',\n" +
                    "        mNum:'"+mNum+"',\n" +
                    "        mtreadNum:'"+mtreadNum+"',\n" +
                    "        mNtreadNum:'"+mNtreadNum+"',\n" +
                    "        yNum:'"+yNum+"',\n" +
                    "        ytreadNum:'"+ytreadNum+"',\n" +
                    "        yNtreadNum:'"+yNtreadNum+"',\n" +
                    "        uiProvider:'col',\n " +
                    "        cls:'master-task',\n" +
                    "        iconCls:'task-folder',\n" +
                    "        leaf:false ,\n"+
                    "        children:[" );

            ArrayList<String> list2 = sysabnormalDao.findIdsystemRS(ooCode);
            for(String oocity:list2){

                HashMap<String,Integer> mapcity = sysabnormalDao.findAbnormalAllNumByIdsystem(oocity);
                mNum = mapcity.get("mNum");
                mtreadNum = mapcity.get("mtreadNum");
                mNtreadNum = mapcity.get("mNtreadNum");
                yNum = mapcity.get("yNum");
                ytreadNum = mapcity.get("ytreadNum");
                yNtreadNum = mapcity.get("yNtreadNum");
                jsonb.append("    {   zonename:'"+sysRegInfDao.findSystemName(oocity)+"',\n" +
                        "        zonecode:'"+oocity+"',\n" +
                        "        mNum:'"+mNum+"',\n" +
                        "        mtreadNum:'"+mtreadNum+"',\n" +
                        "        mNtreadNum:'"+mNtreadNum+"',\n" +
                        "        yNum:'"+yNum+"',\n" +
                        "        ytreadNum:'"+ytreadNum+"',\n" +
                        "        yNtreadNum:'"+yNtreadNum+"',\n" +
                        "        uiProvider:'col',\n " +
                        "        cls:'master-task',\n" +
                        "        iconCls:'task-folder',\n" +
                        "        leaf:true },");

            }

            ooooList = zonecodeDao.findZoneByZonecodeLike(oo);
            for(int j=1;j<ooooList.size();j++) {
                ooooName = ooooList.get(j).getZonename();
                ooooCode = ooooList.get(j).getZonecode();

                HashMap<String,Integer> map3 = sysabnormalDao.findAbnormalAllNumByIdsystem(ooooCode);
                mNum = map3.get("mNum");
                mtreadNum = map3.get("mtreadNum");
                mNtreadNum = map3.get("mNtreadNum");
                yNum = map3.get("yNum");
                ytreadNum = map3.get("ytreadNum");
                yNtreadNum = map3.get("yNtreadNum");
                jsonb.append("    {   zonename:'"+ooooName+"',\n" +
                        "        zonecode:'"+ooooCode+"',\n" +
                        "        mNum:'"+mNum+"',\n" +
                        "        mtreadNum:'"+mtreadNum+"',\n" +
                        "        mNtreadNum:'"+mNtreadNum+"',\n" +
                        "        yNum:'"+yNum+"',\n" +
                        "        ytreadNum:'"+ytreadNum+"',\n" +
                        "        yNtreadNum:'"+yNtreadNum+"',\n" +
                        "        uiProvider:'col',\n " +
                        "        cls:'master-task',\n" +
                        "        iconCls:'task-folder',\n" +
                        "        leaf:false ," +
                        "        children:[" );

                ArrayList<String> list3 = sysabnormalDao.findIdsystemRS(ooooCode);
                boolean flag=false;
                for(String oocounty:list3){
                    HashMap<String,Integer> mapcounty = sysabnormalDao.findAbnormalAllNumByIdsystem(oocounty);
                    mNum = mapcounty.get("mNum");
                    mtreadNum = mapcounty.get("mtreadNum");
                    mNtreadNum = mapcounty.get("mNtreadNum");
                    yNum = mapcounty.get("yNum");
                    ytreadNum = mapcounty.get("ytreadNum");
                    yNtreadNum = mapcounty.get("yNtreadNum");
                    jsonb.append("    {   zonename:'"+sysRegInfDao.findSystemName(oocounty)+"',\n" +
                            "        zonecode:'"+oocounty+"',\n" +
                            "        mNum:'"+mNum+"',\n" +
                            "        mtreadNum:'"+mtreadNum+"',\n" +
                            "        mNtreadNum:'"+mNtreadNum+"',\n" +
                            "        yNum:'"+yNum+"',\n" +
                            "        ytreadNum:'"+ytreadNum+"',\n" +
                            "        yNtreadNum:'"+yNtreadNum+"',\n" +
                            "        uiProvider:'col',\n " +
                            "        cls:'master-task',\n" +
                            "        iconCls:'task-folder',\n" +
                            "        leaf:true },");
                    flag=true;
                }
                if(flag) {
                    jsonb.deleteCharAt(jsonb.length()-1);
                }

                jsonb.append("                   ]" +
                        "        }," );
            }
            if(ooooList.size()>=1){
                jsonb.deleteCharAt(jsonb.length()-1);
            }

            jsonb.append("]},");
        }
        jsonb.deleteCharAt(jsonb.length()-1);

         jsonb.append( "        ]\n" +
                "    }\n" +
                "]");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findAbnormaNumForCity(String idsystem) throws Exception {
        String oo=idsystem.substring(0,4);

        HashMap<String,Integer> map = sysabnormalDao.findAbnormalAllNumByIdsystem(oo);
        int mNum = map.get("mNum");
        int mtreadNum = map.get("mtreadNum");
        int mNtreadNum = map.get("mNtreadNum");
        int yNum = map.get("yNum");
        int ytreadNum=map.get("ytreadNum");
        int yNtreadNum=map.get("yNtreadNum");

        ArrayList<ZoneCode> ooList = zonecodeDao.findZoneByZonecodeLike(oo);
        String ooName = ooList.get(0).getZonename();
        String ooCode = ooList.get(0).getZonecode();

        StringBuffer jsonb = new StringBuffer();
        jsonb.append("[" +
                "    {   zonename:'"+ooName+"',\n" +
                "        zonecode:'"+ooCode+"',\n" +
                "        mNum:'"+mNum+"',\n" +
                "        mtreadNum:'"+mtreadNum+"',\n" +
                "        mNtreadNum:'"+mNtreadNum+"',\n" +
                "        yNum:'"+yNum+"',\n" +
                "        ytreadNum:'"+ytreadNum+"',\n" +
                "        yNtreadNum:'"+yNtreadNum+"',\n" +
                "        uiProvider:'col',\n" +
                "        expanded: true,\n" +
                "        children:[" );

        ArrayList<String> list1 = sysabnormalDao.findIdsystemRS(ooCode);
        for(String soo:list1){
            HashMap<String,Integer> map1 = sysabnormalDao.findAbnormalAllNumByIdsystem(soo);
            mNum = map1.get("mNum");
            mtreadNum = map1.get("mtreadNum");
            mNtreadNum = map1.get("mNtreadNum");
            yNum = map1.get("yNum");
            ytreadNum = map1.get("ytreadNum");
            yNtreadNum = map1.get("yNtreadNum");
            jsonb.append("    {   zonename:'"+sysRegInfDao.findSystemName(soo)+"',\n" +
                    "        zonecode:'"+soo+"',\n" +
                    "        mNum:'"+mNum+"',\n" +
                    "        mtreadNum:'"+mtreadNum+"',\n" +
                    "        mNtreadNum:'"+mNtreadNum+"',\n" +
                    "        yNum:'"+yNum+"',\n" +
                    "        ytreadNum:'"+ytreadNum+"',\n" +
                    "        yNtreadNum:'"+yNtreadNum+"',\n" +
                    "        uiProvider:'col',\n " +
                    "        cls:'master-task',\n" +
                    "        iconCls:'task-folder',\n" +
                    "        expanded: true,\n" +
                    "        leaf:true },");

        }

        ArrayList<ZoneCode> ooooList = zonecodeDao.findZoneByZonecodeLike(oo);
        for(int j=1;j<ooooList.size();j++) {
            String ooooName = ooooList.get(j).getZonename();
            String ooooCode = ooooList.get(j).getZonecode();

            HashMap<String,Integer> map3 = sysabnormalDao.findAbnormalAllNumByIdsystem(ooooCode);
            mNum = map3.get("mNum");
            mtreadNum = map3.get("mtreadNum");
            mNtreadNum = map3.get("mNtreadNum");
            yNum = map3.get("yNum");
            ytreadNum = map3.get("ytreadNum");
            yNtreadNum = map3.get("yNtreadNum");
            jsonb.append("    {   zonename:'"+ooooName+"',\n" +
                    "        zonecode:'"+ooooCode+"',\n" +
                    "        mNum:'"+mNum+"',\n" +
                    "        mtreadNum:'"+mtreadNum+"',\n" +
                    "        mNtreadNum:'"+mNtreadNum+"',\n" +
                    "        yNum:'"+yNum+"',\n" +
                    "        ytreadNum:'"+ytreadNum+"',\n" +
                    "        yNtreadNum:'"+yNtreadNum+"',\n" +
                    "        uiProvider:'col',\n " +
                    "        cls:'master-task',\n" +
                    "        iconCls:'task-folder',\n" +
                    "        leaf:false ," +
                    "        expanded: false,\n" +
                    "        children:[" );

            ArrayList<String> list3 = sysabnormalDao.findIdsystemRS(ooooCode);
            boolean flag=false;
            for(String oocounty:list3){
                HashMap<String,Integer> mapcounty = sysabnormalDao.findAbnormalAllNumByIdsystem(oocounty);
                mNum = mapcounty.get("mNum");
                mtreadNum = mapcounty.get("mtreadNum");
                mNtreadNum = mapcounty.get("mNtreadNum");
                yNum = mapcounty.get("yNum");
                ytreadNum = mapcounty.get("ytreadNum");
                yNtreadNum = mapcounty.get("yNtreadNum");
                jsonb.append("    {   zonename:'"+sysRegInfDao.findSystemName(oocounty)+"',\n" +
                        "        zonecode:'"+oocounty+"',\n" +
                        "        mNum:'"+mNum+"',\n" +
                        "        mtreadNum:'"+mtreadNum+"',\n" +
                        "        mNtreadNum:'"+mNtreadNum+"',\n" +
                        "        yNum:'"+yNum+"',\n" +
                        "        ytreadNum:'"+ytreadNum+"',\n" +
                        "        yNtreadNum:'"+yNtreadNum+"',\n" +
                        "        uiProvider:'col',\n " +
                        "        cls:'master-task',\n" +
                        "        iconCls:'task-folder',\n" +
                        "        leaf:true },");
                flag=true;
            }
            if(flag) {
                jsonb.deleteCharAt(jsonb.length()-1);
            }

            jsonb.append("                   ]" +
                    "        }," );
        }
        if(ooooList.size()>=1){
            jsonb.deleteCharAt(jsonb.length()-1);
        }

        jsonb.append("]}]");

        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findAbnormaNumForCounty(String idsystem) throws Exception {
        String oo=idsystem.substring(0,6);

        HashMap<String,Integer> map = sysabnormalDao.findAbnormalAllNumByIdsystem(oo);
        int mNum = map.get("mNum");
        int mtreadNum = map.get("mtreadNum");
        int mNtreadNum = map.get("mNtreadNum");
        int yNum = map.get("yNum");
        int ytreadNum=map.get("ytreadNum");
        int yNtreadNum=map.get("yNtreadNum");

        ArrayList<ZoneCode> ooList = zonecodeDao.findZoneByZonecodeLike(oo);
        String ooName = ooList.get(0).getZonename();
        String ooCode = ooList.get(0).getZonecode();

        StringBuffer jsonb = new StringBuffer();
        jsonb.append("[" +
                "    {   zonename:'"+ooName+"',\n" +
                "        zonecode:'"+ooCode+"',\n" +
                "        mNum:'"+mNum+"',\n" +
                "        mtreadNum:'"+mtreadNum+"',\n" +
                "        mNtreadNum:'"+mNtreadNum+"',\n" +
                "        yNum:'"+yNum+"',\n" +
                "        ytreadNum:'"+ytreadNum+"',\n" +
                "        yNtreadNum:'"+yNtreadNum+"',\n" +
                "        uiProvider:'col',\n" +
                "        expanded: true,\n" +
                "        children:[" );

        ArrayList<String> list3 = sysabnormalDao.findIdsystemRS(oo);
        boolean flag=false;
        for(String oocounty:list3){
            HashMap<String,Integer> mapcounty = sysabnormalDao.findAbnormalAllNumByIdsystem(oocounty);
            mNum = mapcounty.get("mNum");
            mtreadNum = mapcounty.get("mtreadNum");
            mNtreadNum = mapcounty.get("mNtreadNum");
            yNum = mapcounty.get("yNum");
            ytreadNum = mapcounty.get("ytreadNum");
            yNtreadNum = mapcounty.get("yNtreadNum");
            jsonb.append("    {   zonename:'"+sysRegInfDao.findSystemName(oocounty)+"',\n" +
                    "        zonecode:'"+oocounty+"',\n" +
                    "        mNum:'"+mNum+"',\n" +
                    "        mtreadNum:'"+mtreadNum+"',\n" +
                    "        mNtreadNum:'"+mNtreadNum+"',\n" +
                    "        yNum:'"+yNum+"',\n" +
                    "        ytreadNum:'"+ytreadNum+"',\n" +
                    "        yNtreadNum:'"+yNtreadNum+"',\n" +
                    "        uiProvider:'col',\n " +
                    "        cls:'master-task',\n" +
                    "        iconCls:'task-folder',\n" +
                    "        leaf:true },");
            flag=true;
        }
        if(flag) {
            jsonb.deleteCharAt(jsonb.length()-1);
        }

        jsonb.append("]}]");
        String jsons = jsonb.toString();
        return jsons;
    }
}


