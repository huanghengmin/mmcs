package com.hzih.mmcs.service.impl;

import com.hzih.mmcs.dao.SysAssessmentDao;
import com.hzih.mmcs.dao.SysRegInfDao;
import com.hzih.mmcs.dao.ZoneCodeDao;
import com.hzih.mmcs.domain.SysAssessment;
import com.hzih.mmcs.domain.ZoneCode;
import com.hzih.mmcs.service.SysAssessmentService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-16
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */
public class SysAssessmentServiceImpl implements SysAssessmentService{
    private ZoneCodeDao zoneCodeDao;
    private SysAssessmentDao sysAssessmentDao;
    private SysRegInfDao sysRegInfDao;

    public ZoneCodeDao getZoneCodeDao() {
        return zoneCodeDao;
    }

    public void setZoneCodeDao(ZoneCodeDao zoneCodeDao) {
        this.zoneCodeDao = zoneCodeDao;
    }

    public SysAssessmentDao getSysAssessmentDao() {
        return sysAssessmentDao;
    }

    public void setSysAssessmentDao(SysAssessmentDao sysAssessmentDao) {
        this.sysAssessmentDao = sysAssessmentDao;
    }

    public void setSysRegInfDao(SysRegInfDao sysRegInfDao) {
        this.sysRegInfDao = sysRegInfDao;
    }

    public Map<String, Double> getMapScore(List<SysAssessment> sysAssessments) {
        Map<String, Double> score = new HashMap<>();
        Double reg_score = 0.000;
        Double abn_score = 0.000;
        Double log_score = 0.000;
        Double ins_score = 0.000;
        Double act_score = 0.000;
        Double sum_score = 0.000;
        if (sysAssessments != null && sysAssessments.size() > 0) {
            for (SysAssessment sysAssessment : sysAssessments) {
                reg_score += sysAssessment.getRegscore();
                abn_score += sysAssessment.getAbnscore();
                log_score += sysAssessment.getLogscore();
                ins_score += sysAssessment.getResscore();
                act_score += sysAssessment.getActscore();
                sum_score += sysAssessment.getSumscore();
            }
            score.put("reg_score", reg_score / sysAssessments.size());
            score.put("abn_score", abn_score / sysAssessments.size());
            score.put("log_score", log_score / sysAssessments.size());
            score.put("ins_score", ins_score / sysAssessments.size());
            score.put("act_score", act_score / sysAssessments.size());
            score.put("sum_score", sum_score / sysAssessments.size());
        } else {
            score.put("reg_score", 0.000);
            score.put("abn_score", 0.000);
            score.put("log_score", 0.000);
            score.put("ins_score", 0.000);
            score.put("act_score", 0.000);
            score.put("sum_score", 0.000);
        }
        return score;
    }

