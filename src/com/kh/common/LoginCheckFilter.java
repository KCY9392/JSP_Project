package com.kh.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//1번째 방법 : 앞에 어떤 내용이든 .bo랑 .th로 되어있는 와스 다 감지
@WebFilter({"/board/*", "/thumb/*"} 
//2번째 방법 : 서블릿 이름으로 가져오기  
//servletNames = {"boardDeleteServlet", "servletname","servletname"}
        ) 
public class LoginCheckFilter implements Filter {

    
    public LoginCheckFilter() {

    }

    
	public void destroy() {
	
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
	    HttpSession session = ((HttpServletRequest)request).getSession();
	    if(session == null || session.getAttribute("loginUser") == null) {
	        //로그인 후 이용할 수 있다는 안내문구
	        request.setAttribute("errorMsg", "로그인권한이 없습니다");
            request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
	    }
	    
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
