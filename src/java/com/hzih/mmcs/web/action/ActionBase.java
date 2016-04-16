package com.hzih.mmcs.web.action;

import cn.collin.commons.utils.DateUtils;
import com.hzih.mmcs.utils.CheckTimeResult;
import com.hzih.mmcs.utils.StringUtils;
import com.hzih.mmcs.web.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ActionBase {

	private static final long serialVersionUID = -3517772370262338399L;

	public String actionBegin(HttpServletRequest request){
		request.getCharacterEncoding();
        String result = "";
//		String result = new CheckTimeResult().getResult(request);
//        SessionUtils.setLoginTime(request, DateUtils.getNow().getTime());
		return result;
	}
	
	public void actionEnd(HttpServletResponse response,String json,String result) throws IOException {
		response.setCharacterEncoding("utf-8");
		StringUtils st = new StringUtils();

		response.setContentType("text/html");//上传文件回调函数处理时需要
		response.getWriter().print(result);
		
		response.getWriter().write(st.trim(json));
		response.getWriter().close();
	}

    /**
     * 不需要改变登录时间
     * @param response
     * @param json
     * @throws java.io.IOException
     */
    public void actionEnd(HttpServletResponse response, String json) throws IOException{
        json = StringUtils.trim(json);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");//上传文件回调函数处理时需要
        response.getWriter().write(json);
        response.getWriter().close();
    }
}
