package com.hzih.mmcs.service.impl;

import com.hzih.mmcs.dao.DistrictDao;
import com.hzih.mmcs.domain.District;
import com.hzih.mmcs.service.DistrictService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-14
 * Time: 上午10:43
 * To change this template use File | Settings | File Templates.
 */
public class DistrictServiceImpl implements DistrictService {
    private DistrictDao districtDao;

    public void setDistrictDao(DistrictDao districtDao) {
        this.districtDao = districtDao;
    }

    @Override
    public String queryKeyValueAllParents() throws Exception {
        List<District> list = districtDao.getAllParents();
        String json = "{success:true,total:"+list.size()+",rows:[";
        for(District d : list){
            json += "{key:'"+d.getDistrictName()+"',value:'"+d.getId()+"'},";
        }
        json += "]}";
        return json;
    }

    @Override
    public String queryKeyValueChildren(long parentId) throws Exception {
        List<District> list = districtDao.findChildByParent(parentId);
        String json = "{success:true,total:"+list.size()+",rows:[";
        for(District d : list){
            json += "{key:'"+d.getDistrictName()+"',value:'"+d.getId()+"'},";
        }
        json += "]}";
        return json;
    }
}
