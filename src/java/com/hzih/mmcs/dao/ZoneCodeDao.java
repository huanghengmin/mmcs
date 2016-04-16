package com.hzih.mmcs.dao;

import com.hzih.mmcs.domain.ZoneCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-14
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public interface ZoneCodeDao {

    public ArrayList<ZoneCode> findZoneByZonecodeLike(String codelike);

    public ZoneCode findProvince(String code);

    public ZoneCode findCity(String code);

    public String  getZoneCodeName(String code);

    public List<ZoneCode> findProvinceCity(String provinceCode);

    public List<ZoneCode> findCityCounty(String code,String notLikeCode);
}
