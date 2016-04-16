package com.hzih.mmcs.service.impl;

import com.hzih.mmcs.dao.DistrictDao;
import com.hzih.mmcs.dao.InitSystemDao;
import com.hzih.mmcs.domain.District;
import com.hzih.mmcs.domain.InitSystem;
import com.hzih.mmcs.service.InitSystemService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-14
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public class InitSystemServiceImpl implements InitSystemService {
    private InitSystemDao initSystemDao;
    private DistrictDao districtDao;

    public void setInitSystemDao(InitSystemDao initSystemDao) {
        this.initSystemDao = initSystemDao;
    }

    public void setDistrictDao(DistrictDao districtDao) {
        this.districtDao = districtDao;
    }

    @Override
    public String load() throws Exception {
        List<InitSystem> list = initSystemDao.findAll();
        String json = "{success:true,total:0,rows:[]}";
        if(list.size()>0){
            InitSystem initSystem = list.get(0);
            District district = districtDao.findById(Long.valueOf(initSystem.getRootCode()));
            json = "{success:true,total:1,rows:[{districtId:'"+initSystem.getRootCode()+"',districtName:'"+district.getDistrictName()+"'}]}";
        }
        return json;
    }

    @Override
    public InitSystem query() throws Exception {
        List<InitSystem> list = initSystemDao.findAll();
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public String insert(InitSystem initSystem) throws Exception {
        initSystemDao.create(initSystem);
        return "初始化成功,点击[确定]返回页面";
    }

    @Override
    public String update(InitSystem initSystem) throws Exception {
        initSystemDao.update(initSystem);
        return "修改成功,点击[确定]返回页面";
    }


}