    public String findProvinceCode(String code) throws Exception {
        StringBuilder json = new StringBuilder();
        //省厅数据
        List<SysAssessment> provinceSysAssessment = sysAssessmentDao.findLikeCode(code);
        //省厅算分
        Map<String, Double> score = getMapScore(provinceSysAssessment);
        //加入省头
        json.append("[");
        addHeader(code, json, score);
        //遍历省厅数据（加入省厅数据）
        if (provinceSysAssessment != null && provinceSysAssessment.size() > 0) {
            Iterator<SysAssessment> sysAssessmentIterator = provinceSysAssessment.listIterator();
            while (sysAssessmentIterator.hasNext()) {
                //加入数据
                addData(json, sysAssessmentIterator);
            }
        }
            //得到下面所有的市包括省本身
            List<ZoneCode> provinceCity = zoneCodeDao.findProvinceCity(code);
            //遍历市
            if (provinceCity != null && provinceCity.size() > 1) {
                Iterator<ZoneCode> zoneCodeIterator = provinceCity.iterator();
                while (zoneCodeIterator.hasNext()) {
                    ZoneCode city = zoneCodeIterator.next();
                    if (!city.getZonecode().equals(code)) {
                        //得到市局数据
                        List<SysAssessment> citySysAssessment = sysAssessmentDao.findLikeCode(city.getZonecode());
                        //市局算分
                        Map<String, Double> cityScore = getMapScore(citySysAssessment);
                        //加市名称（市局算分）
                        addHeader(city.getZonecode(),json,cityScore);
                        //加市局数据
                        if (citySysAssessment != null && citySysAssessment.size() > 0) {
                            Iterator<SysAssessment> cityIterator = citySysAssessment.listIterator();
                            while (cityIterator.hasNext()) {
                                addData(json, cityIterator);
                            }
                        }
                            //查找市下所有区
                            List<ZoneCode> cityCounty = zoneCodeDao.findCityCounty(city.getZonecode().substring(0, 4), city.getZonecode());
                            if (cityCounty != null && cityCounty.size() > 0) {
                                Iterator<ZoneCode> countyCodeIterator = cityCounty.iterator();
                                while (countyCodeIterator.hasNext()) {
                                    ZoneCode county = countyCodeIterator.next();
                                    //区局数据
                                    List<SysAssessment> countySysAssessment = sysAssessmentDao.findLikeCode(county.getZonecode());
                                    //区算分
                                    Map<String, Double> countyScore = getMapScore(countySysAssessment);
                                    //加区名称
                                    addHeader(county.getZonecode(),json,countyScore);
                                    //加区县数据
                                    if(countySysAssessment.size()>0&&countySysAssessment!=null){
                                        Iterator<SysAssessment> countyIteratorData = countySysAssessment.listIterator();
                                        while (countyIteratorData.hasNext()) {
                                            //加入数据
                                            addData(json,countyIteratorData);
                                        }
                                    }
                                    json.append("]}");
                                    if (countyCodeIterator.hasNext()) {
                                        json.append(",");
                                    }
                                }
                            }
                        json.append("]}");
                        if (zoneCodeIterator.hasNext()) {
                            json.append(",");
                        }
                    }
                }
            }else {
                //如果市为空，表明为直辖市，直接遍历区
                //直接查找市下所有区
                List<ZoneCode> cityCounty = zoneCodeDao.findCityCounty(code.substring(0,2), code);
                if (cityCounty != null && cityCounty.size() > 0) {
                    Iterator<ZoneCode> countyCodeIterator = cityCounty.iterator();
                    while (countyCodeIterator.hasNext()) {
                        ZoneCode county = countyCodeIterator.next();
                        //区局数据
                        List<SysAssessment> countySysAssessment = sysAssessmentDao.findLikeCode(county.getZonecode());
                        //区算分
                        Map<String, Double> countyScore = getMapScore(countySysAssessment);
                        //加区名称
                        addHeader(county.getZonecode(),json,countyScore);
                        //加区县数据
                        Iterator<SysAssessment> countyIteratorData = countySysAssessment.listIterator();
                        while (countyIteratorData.hasNext()) {
                            //加入数据
                            addData(json,countyIteratorData);
                        }
                        json.append("]}");
                        if (countyCodeIterator.hasNext()) {
                            json.append(",");
                        }
                    }
                }
            }
        json.append("]}]");
        return json.toString();
    }

    public String findCityCode(String code) throws Exception {
        StringBuilder json = new StringBuilder();
        //市局数据
        List<SysAssessment> citySysAssessment = sysAssessmentDao.findLikeCode(code);
        //市局算分
        Map<String, Double> score = getMapScore(citySysAssessment);
        //加入市头
        json.append("[");
        //加入头信息
        addHeader(code,json,score);
        //遍历市局数据（加入市局数据）
        if (citySysAssessment != null && citySysAssessment.size() > 0) {
            Iterator<SysAssessment> sysAssessmentIterator = citySysAssessment.listIterator();
            while (sysAssessmentIterator.hasNext()) {
                //加入数据
                addData(json, sysAssessmentIterator);
            }
        }
        //查找市下所有区
        List<ZoneCode> cityCounty = zoneCodeDao.findCityCounty(code.substring(0,4), code);
        if (cityCounty != null && cityCounty.size() > 0) {
            Iterator<ZoneCode> countyCodeIterator = cityCounty.iterator();
            while (countyCodeIterator.hasNext()) {
                ZoneCode county = countyCodeIterator.next();
                //区局数据
                List<SysAssessment> countySysAssessment = sysAssessmentDao.findLikeCode(county.getZonecode());
                //区算分
                Map<String, Double> countyScore = getMapScore(countySysAssessment);
                //加区名称
                addHeader(county.getZonecode(),json,countyScore);
                //加区县数据
                if(countySysAssessment!=null&&countySysAssessment.size()>0){
                    Iterator<SysAssessment> countyIteratorData = countySysAssessment.listIterator();
                    while (countyIteratorData.hasNext()) {
                        //加入数据
                        addData(json,countyIteratorData);
                    }
                }
                json.append("]}");
                if (countyCodeIterator.hasNext()) {
                    json.append(",");
                }
            }
        }
        json.append("]}]");
        return json.toString();
    }

