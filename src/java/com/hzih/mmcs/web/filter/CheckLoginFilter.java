package com.hzih.mmcs.web.filter;

import com.hzih.mmcs.domain.Account;
import com.hzih.mmcs.utils.KeyUtils;
import com.hzih.mmcs.web.SessionUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.security.cert.X509Certificate;

/**
 * 过滤证书内容
 * 
 * @author collin.code@gmail.com
 * 
 */
public class CheckLoginFilter implements Filter {
    private static final Logger logger = Logger.getLogger(CheckLoginFilter.class);
    private static final String ATTR_CER = "javax.servlet.request.X509Certificate";
//    private static final String ATTR_CER = "javax.net.ssl.peer_certificates";
    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String SCHEME_HTTPS = "https";
    private static StartLoginThread startLoginThread = new StartLoginThread();
    private static boolean isStartLoginThread = false;

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        startLoginThread();
		HttpServletRequest request = (HttpServletRequest) req;
        request.getCharacterEncoding();
		HttpServletResponse response = (HttpServletResponse) res;
        response.setCharacterEncoding(DEFAULT_ENCODING);
        response.setContentType(CONTENT_TYPE);
        String requestURL = request.getRequestURL().toString();
        /*if(requestURL.endsWith(request.getContextPath())){
            response.sendRedirect(request.getContextPath()+"/error.jsp");
        }*/
        PrintWriter out = response.getWriter();
        Account account = null;
        String ss = (String) request.getAttribute("javax.servlet.request.ssl_session");
        X509Certificate[] certs = null;
        if(ss!=null){
            certs = (X509Certificate[]) request.getAttribute(ATTR_CER);
        }
        if (certs != null) {
            for (int i = 0; i < certs.length; i++) {
                boolean isChecked = verifyCertificate(certs[i]);
                if(isChecked) {
                    Principal principal = certs[i].getSubjectDN();
                    String dn  = principal.getName();
                    try {
                        account = KeyUtils.getKeyAccount(dn);
                        if(SessionUtils.getAccount(request)==null){
                            SessionUtils.setAccount(request,account);
                            startLoginThread.query.offer(account.getUserName());
                        }
                        if(SessionUtils.getAccount(request) != null){

                            chain.doFilter(request,response);
                        }
                    } catch (Exception e) {
//                        out.println("证书解析错误,可能是无效证书");
                    }
                } else {
                    out.println("证书无效");
                }
            }
        } else {
            if (SCHEME_HTTPS.equalsIgnoreCase(request.getScheme())) {
                out.println("这是一个HTTPS请求,但是没有可用的客户端证书");
            } else {
                out.println("<br><br><br><br><br><br><br><h1 style=\"color:red\"><center>这不是一个HTTPS请求</center></h1>");
            }
        }
        out.close();
	}

    private void startLoginThread() {
        if(isStartLoginThread==true){
            return;
        }
        startLoginThread.init();
        startLoginThread.start();
        isStartLoginThread = true;
    }

    public void init(FilterConfig arg0) throws ServletException {
	}

    /**
     * <p>
     * 校验证书是否过期
     * </p>
     *
     * @param certificate
     * @return
     */
    private boolean verifyCertificate(X509Certificate certificate) {
        boolean valid = true;
        try {
            certificate.checkValidity();
        } catch (Exception e) {
            logger.error("校验证书是否过期错误",e);
            valid = false;
        }
        return valid;
    }
}
