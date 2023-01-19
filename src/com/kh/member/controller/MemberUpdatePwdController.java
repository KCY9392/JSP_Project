package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

@WebServlet("/updatePwd.me")
public class MemberUpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberUpdatePwdController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    request.setCharacterEncoding("UTF-8");
	    
	    String userId = request.getParameter("userId");
	    String userPwd = request.getParameter("userPwd");
	    String updatePwd = request.getParameter("updatePwd");
	    
	    Member updateMem = new MemberService().updatePwdMember(userId, userPwd, updatePwd);
	    
	    HttpSession session = request.getSession();
	    
	    if(updateMem == null) { // 실행 실패시 로직
	        session.setAttribute("alertMsg", "비밀번호 변경에 실패하였습니다");
	    }else {// 실행 성공시 로직
	        session.setAttribute("alertMsg", "성공적으로 비밀번호가 변경되었습니다");
	        session.setAttribute("loginUser", updateMem);
	    }
	    
	    response.sendRedirect(request.getContextPath()+"/myPage.me"); // /jsp
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
