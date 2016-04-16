package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.mmcs.domain.Sysmessage;
import com.hzih.mmcs.vo.MessageUserVo;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-10
 * Time: 下午2:29
 * To change this template use File | Settings | File Templates.
 */
public interface SysmessageDao extends BaseDao {
    public ArrayList<MessageUserVo> findMessageUserVos()throws Exception;
    public ArrayList<MessageUserVo> findMessageUserVos(final int start, final int limit)throws Exception;
    public ArrayList<MessageUserVo> findMessageUserVosByMsgAdminId(String msgAdminId)throws Exception;
    public ArrayList<MessageUserVo> findMessageUserVosByMsgAdminId(String msgAdminId, final int start, final int limit)throws Exception;
    public ArrayList<Sysmessage> findSysmessagesById(int id)throws Exception;
    public ArrayList<Sysmessage> findSysmessagesByIds(String ids)throws Exception;
    public void addSysmessage(Sysmessage sysmessage)throws Exception;
    public void delSysmessagesList(ArrayList<Sysmessage> list)throws Exception;
    public void updSysmessage(Sysmessage sysmessage)throws Exception;

    PageResult listSendByPage(Long id, int pageIndex, int limit)throws Exception;
    public PageResult listGetByPageBy(Long id, int pageIndex, int limit)throws Exception;

    public String findAccountByNotGet(long getid)throws Exception;

    public void deleteBySendOrReceiveId(String id) throws Exception;

}
