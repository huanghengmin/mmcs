package com.hzih.mmcs.dao;

import cn.collin.commons.dao.BaseDao;
import com.hzih.mmcs.domain.SysAssessment;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-11
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public interface SysAssessmentDao  extends BaseDao {
    public List<SysAssessment> findALL()throws Exception;

    public List<SysAssessment> findLikeCode(String likeCode)throws Exception;

    public List<SysAssessment> findLikeCode(String likeCode,String notLikeCode)throws Exception;

}
