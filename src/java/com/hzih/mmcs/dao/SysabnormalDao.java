package com.hzih.mmcs.dao;

import com.hzih.mmcs.vo.AbnormalVo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-11
 * Time: 上午10:52
 * To change this template use File | Settings | File Templates.
 */
public interface SysabnormalDao {

    public ArrayList<AbnormalVo> findAbnormaVos(String idsystem)throws Exception;
    public ArrayList<AbnormalVo> findAbnormaVos(String idsystem, final int start, final int limit)throws Exception;
    public int findAbnormalNumByMonth(String zonelike, String treadresult)throws Exception;
    public int findAbnormalNumByYear(String zonelike, String treadresult)throws Exception;
    public ArrayList<String> findIdsystemRS(String idsystem)throws Exception;
    public HashMap<String,Integer> findAbnormalAllNumByIdsystem(String idsystem)throws Exception;

    public ArrayList<AbnormalVo> findAbnormaVosByMonth(String idsystem,String treadresult) throws Exception;
    public ArrayList<AbnormalVo> findAbnormaVosByMonth(String idsystem,final int start, final int limit,String treadresult)throws Exception;
    public ArrayList<AbnormalVo> findAbnormaVosByYear(String idsystem,String treadresult)throws Exception;
    public ArrayList<AbnormalVo> findAbnormaVosByYear(String idsystem,final int start, final int limit,String treadresult)throws Exception;
}
