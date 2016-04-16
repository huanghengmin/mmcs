package com.hzih.mmcs.service.impl;

import cn.collin.commons.domain.PageResult;
import cn.collin.commons.utils.DateUtils;
import com.hzih.mmcs.dao.AccountDao;
import com.hzih.mmcs.dao.OrgcodeDao;
import com.hzih.mmcs.dao.SysmessageDao;
import com.hzih.mmcs.domain.Account;
import com.hzih.mmcs.domain.Role;
import com.hzih.mmcs.domain.Sysmessage;
import com.hzih.mmcs.service.SysmessageService;
import com.hzih.mmcs.vo.MessageUserVo;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-1-10
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
public class SysmessageServiceImpl implements SysmessageService {
    private SysmessageDao sysmessageDao;
    private AccountDao accountDao;
    private OrgcodeDao orgcodeDao;

    public void setOrgcodeDao(OrgcodeDao orgcodeDao) {
        this.orgcodeDao = orgcodeDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public SysmessageDao getSysmessageDao() {
        return sysmessageDao;
    }

    public void setSysmessageDao(SysmessageDao sysmessageDao) {
        this.sysmessageDao = sysmessageDao;
    }

    @Override
    public String findMessageUserVos(int start, int limit) throws Exception {
        ArrayList<MessageUserVo> alllist = sysmessageDao.findMessageUserVos();
        ArrayList<MessageUserVo> list = sysmessageDao.findMessageUserVos(start,limit);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'mulist':").append(alllist.size()).append(",'murow':[");
        for(MessageUserVo s : list){
            jsonb.append("{id:").append(s.getId()).append(",msgName:'").append(s.getMsgName());

            jsonb.append("',msgType:'");
            String msgtype = s.getMsgType();
            if("0".equals(msgtype)){
                jsonb.append("审批通知类");
            }else if("1".equals(msgtype)) {
                jsonb.append("违规通知类");
            }else {
                jsonb.append("其他");
            }
            jsonb.append("',username:'").append(s.getUsername());
            jsonb.append("',unit:'").append(s.getUnit()).append("',roleids:'");
            
//            msgtype=s.getRoleids();
//            if("1".equals(msgtype)) {
//                jsonb.append("省厅管理员");
//            }else if("2".equals(msgtype)) {
//                jsonb.append("地市管理员");
//            }else if("3".equals(msgtype)){
//                jsonb.append("区县管理员");
//            }else {
//                jsonb.append("其他管理员");
//            }
            jsonb.append(s.getRoleids());

            jsonb.append("',ifchecked:'");
            msgtype=s.getIfchecked();
            if("0".equals(msgtype)){
                jsonb.append("否");
            }else if("1".equals(msgtype)) {
                jsonb.append("是");
            }else {
                jsonb.append("wu");
            }

            jsonb.append("',sendTime:'").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getSendTime()));
            Date checkedTime = s.getCheckedTime();
            String chetimes = "无";
            if(null!=checkedTime){
                chetimes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(checkedTime);
            }
            jsonb.append("',checkedTime:'").append(chetimes).append("'},");

        }
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findMessageUserVosByMsgAdminId(String msgAdminId,final int start, final int limit) throws Exception {




//        long st = System.currentTimeMillis();
        ArrayList<MessageUserVo> alllist = sysmessageDao.findMessageUserVosByMsgAdminId(msgAdminId);
//        long ed = System.currentTimeMillis();
//        System.out.println(ed-st);
        ArrayList<MessageUserVo> list = sysmessageDao.findMessageUserVosByMsgAdminId(msgAdminId,start,limit);
//        long ed1 = System.currentTimeMillis();
//        System.out.println(ed1-ed);
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'mulist':").append(alllist.size()).append(",'murow':[");
        for(MessageUserVo s : list){
            jsonb.append("{id:").append(s.getId()).append(",msgName:'").append(s.getMsgName());

            jsonb.append("',msgType:'");
            String msgtype = s.getMsgType();
            if("0".equals(msgtype)){
                jsonb.append("审批通知类");
            }else if("1".equals(msgtype)) {
                jsonb.append("违规通知类");
            }else {
                jsonb.append("其他");
            }
            jsonb.append("',username:'").append(s.getUsername());
            jsonb.append("',unit:'").append(s.getUnit()).append("',roleids:'");

//            msgtype=s.getRoleids();
//            if("1".equals(msgtype)) {
//                jsonb.append("省厅管理员");
//            }else if("2".equals(msgtype)) {
//                jsonb.append("地市管理员");
//            }else if("3".equals(msgtype)){
//                jsonb.append("区县管理员");
//            }else {
//                jsonb.append("其他管理员");
//            }
            jsonb.append(s.getRoleids());

            jsonb.append("',ifchecked:'");
            msgtype=s.getIfchecked();
            if("0".equals(msgtype)){
                jsonb.append("否");
            }else if("1".equals(msgtype)) {
                jsonb.append("是");
            }else {
                jsonb.append("wu");
            }

            jsonb.append("',sendTime:'").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getSendTime()));
            Date checkedTime = s.getCheckedTime();
            String chetimes = "无";
            if(null!=checkedTime){
                chetimes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(checkedTime);
            }
            jsonb.append("',checkedTime:'").append(chetimes).append("'},");

        }
        long ed2 = System.currentTimeMillis();
//        System.out.println(ed2-ed1);
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public void addSysmessage(String msgName, String msgType, long idalertmatter, long msgAdminId, String idsystem, String ifchecked, Date sendTime, Date checkedTime) {
        sysmessageDao.create(new Sysmessage(msgName, msgType, idalertmatter, msgAdminId, idsystem, ifchecked, sendTime, checkedTime));
    }

    @Override
    public void delSysmessagesByIds(String ids) throws Exception {
        ArrayList<Sysmessage> list = sysmessageDao.findSysmessagesByIds(ids);
        sysmessageDao.delSysmessagesList(list);
    }

    @Override
    public String findUsers() {
        List<Account> list = accountDao.findAll();
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{'userlist':").append(list.size()).append(",'rows':[");
        for(Account s : list){
            jsonb.append("{id:'").append(s.getId());
            jsonb.append("',username:'").append(s.getUserName()).append("'},");
        }
        jsonb.deleteCharAt(jsonb.length()-1);
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findAccountByNotGet(long getid) {
        List<Account> list = accountDao.findAll();
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{'userlist':").append(list.size()).append(",'rows':[");
        for(Account s : list){
            jsonb.append("{id:'").append(s.getId());
            jsonb.append("',username:'").append(s.getUserName()).append("'},");
        }
        jsonb.deleteCharAt(jsonb.length()-1);
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }


    @Override
    public String findMessageUserSendById(Account a, int start, int limit) throws Exception {
        PageResult ps = sysmessageDao.listSendByPage(a.getId(),(start/limit+1),limit);
//        PageResult ps = sysmessageDao.listGetByPageBy(a.getId(),(start/limit+1),limit);

        int len = ps.getAllResultsAmount();
        List<Sysmessage> list = ps.getResults();
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'mulist':").append(len).append(",'murow':[");
        String json = "";
        for(Sysmessage s : list){
            Account account = (Account) accountDao.getById(s.getMsgAdminId());
            jsonb.append("{id:").append(s.getId()).append(",msgName:'").append(s.getMsgName());
            jsonb.append("',msgType:'");
            String msgtype = s.getMsgType();
            if("0".equals(msgtype)){
                jsonb.append("审批通知类");
            }else if("1".equals(msgtype)) {
                jsonb.append("违规通知类");
            }else {
                jsonb.append("其他");
            }
            jsonb.append("',username:'").append(account.getUserName());
            jsonb.append("',unit:'").append(orgcodeDao.findOrgcode(account.getOrgCode()).getOrgname()).append("',roleids:'");
            Set<Role> roles = a.getRoles();
            Iterator<Role> iterator = roles.iterator();
            String roleName = null;
            while (iterator.hasNext()){
                Role role = iterator.next();
                roleName = role.getName();
            }
            jsonb.append(roleName);

            jsonb.append("',ifchecked:'");
            msgtype=s.getIfchecked();
            if("0".equals(msgtype)){
                jsonb.append("否");
            }else if("1".equals(msgtype)) {
                jsonb.append("是");
            }else {
                jsonb.append("wu");
            }

            jsonb.append("',sendTime:'").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getSendTime()));
            Date checkedTime = s.getCheckedTime();
            String chetimes = "无";
            if(null!=checkedTime){
                chetimes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(checkedTime);
            }
            jsonb.append("',checkedTime:'").append(chetimes).append("'},");
        }
        /*for(MessageUserVo s : list){
            jsonb.append("{id:").append(s.getId()).append(",msgName:'").append(s.getMsgName());

            jsonb.append("',msgType:'");
            String msgtype = s.getMsgType();
            if("0".equals(msgtype)){
                jsonb.append("审批通知类");
            }else if("1".equals(msgtype)) {
                jsonb.append("违规通知类");
            }else {
                jsonb.append("其他");
            }
            jsonb.append("',username:'").append(s.getUsername());
            jsonb.append("',unit:'").append(s.getUnit()).append("',roleids:'");

//            msgtype=s.getRoleids();
//            if("1".equals(msgtype)) {
//                jsonb.append("省厅管理员");
//            }else if("2".equals(msgtype)) {
//                jsonb.append("地市管理员");
//            }else if("3".equals(msgtype)){
//                jsonb.append("区县管理员");
//            }else {
//                jsonb.append("其他管理员");
//            }
            jsonb.append(s.getRoleids());

            jsonb.append("',ifchecked:'");
            msgtype=s.getIfchecked();
            if("0".equals(msgtype)){
                jsonb.append("否");
            }else if("1".equals(msgtype)) {
                jsonb.append("是");
            }else {
                jsonb.append("wu");
            }

            jsonb.append("',sendTime:'").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getSendTime()));
            Date checkedTime = s.getCheckedTime();
            String chetimes = "无";
            if(null!=checkedTime){
                chetimes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(checkedTime);
            }
            jsonb.append("',checkedTime:'").append(chetimes).append("'},");

        }*/
        long ed2 = System.currentTimeMillis();
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public String findMessageUserGetById(Account a, int start, int limit) throws Exception {
//        PageResult ps = sysmessageDao.listSendByPage(a.getId(),(start/limit+1),limit);
        PageResult ps = sysmessageDao.listGetByPageBy(a.getId(),(start/limit+1),limit);

        int len = ps.getAllResultsAmount();
        List<Sysmessage> list = ps.getResults();
        StringBuffer jsonb = new StringBuffer();
        jsonb.append("{success:true,'mulist':").append(len).append(",'murow':[");
        String json = "";
        for(Sysmessage s : list){
            Account account = (Account) accountDao.getById(s.getMsgAdminId());
            jsonb.append("{id:").append(s.getId()).append(",msgName:'").append(s.getMsgName());
            jsonb.append("',msgType:'");
            String msgtype = s.getMsgType();
            if("0".equals(msgtype)){
                jsonb.append("审批通知类");
            }else if("1".equals(msgtype)) {
                jsonb.append("违规通知类");
            }else {
                jsonb.append("其他");
            }
            jsonb.append("',username:'").append(account.getUserName());
            jsonb.append("',unit:'").append(orgcodeDao.findOrgcode(account.getOrgCode()).getOrgname()).append("',roleids:'");
            Set<Role> roles = a.getRoles();
            Iterator<Role> iterator = roles.iterator();
            String roleName = null;
            while (iterator.hasNext()){
                Role role = iterator.next();
                roleName = role.getName();
            }
            jsonb.append(roleName);

            jsonb.append("',ifchecked:'");
            msgtype=s.getIfchecked();
            if("0".equals(msgtype)){
                jsonb.append("否");
            }else if("1".equals(msgtype)) {
                jsonb.append("是");
            }else {
                jsonb.append("wu");
            }

            jsonb.append("',sendTime:'").append(DateUtils.format(s.getSendTime(),"yyyy-MM-dd HH:mm:ss"));
            Date checkedTime = s.getCheckedTime();
            String chetimes = "无";
            if(null!=checkedTime){
                chetimes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(checkedTime);
            }
            jsonb.append("',checkedTime:'").append(chetimes).append("'},");
        }
        /*for(MessageUserVo s : list){
            jsonb.append("{id:").append(s.getId()).append(",msgName:'").append(s.getMsgName());

            jsonb.append("',msgType:'");
            String msgtype = s.getMsgType();
            if("0".equals(msgtype)){
                jsonb.append("审批通知类");
            }else if("1".equals(msgtype)) {
                jsonb.append("违规通知类");
            }else {
                jsonb.append("其他");
            }
            jsonb.append("',username:'").append(s.getUsername());
            jsonb.append("',unit:'").append(s.getUnit()).append("',roleids:'");

//            msgtype=s.getRoleids();
//            if("1".equals(msgtype)) {
//                jsonb.append("省厅管理员");
//            }else if("2".equals(msgtype)) {
//                jsonb.append("地市管理员");
//            }else if("3".equals(msgtype)){
//                jsonb.append("区县管理员");
//            }else {
//                jsonb.append("其他管理员");
//            }
            jsonb.append(s.getRoleids());

            jsonb.append("',ifchecked:'");
            msgtype=s.getIfchecked();
            if("0".equals(msgtype)){
                jsonb.append("否");
            }else if("1".equals(msgtype)) {
                jsonb.append("是");
            }else {
                jsonb.append("wu");
            }

            jsonb.append("',sendTime:'").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(s.getSendTime()));
            Date checkedTime = s.getCheckedTime();
            String chetimes = "无";
            if(null!=checkedTime){
                chetimes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(checkedTime);
            }
            jsonb.append("',checkedTime:'").append(chetimes).append("'},");

        }*/
        long ed2 = System.currentTimeMillis();
        if(list.size()!=0){
            jsonb.deleteCharAt(jsonb.length()-1);
        }
        jsonb.append("]}");
        String jsons = jsonb.toString();
        return jsons;
    }

    @Override
    public void updSysmessageByIdForIfchecked(int id, String ifchecked) throws Exception {
        Sysmessage sysmessage = sysmessageDao.findSysmessagesById(id).get(0);
        sysmessage.setIfchecked(ifchecked);
        sysmessage.setCheckedTime(new Date());
        sysmessageDao.updSysmessage(sysmessage);
    }
}
