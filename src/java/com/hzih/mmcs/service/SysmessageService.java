package com.hzih.mmcs.service;

import com.hzih.mmcs.domain.Account;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-10
 * Time: 下午3:30
 * To change this template use File | Settings | File Templates.
 */
public interface SysmessageService {

    public String findMessageUserVos(int start, int limit) throws Exception;
    public String findMessageUserVosByMsgAdminId(String msgAdminId, final int start, final int limit) throws Exception;
    public void addSysmessage(String msgName, String msgType, long idalertmatter, long msgAdminId, String idsystem, String ifchecked, Date sendTime, Date checkedTime);
    public void delSysmessagesByIds(String ids) throws Exception;
    public String findUsers();
    public String findAccountByNotGet(long getid);
    public void updSysmessageByIdForIfchecked(int id,String ifchecked ) throws Exception;

    public String findMessageUserSendById(Account a, int start, int limit) throws Exception;
    public String findMessageUserGetById(Account a, int start, int limit) throws Exception;
}
