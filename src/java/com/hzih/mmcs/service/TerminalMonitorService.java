package com.hzih.mmcs.service;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-9
 * Time: 下午7:26
 * To change this template use File | Settings | File Templates.
 */
public interface TerminalMonitorService {
    
    public String selectAll(String zonecode)throws Exception;

    public String indexAll(String zonecode) throws Exception;
    
    public String detail(String idsystem, String pageSize, String start)throws Exception;

    public String citySelect(String zonecode)throws Exception;

    public String ZhixiacitySelect(String zonecode)throws Exception;
}
