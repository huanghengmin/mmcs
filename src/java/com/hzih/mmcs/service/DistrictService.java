package com.hzih.mmcs.service;

import com.hzih.mmcs.domain.District;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-14
 * Time: 上午10:38
 * To change this template use File | Settings | File Templates.
 */
public interface DistrictService {

    /**
     * 查找所有省,部节点,并组织成key,value的json={success:true,total:1,rows[{key:'',value:''}]}
     * @return
     * @throws Exception
     */
    public String queryKeyValueAllParents() throws Exception;

    public String queryKeyValueChildren(long parentId) throws Exception;
}