    public String findCountyCode(String code) throws Exception {
        StringBuilder json = new StringBuilder();
        //区局数据
        List<SysAssessment> countySysAssessment = sysAssessmentDao.findLikeCode(code);
        //区算分
        Map<String, Double> countyScore = getMapScore(countySysAssessment);
        //加区名称
        json.append("[");
        addHeader(code,json,countyScore);
        //加区县数据
        if(countySysAssessment!=null&&countySysAssessment.size()>0){
            Iterator<SysAssessment> countyIteratorData = countySysAssessment.listIterator();
            while (countyIteratorData.hasNext()) {
                //加入数据
                addData(json,countyIteratorData);
            }
        }
        json.append("]}]");
       return json.toString();
    }
    
    public String findAllCode(String code){
        return null;
    }
    
    @Override
    public String findSysAssessment(String code) throws Exception {
       String json = null ;
        //如果为省
       if(code.length()==6&&code.endsWith("0000")){
//          json = findProvinceCode(code);
           json =  findProvinceCity(code);
       //如果为市
       }else if(code.length()==6&&code.endsWith("00")){
//          json = findCityCode(code);
            json =  findCityCounty(code);
       } else {
       //为区
//           json = findCountyCode(code);
             json = findCountyData(code);
       }
        return json;
    }

    @Override
    public String findSingleAssessment(String code) throws Exception {
        StringBuilder json = new StringBuilder();
        //省厅数据
        List<SysAssessment> provinceSysAssessment = sysAssessmentDao.findLikeCode(code);
        //省厅算分
        Map<String, Double> score = getMapScore(provinceSysAssessment);
        //加入省头
        json.append("[");
        addHeader(code, json, score,true);
        //遍历省厅数据（加入省厅数据）
//        if (provinceSysAssessment != null && provinceSysAssessment.size() > 0) {
//            Iterator<SysAssessment> sysAssessmentIterator = provinceSysAssessment.listIterator();
//            while (sysAssessmentIterator.hasNext()) {
//                //加入数据
//                addData(json, sysAssessmentIterator);
//            }
//        }
//        json.append("]}]");
        json.append("}]");
        return json.toString();
    }

    private void addHeader(String code, StringBuilder json, Map<String, Double> score, boolean b) {
        json.append("{");
        json.append("address:'").append(zoneCodeDao.getZoneCodeName(code)).append("'").append(",");
        json.append("reg_score:'").append(score.get("reg_score")).append("'").append(",");
        json.append("levelCode:'").append(code).append("'").append(",");
        json.append("abn_score:'").append(score.get("abn_score")).append("'").append(",");
        json.append("log_score:'").append(score.get("log_score")).append("'").append(",");
        json.append("ins_score:'").append(score.get("ins_score")).append("'").append(",");
        json.append("act_score:'").append(score.get("act_score")).append("'").append(",");
        json.append("sum_score:'").append(score.get("sum_score")).append("'").append(",");
        json.append("expanded:").append(b).append(",");
        json.append("uiProvider:'").append("col'").append(",");
        json.append("iconCls:'").append("task-folder'").append(",");
        json.append("leaf:").append(false)/*.append(",");
        json.append("children:[")*/;
    }

    private void addData(StringBuilder json, Iterator<SysAssessment> cityIterator) throws Exception {
        SysAssessment cityAssessment = cityIterator.next();
        json.append("{").append("address:'").append(sysRegInfDao.findSystemName(cityAssessment.getIdsystem())).append("'").append(",");
        json.append("reg_score:'").append(cityAssessment.getRegscore()).append("'").append(",");
        json.append("abn_score:'").append(cityAssessment.getAbnscore()).append("'").append(",");
        json.append("log_score:'").append(cityAssessment.getLogscore()).append("'").append(",");
        json.append("ins_score:'").append(cityAssessment.getResscore()).append("'").append(",");
        json.append("act_score:'").append(cityAssessment.getActscore()).append("'").append(",");
        json.append("sum_score:'").append(cityAssessment.getSumscore()).append("'").append(",");
        json.append("idsystem:'").append(cityAssessment.getIdsystem()).append("'").append(",");
        json.append("id:'").append(cityAssessment.getId()).append("'").append(",");
        json.append("uiProvider:'").append("col'").append(",");
        json.append("iconCls:'").append("task'").append(",");
        json.append("leaf:").append(true).append("}");
        if (cityIterator.hasNext()) {
            json.append(",");
        }
    }

