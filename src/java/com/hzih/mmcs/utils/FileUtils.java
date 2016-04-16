package com.hzih.mmcs.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-21
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {
    public static void downType(HttpServletResponse response,String name,String userBrowser) {
		response.reset();
		response.setBufferSize(5*1024*1024);
		response.addHeader("Content-Disposition", "attachment;filename=\"" + name + "\"");
		if(userBrowser.indexOf("Firefox")>0){
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
		}
		response.setContentType("application/octet-stream; charset=UTF-8");
	}

    /**
     *
     * @param from       被复制文件
     * @param response   传输响应 用于文件下载时
     */
    public static HttpServletResponse copy(File from,HttpServletResponse response) throws IOException { //下载

        ServletOutputStream out =null;
        BufferedInputStream in = null;
        out = response.getOutputStream();
        in = new BufferedInputStream(new FileInputStream(from));
        byte[] content = new byte[1024*1024];
        int length;
        while ((length = in.read(content, 0, content.length)) != -1){
            out.write(content, 0, length);
            out.flush();
        }
        in.close();
        out.flush();
//            out.close();

        return response;
    }
}
