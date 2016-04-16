package com.hzih.mmcs.web.filter;

import com.hzih.mmcs.utils.ServiceResponse;
import com.hzih.mmcs.utils.ServiceUtil;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-16
 * Time: 下午5:44
 * To change this template use File | Settings | File Templates.
 */
public class StartLoginThread extends Thread{
    private static final Logger logger = Logger.getLogger(StartLoginThread.class);
    private boolean isRun = false;
    public BlockingQueue<String> query;
    public void init(){
        query = new LinkedBlockingQueue<String>(1000);
    }

    private String pollQuery() {
        try {
            return query.take();
        } catch (InterruptedException e) {
            logger.error("队列取值错误",e);
        }
        return null;
    }

    public void run() {
        isRun = true;
        while (isRun) {
            String userName = pollQuery();
            boolean isSendOk = false;
            while (!isSendOk) {
                String[][] params = new String[][] {
                        { "SERVICEREQUESTTYPE", "SERVICECONTROLPOST" },
                        { "userName", userName }
                };
                ServiceResponse response = ServiceUtil.callService(params);
                if (response != null && response.getCode()==200){
                    isSendOk = true;
                } else {
                    if(response!=null){
                        logger.error("发送"+userName+"登录消息失败:"+response.getCode());
                    } else {
                        logger.error("发送"+userName+"登录消息失败:response没有返回值");
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    public void close() {
        isRun = false;
    }

    public boolean isRunning() {
        return isRun;
    }
}