    private void addHeader(String code, StringBuilder json, Map<String, Double> score) {
        json.append("{");
        json.append("address:'").append(zoneCodeDao.getZoneCodeName(code)).append("'").append(",");
        json.append("reg_score:'").append(score.get("reg_score")).append("'").append(",");
        json.append("levelCode:'").append(code).append("'").append(",");
        json.append("abn_score:'").append(score.get("abn_score")).append("'").append(",");
        json.append("log_score:'").append(score.get("log_score")).append("'").append(",");
        json.append("ins_score:'").append(score.get("ins_score")).append("'").append(",");
        json.append("act_score:'").append(score.get("act_score")).append("'").append(",");
        json.append("sum_score:'").append(score.get("sum_score")).append("'").append(",");
        json.append("uiProvider:'").append("col'").append(",");
        json.append("iconCls:'").append("task-folder'").append(",");
        json.append("leaf:").append(false)/*.append(",");
        json.append("children:[")*/;
    }

    private String findProvinceCity(String code) throws Exception {
        StringBuilder json = new StringBuilder();
        //省厅数据
        List<SysAssessment> provinceSysAssessment = sysAssessmentDao.findLikeCode(code);
        //省厅算分
        Map<String, Double> score = getMapScore(provinceSysAssessment);
        //加入省头
//        json.append("[");
//        addHeader(code, json, score);
        //遍历省厅数据（加入省厅数据）
        if (provinceSysAssessment != null && provinceSysAssessment.size() > 0) {
            Iterator<SysAssessment> sysAssessmentIterator = provinceSysAssessment.listIterator();
            while (sysAssessmentIterator.hasNext()) {
                //加入数据
                addData(json, sysAssessmentIterator);
            }
        }
       //省厅数据结束
        //得到下面所有的市包括省本身
        List<ZoneCode> provinceCity = zoneCodeDao.findProvinceCity(code);
        //遍历市
        if (provinceCity != null && provinceCity.size() > 1) {
            if(provinceSysAssessment.size()>0){
                json.append(",");
            }
            Iterator<ZoneCode> zoneCodeIterator = provinceCity.iterator();
            while (zoneCodeIterator.hasNext()) {
                ZoneCode city = zoneCodeIterator.next();
                if (!city.getZonecode().equals(code)) {
                    //得到市局数据
                    List<SysAssessment> citySysAssessment = sysAssessmentDao.findLikeCode(city.getZonecode());
                    //市局算分
                    Map<String, Double> cityScore = getMapScore(citySysAssessment);
                    //加市名称（市局算分）
                    addHeader(city.getZonecode(),json,cityScore);
                    //加市局数据
//                    if (citySysAssessment != null && citySysAssessment.size() > 0) {
//                        Iterator<SysAssessment> cityIterator = citySysAssessment.listIterator();
//                        while (cityIterator.hasNext()) {
//                            addData(json, cityIterator);
//                        }
//                    }
                    //查找市下所有区
//                    List<ZoneCode> cityCounty = zoneCodeDao.findCityCounty(city.getZonecode().substring(0, 4), city.getZonecode());
//                    if (cityCounty != null && cityCounty.size() > 0) {
//                        Iterator<ZoneCode> countyCodeIterator = cityCounty.iterator();
//                        while (countyCodeIterator.hasNext()) {
//                            ZoneCode county = countyCodeIterator.next();
//                            //区局数据
//                            List<SysAssessment> countySysAssessment = sysAssessmentDao.findLikeCode(county.getZonecode());
//                            //区算分
//                            Map<String, Double> countyScore = getMapScore(countySysAssessment);
//                            //加区名称
//                            addHeader(county.getZonecode(),json,countyScore);
//                            //加区县数据
//                            if(countySysAssessment.size()>0&&countySysAssessment!=null){
//                                Iterator<SysAssessment> countyIteratorData = countySysAssessment.listIterator();
//                                while (countyIteratorData.hasNext()) {
//                                    //加入数据
//                                    addData(json,countyIteratorData);
//                                }
//                            }
//                            json.append("]}");
//                            if (countyCodeIterator.hasNext()) {
//                                json.append(",");
//                            }
//                        }
//                    }
//                    json.append("]}");
                    json.append("}");
                    if (zoneCodeIterator.hasNext()) {
                        json.append(",");
                    }
                }
            }
        }else {
            //如果市为空，表明为直辖市，直接遍历区
            //直接查找市下所有区
            List<ZoneCode> cityCounty = zoneCodeDao.findCityCounty(code.substring(0,2), code);
            if (cityCounty != null && cityCounty.size() > 0) {
                if(provinceSysAssessment.size()>0){
                    json.append(",");
                }
                Iterator<ZoneCode> countyCodeIterator = cityCounty.iterator();
                while (countyCodeIterator.hasNext()) {
                    ZoneCode county = countyCodeIterator.next();
                    //区局数据
                    List<SysAssessment> countySysAssessment = sysAssessmentDao.findLikeCode(county.getZonecode());
                    //区算分
                    Map<String, Double> countyScore = getMapScore(countySysAssessment);
                    //加区名称
                    addHeader(county.getZonecode(),json,countyScore);
                    //加区县数据
//                    Iterator<SysAssessment> countyIteratorData = countySysAssessment.listIterator();
//                    while (countyIteratorData.hasNext()) {
//                        //加入数据
//                        addData(json,countyIteratorData);
//                    }
//                    json.append("]}");
                    json.append("}");
                    if (countyCodeIterator.hasNext()) {
                        json.append(",");
                    }
                }
            }
        }
//        json.append("]}]");
        return json.toString();
    }

