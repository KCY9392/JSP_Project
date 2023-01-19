package com.kh.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


@WebFilter("/*") // /* : 모든 url요청에 대해서 Filter가 응답해준다.
// Filter가 먼저 호출되고, 우리가 요청햇던 url이 호출되는 순서이다.
public class EncodingFilter implements Filter {

    
    public EncodingFilter() {
    }

    
	public void destroy() {
	}

	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 모든 서블릿을 거치기 전에 먼저 filter에서 인코딩을 설정해줄 수 있다 (서블릿에서 인코딩설정구문 줄임)
	    request.setCharacterEncoding("UTF-8");
	    System.out.println("인코딩 실행");
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {

	
	}

}
