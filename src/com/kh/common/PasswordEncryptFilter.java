package com.kh.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;


@WebFilter(servletNames = {"loginServlet","memberInsertServlet" 
        //1. 로그인시
        //2. 회원정보변경시
        //3. 회원가입시
}) 
public class PasswordEncryptFilter implements Filter {

    public PasswordEncryptFilter() {
        
    }

	
	public void destroy() {
	    
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
	    PasswordEncryptWrapper pew = new PasswordEncryptWrapper((HttpServletRequest)request);
		chain.doFilter(pew, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
