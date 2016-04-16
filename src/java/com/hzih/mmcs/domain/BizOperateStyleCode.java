package com.hzih.mmcs.domain;

/**
 *接入应用操作方式代码表实体
 * User: Administrator
 * Date: 13-1-17
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public class BizOperateStyleCode {
    private String  bizOperatestylecode;
    private String operationname;

    public String getBizOperatestylecode() {
        return bizOperatestylecode;
    }

    public void setBizOperatestylecode(String bizOperatestylecode) {
        this.bizOperatestylecode = bizOperatestylecode;
    }

    public String getOperationname() {
        return operationname;
    }

    public void setOperationname(String operationname) {
        this.operationname = operationname;
    }
}
