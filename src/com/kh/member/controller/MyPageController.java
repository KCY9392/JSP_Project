package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/myPage.me")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public MyPageController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // url로 직접 요청도 가능하기때문에
	    // 로그인 전 요청 시 => 메인페이지로
	    // 로그인 후 요청 시 => 마이페이지로 포워딩.
	    
	    HttpSession session = request.getSession();
	    
	    if(session.getAttribute("loginUser") == null) { // 로그인 안 한 상태라면
	        
	        session.setAttribute("alertMsg", "로그인 후 이용가능한 서비스 입니다");
	        response.sendRedirect(request.getContextPath());
	        
	    }else { // 로그인 한 상태라면
	        
	        request.getRequestDispatcher("views/member/myPage.jsp").forward(request, response);
	        
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
