package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;


@WebServlet("/idCheck.me")
public class AjaxIdCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxIdCheckController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    String checkId = request.getParameter("checkId");
	    
	    int count = new MemberService().idCheck(checkId);
	
	    if(count > 0) {// 중복된 아이디가 존재한다 -> 사용불가
	        response.getWriter().print("NNNN");
	    }else {// 존재하는 아이디가 없을 경우 -> 사용가능
	        response.getWriter().print("NNNNY");
	    }
	
	
	
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
