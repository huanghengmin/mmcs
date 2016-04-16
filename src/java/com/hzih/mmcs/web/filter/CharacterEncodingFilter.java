package com.hzih.mmcs.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {

    protected FilterConfig filterConfig;
    protected String encodingName;
    protected boolean enable;

    public CharacterEncodingFilter() {
        this.encodingName = "UTF-8";
        this.enable = false;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        loadConfigParams();
    }

    private void loadConfigParams() {

        this.encodingName = this.filterConfig.getInitParameter("encoding");

        String strIgnoreFlag = this.filterConfig.getInitParameter("enable");
        if (strIgnoreFlag.equalsIgnoreCase("true")) {
            this.enable = true;
        } else {
            this.enable = false;
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (this.enable) {
            request.setCharacterEncoding(this.encodingName);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

}