    private String findCityCounty(/*ZoneCode city*/String code) throws Exception {
        StringBuilder json = new StringBuilder();
        //得到市局数据
        List<SysAssessment> citySysAssessment = sysAssessmentDao.findLikeCode(/*city.getZonecode()*/code);
        //加市局数据
        if (citySysAssessment != null && citySysAssessment.size() > 0) {
            Iterator<SysAssessment> cityIterator = citySysAssessment.listIterator();
            while (cityIterator.hasNext()) {
                addData(json, cityIterator);
            }
        }
//        查找市下所有区
        List<ZoneCode> cityCounty = zoneCodeDao.findCityCounty(/*city.getZonecode()*/code.substring(0, 4), code/*city.getZonecode()*/);
        if (cityCounty != null && cityCounty.size() > 0) {
            if(citySysAssessment.size()>0){
                json.append(",");
            }
            Iterator<ZoneCode> countyCodeIterator = cityCounty.iterator();
            while (countyCodeIterator.hasNext()) {
                ZoneCode county = countyCodeIterator.next();
                //区局数据
                List<SysAssessment> countySysAssessment = sysAssessmentDao.findLikeCode(county.getZonecode());
                //区算分
                Map<String, Double> countyScore = getMapScore(countySysAssessment);
                //加区名称
                addHeader(county.getZonecode(),json,countyScore);
                //加区县数据
//                if(countySysAssessment.size()>0&&countySysAssessment!=null){
//                    Iterator<SysAssessment> countyIteratorData = countySysAssessment.listIterator();
//                    while (countyIteratorData.hasNext()) {
//                        //加入数据
//                        addData(json,countyIteratorData);
//                    }
//                }
//                json.append("]}");
                json.append("}");
                if (countyCodeIterator.hasNext()) {
                    json.append(",");
                }
            }
        }
        return json.toString();
    }

    private String findCountyData(/*ZoneCode county*/String code) throws Exception {
        StringBuilder json = new StringBuilder();
        //区局数据
        List<SysAssessment> countySysAssessment = sysAssessmentDao.findLikeCode(/*county.getZonecode()*/code);
        //加区县数据
        if(countySysAssessment.size()>0&&countySysAssessment!=null){
            Iterator<SysAssessment> countyIteratorData = countySysAssessment.listIterator();
            while (countyIteratorData.hasNext()) {
                //加入数据
                addData(json,countyIteratorData);
            }
        }
        return json.toString();
    }

}
