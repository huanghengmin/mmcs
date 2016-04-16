package com.hzih.mmcs.service;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 12-6-19
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
public interface AuditService {

    public String selectBusinessAudit(int pageIndex, int limit, Date startDate, Date endDate,
                                      String logLevel, String businessName) throws Exception;

    public String selectEquipmentAudit(int pageIndex, int limit, Date startDate, Date endDate,
                                       String logLevel, String equipmentName) throws Exception;

    public String selectUserAudit(int pageIndex, int limit, Date startDate, Date endDate,
                                  String logLevel, String userName) throws Exception;

    public void deleteEquipment(String startDate, String endDate,
                                String businessType, String equipmentName) throws Exception;

    public void deleteBusiness(String startDate, String endDate,
                               String logLevel, String businessName) throws Exception;

    public String selectOSAudit(int pageIndex, int limit, Date startDate, Date endDate, String logLevel) throws Exception;
}
