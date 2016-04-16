package com.hzih.mmcs.service.impl;

import com.hzih.mmcs.dao.DistrictDao;
import com.hzih.mmcs.dao.StatSysStatusDao;
import com.hzih.mmcs.dao.DistrictDao;
import com.hzih.mmcs.dao.SysRegInfDao;
import com.hzih.mmcs.domain.District;
import com.hzih.mmcs.domain.StatSysStatus;
import com.hzih.mmcs.domain.District;
import com.hzih.mmcs.service.StatSysStatusService;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-10
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public class StatSysStatusServiceImpl implements StatSysStatusService {
    private StatSysStatusDao statSysStatusDao;
//    private DistrictDao DistrictDao;
    private DistrictDao districtDao;
    private SysRegInfDao sysRegInfDao;

    public StatSysStatusDao getStatSysStatusDao() {
        return statSysStatusDao;
    }

    public void setStatSysStatusDao(StatSysStatusDao statSysStatusDao) {
        this.statSysStatusDao = statSysStatusDao;
    }

   /* public DistrictDao getDistrictDao() {
        return DistrictDao;
    }

    public void setDistrictDao(DistrictDao DistrictDao) {
        this.DistrictDao = DistrictDao;
    }*/

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

    @Override
public String selectAll(String District)throws Exception {
    StringBuffer json = new StringBuffer();
    json.append("[");
    String zone = "";
    String statSysStatusDistrict="";
    String DistrictFlag="";
    boolean firstCity = false;
    boolean province = false;
    boolean city = false;
    boolean provinceToCity = false;

    if(District.contains("0000")){  //判断用户权限 省
        zone = District.substring(0,6);
        String  provincecode = District.substring(0,2);
        String zonesql = " from District where id = '"+zone+"'";
        List zonelist = districtDao.list(zonesql);
        District z = (District) zonelist.get(0);
        json.append("{zonename:'"+z.getDistrictName()+"',uiProvider:'col',");
        String hql = "from StatSysStatus where idsystem like '"+provincecode+"%' order by idsystem asc ";
        List statSysStatusList = statSysStatusDao.list(hql);
        for(int i = 0;i<statSysStatusList.size();i++){
            StatSysStatus s = (StatSysStatus) statSysStatusList.get(i);
            statSysStatusDistrict = s.getIdsystem().substring(0,6);
            //判断是否是省厅的数据
            if(statSysStatusDistrict.equalsIgnoreCase(zone)){
                //判断之前有没有相同层次的数据
                if(statSysStatusDistrict.equalsIgnoreCase(DistrictFlag)){
                    json.append(",");
                }
                DistrictFlag = statSysStatusDistrict;
                //判断是否有省头
                if(!province){
                    //省头
                    json.append("children:[{zonename:'"+z.getDistrictName()+"省厅',");
                    json.append("uiProvider:'col',");
                    //省厅数据
                    json.append("children:[{zonename:'"+z.getDistrictName()+s.getIdsystem().substring(6,8)+"',");
                    json.append("bizsum:'"+s.getBizsum()+"',");
                    json.append("accesssum:'"+s.getAccesssum()+"',");
                    json.append("terminalnum:'"+s.getTerminalnum()+"',");
                    json.append("outflux:'"+s.getOutflux()+"',");
                    json.append("monthaccess:'"+s.getMonthaccess()+"',");
                    json.append("monthterminalnum:'"+s.getMonthterminalnum()+"',");
                    json.append("monthoutflux:'"+s.getMonthoutflux()+"',");
                    json.append("sysrunpresent:'"+s.getSysrunpresent()+"',");
                    json.append(" uiProvider:'col'," +
                            "cls:'master-task'," +
                            "expanded:true," +
                            "leaf:true," +
                            "iconCls:'task-folder'");
                    json.append("}");
                    province = true;
                }else{
                    json = StatSysStatusJson(json,s,z);
                    json.append(" uiProvider:'col'," +
                            "cls:'master-task'," +
                            "leaf:true," +
                            "iconCls:'task-folder'");
                    json.append("}");
                }
            }else{
                if(!provinceToCity){
                    //判断之前有没有相同层次的数据
                    if(DistrictFlag.equalsIgnoreCase(statSysStatusDistrict.substring(0,6))){
                        if(province){
                            if(firstCity){
                                json.append(",");
                            }
                            firstCity = true;

                        }
                    }else {
                        json.append("]},");
                        city = false;
                        firstCity = false;
                    }
                    provinceToCity = true;
                    DistrictFlag = statSysStatusDistrict.substring(0,4);
                } else{
                    //判断之前有没有相同层次的数据
                    if(DistrictFlag.equalsIgnoreCase(statSysStatusDistrict.substring(0,4))){
                        if(province){
                            if(firstCity){
                                json.append(",");
                            }
                            firstCity = true;
                        }
                    }else {
                        json.append("]" +
                                "        },");
                        city = false;
                        firstCity = false;
                    }
                    DistrictFlag = statSysStatusDistrict.substring(0,4);
                }
                String cityzonesql = " from District where id = '"+statSysStatusDistrict+"'";
                List  cityzonelist = districtDao.list(cityzonesql);
                District cityzone = (District) cityzonelist.get(0);
                if(!province){
                    json.append("children:[{zonename:'"+z.getDistrictName()+"省厅',");
                    json.append("uiProvider:'col'," +
                            "            cls:'master-task'," +
                            "            iconCls:'task-1'," +
                            "             leaf:false,");
                    //省厅数据
                    json.append("children:[{},");
                    province = true;
                }
                if(!city ){
//                    System.out.println(json.toString());
                    /*if(!json.toString().contains(cityzone.getZonename()+"',")){   //如果json里存在此条数据 不加
                        json.append("{zonename:'"+cityzone.getZonename()+"',");
                        json.append("uiProvider:'col'," +
                                "cls:'master-task'," +
                                "leaf:false," +
                                "iconCls:'task-1'," +
                                "children:[");
                    }*/
                    if(s.getIdsystem().substring(0,6).endsWith("00")){
                        json.append("{zonename:'"+cityzone.getDistrictName()+s.getIdsystem().substring(6,8)+"',");
                        json.append("bizsum:'"+s.getBizsum()+"',");
                        json.append("accesssum:'"+s.getAccesssum()+"',");
                        json.append("terminalnum:'"+s.getTerminalnum()+"',");
                        json.append("outflux:'"+s.getOutflux()+"',");
                        json.append("monthaccess:'"+s.getMonthaccess()+"',");
                        json.append("monthterminalnum:'"+s.getMonthterminalnum()+"',");
                        json.append("monthoutflux:'"+s.getMonthoutflux()+"',");
                        json.append("sysrunpresent:'"+s.getSysrunpresent()+"',");
                        json.append("  uiProvider:'col'," +
                                "            cls:'master-task'," +
                                "            leaf:false," +
                                "            iconCls:'task-1'," +
                                "            children:[");
                    } else{
                        json.append("{zonename:'"+cityzone.getDistrictName()+"',");
                        json.append("  uiProvider:'col'," +
                                "            cls:'master-task'," +
                                "            leaf:false," +
                                "            iconCls:'task-1'," +
                                "            children:[");
                    }

                    city = true;
                }else{
                    json = StatSysStatusJson(json,s,cityzone);
                    json.append(" uiProvider:'col'," +
                            "cls:'master-task'," +
                            " leaf:true," +
                            "iconCls:'task-folder'");
                    json.append("}");

                    //判断之前有没有相同层次的数据
                    if(DistrictFlag.equalsIgnoreCase(statSysStatusDistrict.substring(0,4))){
                        json.append(",");
                    }
                }

            }
        }
    }else{               //市        然后取前两位 还要把省名查出来
        zone = District.substring(0,2);
        String zonesql = " from District where id = '"+zone+"00'";
        List zonelist = districtDao.list(zonesql);
        District z = (District) zonelist.get(0);
        json.append("{zonename:'"+z.getDistrictName()+"',uiProvider:'col',");
        String hql = "from StatSysStatus where idsystem like '"+zone.substring(0,4)+"%' order by idsystem asc ";
        List statSysStatusList = statSysStatusDao.list(hql);
        for(int i = 0;i<statSysStatusList.size();i++){
            StatSysStatus s = (StatSysStatus) statSysStatusList.get(i);
            statSysStatusDistrict = s.getIdsystem().substring(0,4);
        }

    }
    json.append("   ]" +    "    }" +       "        ]" +            //市内容皆为
            "    }" +      //省内容结尾
            "]") ;



    return json.toString().replace("},]","}]");
}

    @Override
    public String findStatSysStatus(String code)throws Exception {
        StringBuilder json = new StringBuilder();
        if (code.length() == 6 && code.endsWith("0000")) {
            //省厅数据
            String hql = "from StatSysStatus where idsystem like '"+code+"%' order by idsystem asc ";
            List statSysStatusList = statSysStatusDao.list(hql);
            String zonesql = " from District where id = '"+code+"' ";
            List zonelist = districtDao.list(zonesql);
            District z = (District) zonelist.get(0);
            //加一个省
            //省头
            json.append("[{");
            json.append("zonename:'"+z.getDistrictName()+"',");
            //todo
            json=totalProvince(json,code);
            json.append("uiProvider:'").append("col'").append(",");
            json.append("expanded:true,");
            json.append("iconCls:'").append("task-folder'").append(",");
            json.append("leaf:").append(false).append(",");
            json.append("children:[");
            //遍历省厅数据（加入省厅数据）
            if (statSysStatusList != null && statSysStatusList.size() > 0) {
                Iterator<StatSysStatus> StatSysStatusIterator = statSysStatusList.listIterator();
                while (StatSysStatusIterator.hasNext()) {
                    StatSysStatus statSysStatus = StatSysStatusIterator.next();
                    String  zhql = " from District where id = '"+code+"' ";
                    List lt = districtDao.list(zhql);
                    District zc = (District) lt.get(0);
                    json.append("{");
                     String zonename=sysRegInfDao.findSystemName(zc.getId()+statSysStatus.getIdsystem().substring(6,8));
//                    json.append("zonename:'"+zc.getDistrictName()+statSysStatus.getIdsystem().substring(6,8)+"',");
                    json.append("zonename:'"+zonename+"',");
                    json.append("idsystem:'"+statSysStatus.getIdsystem()+"',");
                    json.append("bizsum:'"+statSysStatus.getBizsum()+"',");
                    json.append("accesssum:'"+statSysStatus.getAccesssum()+"',");
                    json.append("terminalnum:'"+statSysStatus.getTerminalnum()+"',");
                    json.append("outflux:'"+statSysStatus.getOutflux()+"',");
                    json.append("monthaccess:'"+statSysStatus.getMonthaccess()+"',");
                    json.append("monthterminalnum:'"+statSysStatus.getMonthterminalnum()+"',");
                    json.append("monthoutflux:'"+statSysStatus.getMonthoutflux()+"',");
                    json.append("sysrunpresent:'"+statSysStatus.getSysrunpresent()+"',");
                    json.append("uiProvider:'").append("col'").append(",");
                    json.append("iconCls:'").append("task'").append(",");
                    json.append("leaf:").append(true).append("}");
                    if (StatSysStatusIterator.hasNext()) {
                        json.append(",");
                    }
                }
                //得到下面所有的市
                String hqlCity="from District where id like '"+code.substring(0,2)+"__00'";
                List<District> provinceCity = districtDao.list(hqlCity);
                if (provinceCity != null && provinceCity.size() > 1) {
                    Iterator<District> DistrictIterator = provinceCity.iterator();
                    while (DistrictIterator.hasNext()) {
                        District city = DistrictIterator.next();
                        if (!city.getId().toString().equals(code)) {  //如果不是省数据
                            //得到市局数据
                            String hqlStat="from StatSysStatus where idsystem like '"+city.getId().toString().substring(0,4)+"%' order by idsystem asc ";
                            List<StatSysStatus> cityStatSysStatus = statSysStatusDao.list(hqlStat);
                            //加市名称
                            json.append("{");
                            json.append("zonename:'"+city.getDistrictName()+"',");
                            //todo
                            json=totalCity(json,city.getId().toString());
                            json.append("uiProvider:'").append("col'").append(",");
                            json.append("iconCls:'").append("task-folder'").append(",");
                            json.append("leaf:").append(false).append(",");
                            json.append("children:[");
                            //加市局数据
                            if (cityStatSysStatus != null && cityStatSysStatus.size() > 0) {
                                Iterator<StatSysStatus> cityIterator = cityStatSysStatus.listIterator();
                                while (cityIterator.hasNext()) {
                                    StatSysStatus statSysStatus = cityIterator.next();
                                    json.append("{");
                                    json.append("idsystem:'"+statSysStatus.getIdsystem()+"',");
                                    String zonename=sysRegInfDao.findSystemName(city.getId()+statSysStatus.getIdsystem().substring(6,8));
                                    json.append("zonename:'"+zonename+"',");
//                                    json.append("zonename:'"+city.getDistrictName()+statSysStatus.getIdsystem().substring(6,8)+"',");
                                    json.append("bizsum:'"+statSysStatus.getBizsum()+"',");
                                    json.append("accesssum:'"+statSysStatus.getAccesssum()+"',");
                                    json.append("terminalnum:'"+statSysStatus.getTerminalnum()+"',");
                                    json.append("outflux:'"+statSysStatus.getOutflux()+"',");
                                    json.append("monthaccess:'"+statSysStatus.getMonthaccess()+"',");
                                    json.append("monthterminalnum:'"+statSysStatus.getMonthterminalnum()+"',");
                                    json.append("monthoutflux:'"+statSysStatus.getMonthoutflux()+"',");
                                    json.append("sysrunpresent:'"+statSysStatus.getSysrunpresent()+"',");
                                    json.append("uiProvider:'").append("col'").append(",");
                                    json.append("iconCls:'").append("task'").append(",");
                                    json.append("leaf:").append(true).append("}");
                                    if (cityIterator.hasNext()) {
                                        json.append(",");
                                    }
                                }
//                                System.out.println("json="+json.toString());
                                //得到下面所有的区县
                                String hqlcounty="from District where id like '"+city.getId().toString().substring(0,4)+"%'";
                                List<District> countyCity = districtDao.list(hqlcounty);
                                //遍历区县
                                //加区县名称
                                if (countyCity != null && countyCity.size() > 0) {
                                    Iterator<District> countyCodeIterator = countyCity.iterator();
                                    while (countyCodeIterator.hasNext()) {
                                        District county = countyCodeIterator.next();
                                        if(!county.getId().toString().equals(city.getId().toString())){
                                            //区局数据
                                            //得到区县数据
                                            String hqlStatCounty="from StatSysStatus where idsystem like '"+county.getId().toString()+"%' order by idsystem asc ";
                                            List<StatSysStatus> countyStatSysStatus = statSysStatusDao.list(hqlStatCounty);
                                            //加区名称
                                            json.append(",");
                                            json.append("{");
                                            json.append("zonename:'"+county.getDistrictName()+"',");
                                           json=totalCounty(json,county.getId().toString());
                                            json.append("uiProvider:'").append("col'").append(",");
                                            json.append("iconCls:'").append("task-folder'").append(",");
                                            json.append("leaf:").append(false).append(",");
                                            json.append("children:[");
                                            //加区县数据
                                            Iterator<StatSysStatus> countyIteratorData = countyStatSysStatus.listIterator();
                                            while (countyIteratorData.hasNext()) {
                                                StatSysStatus countyStatSysStatusData = countyIteratorData.next();
                                                json.append("{");
                                                json.append("idsystem:'"+countyStatSysStatusData.getIdsystem()+"',");
                                                String zonename=sysRegInfDao.findSystemName(county.getId()+countyStatSysStatusData.getIdsystem().substring(6,8));
                                                json.append("zonename:'"+zonename+"',");
//                                                json.append("zonename:'"+county.getDistrictName()+countyStatSysStatusData.getIdsystem().substring(6,8)+"',");
                                                json.append("bizsum:'"+countyStatSysStatusData.getBizsum()+"',");
                                                json.append("accesssum:'"+countyStatSysStatusData.getAccesssum()+"',");
                                                json.append("terminalnum:'"+countyStatSysStatusData.getTerminalnum()+"',");
                                                json.append("outflux:'"+countyStatSysStatusData.getOutflux()+"',");
                                                json.append("monthaccess:'"+countyStatSysStatusData.getMonthaccess()+"',");
                                                json.append("monthterminalnum:'"+countyStatSysStatusData.getMonthterminalnum()+"',");
                                                json.append("monthoutflux:'"+countyStatSysStatusData.getMonthoutflux()+"',");
                                                json.append("sysrunpresent:'"+countyStatSysStatusData.getSysrunpresent()+"',");
                                                json.append("uiProvider:'").append("col'").append(",");
                                                json.append("iconCls:'").append("task'").append(",");
                                                json.append("leaf:").append(true);
                                                json.append("}");
                                                if (countyIteratorData.hasNext()) {
                                                    json.append(",");
                                                }
                                            }
                                            json.append("]}");
                                        }
                                    }
                                    if (countyCodeIterator.hasNext()) {
                                        json.append(",");
                                    }
                                }
                            }
                            json.append("]}");
                        }
                        if (DistrictIterator.hasNext()) {
                            json.append(",");
                        }
                    }
                } else {
                    //如果市为空，表明为直辖市，直接遍历区
                    //直接查找市下所有区
                    //得到下面所有的区县
                    String hqlcounty="from District where id like '"+code.substring(0,2)+"%'";
                    List<District> countyCity = districtDao.list(hqlcounty);
                    //遍历区县

                    //加区县名称
                    if (countyCity != null && countyCity.size() > 1) {
                        Iterator<District> countyCodeIterator = countyCity.iterator();
                        while (countyCodeIterator.hasNext()) {
                            District county = countyCodeIterator.next();
                            if(!county.getId().toString().equals(code)){
                                //区局数据
                                //得到区县数据
                                String hqlStatCounty="from StatSysStatus where idsystem like '"+county.getId().toString()+"%' order by idsystem asc ";
                                List<StatSysStatus> countyStatSysStatus = statSysStatusDao.list(hqlStatCounty);
                                //加区名称
                                json.append(",");
                                json.append("{");
                                json.append("zonename:'"+county.getDistrictName()+"',");
                                json=totalCounty(json,county.getId().toString());
                                json.append("uiProvider:'").append("col'").append(",");
                                json.append("iconCls:'").append("task-folder'").append(",");
                                json.append("leaf:").append(false).append(",");
                                json.append("children:[");
                                //加区县数据
                                Iterator<StatSysStatus> countyIteratorData = countyStatSysStatus.listIterator();
                                while (countyIteratorData.hasNext()) {
                                    StatSysStatus countyStatSysStatusData = countyIteratorData.next();
//                                    String cy="from District where id like '"+countyStatSysStatusData.getIdsystem()+"%'";
//                                    List<District> cty = DistrictDao.list(cy);
                                    json.append("{");
                                    json.append("idsystem:'"+countyStatSysStatusData.getIdsystem()+"',");
                                    String zonename=sysRegInfDao.findSystemName(county.getId()+countyStatSysStatusData.getIdsystem().substring(6,8));
                                    json.append("zonename:'"+zonename+"',");
//                                    json.append("zonename:'"+county.getDistrictName()+countyStatSysStatusData.getIdsystem().substring(6,8)+"',");
                                    json.append("bizsum:'"+countyStatSysStatusData.getBizsum()+"',");
                                    json.append("accesssum:'"+countyStatSysStatusData.getAccesssum()+"',");
                                    json.append("terminalnum:'"+countyStatSysStatusData.getTerminalnum()+"',");
                                    json.append("outflux:'"+countyStatSysStatusData.getOutflux()+"',");
                                    json.append("monthaccess:'"+countyStatSysStatusData.getMonthaccess()+"',");
                                    json.append("monthterminalnum:'"+countyStatSysStatusData.getMonthterminalnum()+"',");
                                    json.append("monthoutflux:'"+countyStatSysStatusData.getMonthoutflux()+"',");
                                    json.append("sysrunpresent:'"+countyStatSysStatusData.getSysrunpresent()+"',");
                                    json.append("uiProvider:'").append("col'").append(",");
                                    json.append("iconCls:'").append("task'").append(",");
                                    json.append("leaf:").append(true);
                                    json.append("}");
                                    if (countyIteratorData.hasNext()) {
                                        json.append(",");
                                    }
                                }
                                json.append("]}");
                            }
                        }
                        if (countyCodeIterator.hasNext()) {
                            json.append(",");
                        }
                    }
                }
            }
            json.append("]}]");
        }
        return json.toString();
    }


    public StringBuffer StatSysStatusJson(StringBuffer json,StatSysStatus s,District cityzone)throws Exception{
        json.append("{zonename:'"+cityzone.getDistrictName()+s.getIdsystem().substring(6,8)+"',");
        json.append("bizsum:'"+s.getBizsum()+"',");
        json.append("accesssum:'"+s.getAccesssum()+"',");
        json.append("terminalnum:'"+s.getTerminalnum()+"',");
        json.append("outflux:'"+s.getOutflux()+"',");
        json.append("monthaccess:'"+s.getMonthaccess()+"',");
        json.append("monthterminalnum:'"+s.getMonthterminalnum()+"',");
        json.append("monthoutflux:'"+s.getMonthoutflux()+"',");
        json.append("sysrunpresent:'"+s.getSysrunpresent()+"',");
        return json;
    }

    /**
     * 统计省
     * @param json
     * @param District
     * @return
     */
    public StringBuilder totalProvince(StringBuilder json,String District)throws Exception{
        int  terminalnum = 0;
        int  bizsum = 0;
        int  accesssum = 0;
        int  outflux = 0;
        int  monthoutflux = 0;
        int  monthaccess = 0;
        int  monthterminalnum = 0;
        float  sysrunpresent = 0;
        String hql = "from StatSysStatus where idsystem like '"+District.substring(0,2)+"%'";
        List list = statSysStatusDao.list(hql);
        for(int i = 0;i<list.size();i++){
            StatSysStatus statSysStatus = (StatSysStatus) list.get(i);
            terminalnum += statSysStatus.getTerminalnum();
            bizsum += statSysStatus.getBizsum();
            accesssum += statSysStatus.getAccesssum();
            outflux += statSysStatus.getOutflux();
            monthaccess += statSysStatus.getMonthaccess();
            monthoutflux += statSysStatus.getMonthoutflux();
            monthterminalnum += statSysStatus.getMonthterminalnum();
            sysrunpresent += Float.valueOf(statSysStatus.getSysrunpresent());
        }
        sysrunpresent=sysrunpresent/list.size();
        json.append("bizsum:'"+bizsum+"',");
        json.append("accesssum:'"+accesssum+"',");
        json.append("terminalnum:'"+terminalnum+"',");
        json.append("outflux:'"+outflux+"',");
        json.append("monthaccess:'"+monthaccess+"',");
        json.append("monthterminalnum:'"+monthterminalnum+"',");
        json.append("monthoutflux:'"+monthoutflux+"',");
        json.append("sysrunpresent:'"+sysrunpresent+"',");
        return json;
    }

    /**
     * 统计城市
     * @param json
     * @param District
     * @return
     */
    public StringBuilder totalCity(StringBuilder json,String District)throws Exception{
        int  terminalnum = 0;
        int  bizsum = 0;
        int  accesssum = 0;
        int  outflux = 0;
        int  monthoutflux = 0;
        int  monthaccess = 0;
        int  monthterminalnum = 0;
        float  sysrunpresent = 0;
        String hql = "from StatSysStatus where idsystem like '"+District.substring(0,4)+"%'";
        List list = statSysStatusDao.list(hql);
        for(int i = 0;i<list.size();i++){
            StatSysStatus statSysStatus = (StatSysStatus) list.get(i);
            terminalnum += statSysStatus.getTerminalnum();
            bizsum += statSysStatus.getBizsum();
            accesssum += statSysStatus.getAccesssum();
            outflux += statSysStatus.getOutflux();
            monthaccess += statSysStatus.getMonthaccess();
            monthoutflux += statSysStatus.getMonthoutflux();
            monthterminalnum += statSysStatus.getMonthterminalnum();
            sysrunpresent += Float.valueOf(statSysStatus.getSysrunpresent());
        }
        sysrunpresent=sysrunpresent/list.size();
        json.append("bizsum:'"+bizsum+"',");
        json.append("accesssum:'"+accesssum+"',");
        json.append("terminalnum:'"+terminalnum+"',");
        json.append("outflux:'"+outflux+"',");
        json.append("monthaccess:'"+monthaccess+"',");
        json.append("monthterminalnum:'"+monthterminalnum+"',");
        json.append("monthoutflux:'"+monthoutflux+"',");
        json.append("sysrunpresent:'"+sysrunpresent+"',");
        return json;
    }

    /**
     * 统计区总数
     * @param json
     * @param District
     * @return
     */
    public StringBuilder totalCounty(StringBuilder json,String District)throws Exception{
        int  terminalnum = 0;
        int  bizsum = 0;
        int  accesssum = 0;
        int  outflux = 0;
        int  monthoutflux = 0;
        int  monthaccess = 0;
        int  monthterminalnum = 0;
        float  sysrunpresent = 0;
        String hql = "from StatSysStatus where idsystem like '"+District.substring(0,6)+"%'";
        List list = statSysStatusDao.list(hql);
        for(int i = 0;i<list.size();i++){
            StatSysStatus statSysStatus = (StatSysStatus) list.get(i);
            terminalnum += statSysStatus.getTerminalnum();
            bizsum += statSysStatus.getBizsum();
            accesssum += statSysStatus.getAccesssum();
            outflux += statSysStatus.getOutflux();
            monthaccess += statSysStatus.getMonthaccess();
            monthoutflux += statSysStatus.getMonthoutflux();
            monthterminalnum += statSysStatus.getMonthterminalnum();
            sysrunpresent += Float.valueOf(statSysStatus.getSysrunpresent());
        }
        sysrunpresent=sysrunpresent/list.size();
        json.append("bizsum:'"+bizsum+"',");
        json.append("accesssum:'"+accesssum+"',");
        json.append("terminalnum:'"+terminalnum+"',");
        json.append("outflux:'"+outflux+"',");
        json.append("monthaccess:'"+monthaccess+"',");
        json.append("monthterminalnum:'"+monthterminalnum+"',");
        json.append("monthoutflux:'"+monthoutflux+"',");
        json.append("sysrunpresent:'"+sysrunpresent+"',");
        return json;
    }

    /**
     * 市级单位
     * @param code
     * @return
     */
    public String findCityCode(String code)throws Exception{
        StringBuilder json = new StringBuilder();
        //市局数据
        //得到市局数据
        String hqlStat="from StatSysStatus where idsystem like '"+code+"%' order by idsystem asc ";
        List<StatSysStatus> cityStatSysStatus = statSysStatusDao.list(hqlStat);
        String  zhql = " from District where id = '"+code+"' ";
        List lt = districtDao.list(zhql);
        District zc = (District) lt.get(0);
        //加入市头
        json.append("[");
        //加入头信息
        json.append("{");
        json.append("zonename:'"+zc.getDistrictName()+"',");
        //todo
        json=totalCity(json,code);
        json.append("uiProvider:'").append("col'").append(",");
        json.append("expanded:true,");
        json.append("iconCls:'").append("task-folder'").append(",");
        json.append("leaf:").append(false).append(",");
        json.append("children:[");
        //加市局数据
        if (cityStatSysStatus != null && cityStatSysStatus.size() > 0) {
            Iterator<StatSysStatus> cityIterator = cityStatSysStatus.listIterator();
            while (cityIterator.hasNext()) {
                StatSysStatus statSysStatus = cityIterator.next();
                json.append("{");
                json.append("idsystem:'"+statSysStatus.getIdsystem()+"',");
                String zonename=sysRegInfDao.findSystemName(zc.getId()+statSysStatus.getIdsystem().substring(6,8));
                json.append("zonename:'"+zonename+"',");
//                json.append("zonename:'"+zc.getDistrictName()+statSysStatus.getIdsystem().substring(6,8)+"',");
                json.append("bizsum:'"+statSysStatus.getBizsum()+"',");
                json.append("accesssum:'"+statSysStatus.getAccesssum()+"',");
                json.append("terminalnum:'"+statSysStatus.getTerminalnum()+"',");
                json.append("outflux:'"+statSysStatus.getOutflux()+"',");
                json.append("monthaccess:'"+statSysStatus.getMonthaccess()+"',");
                json.append("monthterminalnum:'"+statSysStatus.getMonthterminalnum()+"',");
                json.append("monthoutflux:'"+statSysStatus.getMonthoutflux()+"',");
                json.append("sysrunpresent:'"+statSysStatus.getSysrunpresent()+"',");
                json.append("uiProvider:'").append("col'").append(",");
                json.append("iconCls:'").append("task'").append(",");
                json.append("leaf:").append(true).append("}");
                if (cityIterator.hasNext()) {
                    json.append(",");
                }
            }
            //查找市下所有区
            //得到下面所有的区县
            String hqlcounty="from District where id like '"+code.substring(0,4)+"%'";
            List<District> countyCity = districtDao.list(hqlcounty);
            //遍历区县

            if (countyCity != null && countyCity.size() > 0) {
                Iterator<District> countyCodeIterator = countyCity.iterator();
                while (countyCodeIterator.hasNext()) {
                    District county = countyCodeIterator.next();
                    if(!county.getId().toString().equals(code)){
                        //区局数据
                        //得到区县数据
                        String hqlStatCounty="from StatSysStatus where idsystem like '"+county.getId().toString()+"%' order by idsystem asc ";
                        List<StatSysStatus> countyStatSysStatus = statSysStatusDao.list(hqlStatCounty);
                        //加区名称
                        json.append(",");
                        json.append("{");
                        json.append("zonename:'"+county.getDistrictName()+"',");
                        json=totalCounty(json,county.getId().toString());
                        json.append("uiProvider:'").append("col'").append(",");
                        json.append("iconCls:'").append("task-folder'").append(",");
                        json.append("leaf:").append(false).append(",");
                        json.append("children:[");
                        //加区县数据
                        Iterator<StatSysStatus> countyIteratorData = countyStatSysStatus.listIterator();
                        while (countyIteratorData.hasNext()) {
                            StatSysStatus countyStatSysStatusData = countyIteratorData.next();
                            json.append("{");
                            json.append("idsystem:'"+countyStatSysStatusData.getIdsystem()+"',");
                            String zonename=sysRegInfDao.findSystemName(county.getId()+countyStatSysStatusData.getIdsystem().substring(6,8));
                            json.append("zonename:'"+zonename+"',");
//                            json.append("zonename:'"+county.getDistrictName()+countyStatSysStatusData.getIdsystem().substring(6,8)+"',");
                            json.append("bizsum:'"+countyStatSysStatusData.getBizsum()+"',");
                            json.append("accesssum:'"+countyStatSysStatusData.getAccesssum()+"',");
                            json.append("terminalnum:'"+countyStatSysStatusData.getTerminalnum()+"',");
                            json.append("outflux:'"+countyStatSysStatusData.getOutflux()+"',");
                            json.append("monthaccess:'"+countyStatSysStatusData.getMonthaccess()+"',");
                            json.append("monthterminalnum:'"+countyStatSysStatusData.getMonthterminalnum()+"',");
                            json.append("monthoutflux:'"+countyStatSysStatusData.getMonthoutflux()+"',");
                            json.append("sysrunpresent:'"+countyStatSysStatusData.getSysrunpresent()+"',");
                            json.append("uiProvider:'").append("col'").append(",");
                            json.append("iconCls:'").append("task'").append(",");
                            json.append("leaf:").append(true);
                            json.append("}");
                            if (countyIteratorData.hasNext()) {
                                json.append(",");
                            }
                        }
                        json.append("]}");
                    }
                }
                if (countyCodeIterator.hasNext()) {
                    json.append(",");
                }
            }
        }
        json.append("]}]");
        return json.toString();
    }

    /**
     * 区级单位
     * @param code
     * @return
     */
    public String findCountyCode(String code)throws Exception{
        StringBuilder json = new StringBuilder();
        //得到区县
        String hqlcounty="from District where id like '"+code+"%'";
        List<District> countyCity = districtDao.list(hqlcounty);
        District cy=countyCity.get(0);
        //遍历区县
        //区局数据
        String hqlStatCounty="from StatSysStatus where idsystem like '"+code+"%' order by idsystem asc ";
        List<StatSysStatus> countyStatSysStatus = statSysStatusDao.list(hqlStatCounty);
        //加区名称
        json.append("[");
        json.append("{");
        json.append("zonename:'"+cy.getDistrictName()+"',");
        json=totalCounty(json,code);
        json.append("uiProvider:'").append("col'").append(",");
        json.append("expanded:true,");
        json.append("iconCls:'").append("task-folder'").append(",");
        json.append("leaf:").append(false).append(",");
        json.append("children:[");
        //加区县数据
        //加区县数据
        Iterator<StatSysStatus> countyIteratorData = countyStatSysStatus.listIterator();
        while (countyIteratorData.hasNext()) {
            StatSysStatus countyStatSysStatusData = countyIteratorData.next();
            json.append("{");
            json.append("idsystem:'"+countyStatSysStatusData.getIdsystem()+"',");
            String zonename=sysRegInfDao.findSystemName(cy.getId()+countyStatSysStatusData.getIdsystem().substring(6,8));
            json.append("zonename:'"+zonename+"',");
//            json.append("zonename:'"+cy.getDistrictName()+countyStatSysStatusData.getIdsystem().substring(6,8)+"',");
            json.append("bizsum:'"+countyStatSysStatusData.getBizsum()+"',");
            json.append("accesssum:'"+countyStatSysStatusData.getAccesssum()+"',");
            json.append("terminalnum:'"+countyStatSysStatusData.getTerminalnum()+"',");
            json.append("outflux:'"+countyStatSysStatusData.getOutflux()+"',");
            json.append("monthaccess:'"+countyStatSysStatusData.getMonthaccess()+"',");
            json.append("monthterminalnum:'"+countyStatSysStatusData.getMonthterminalnum()+"',");
            json.append("monthoutflux:'"+countyStatSysStatusData.getMonthoutflux()+"',");
            json.append("sysrunpresent:'"+countyStatSysStatusData.getSysrunpresent()+"',");
            json.append("uiProvider:'").append("col'").append(",");
            json.append("iconCls:'").append("task'").append(",");
            json.append("leaf:").append(true);
            json.append("}");
            if (countyIteratorData.hasNext()) {
                json.append(",");
            }
        }
        json.append("]}]");
        return json.toString();
    }
}
