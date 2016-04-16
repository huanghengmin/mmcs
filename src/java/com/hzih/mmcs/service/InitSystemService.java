package com.hzih.mmcs.service;

import com.hzih.mmcs.domain.InitSystem;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-14
 * Time: 上午10:14
 * To change this template use File | Settings | File Templates.
 */
public interface InitSystemService {

    /**
     * 查找监管中心初始化信息,有且只有一条记录,并组织成json
     * @return
     * @throws Exception
     */
    public String load() throws Exception;

    /**
     * 查找监管中心初始化信息,有且只有一条记录
     * @return
     * @throws Exception
     */
    public InitSystem query() throws Exception;

    /**
     * 新增初始化信息
     * @param initSystem
     * @return
     * @throws Exception
     */
    public String insert(InitSystem initSystem) throws Exception;

    /**
     * 修改初始化信息
     * @param initSystem
     * @return
     * @throws Exception
     */
    public String update(InitSystem initSystem) throws Exception;

}
