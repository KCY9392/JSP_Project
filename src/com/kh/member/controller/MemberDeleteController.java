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


@WebServlet("/delete.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public MemberDeleteController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    request.setCharacterEncoding("UTF-8");
	    
	    //로그인한 회원의 정보를 얻어내는 방법
	    // 방법 1. input type="hidden"으로 애초에 요청 시, 해당값을 숨겨서 전달받음.
	    // 방법 2. session영역에 담겨있는 회원객체로부터 뽑기.
	    
	    // 세션에 담겨있는 기존의 로그인된 사용자 정보를 얻어오기
	    HttpSession session = request.getSession();
	    
	    String userId = ((Member)session.getAttribute("loginUser")).getUserId();
	    
	    String userPwd = request.getParameter("userPwd");
	    
	    // 서비스에 기능요청
	    int result = new MemberService().deleteMember(userId, userPwd);
	    
	    /*
	     * UPDATE MEMBER
	     * SET STATUS = 'N'
	     * WHERE USER_ID = ? AND USER_PWD = ?
	     */
	    
	    if(result > 0) { //성공했을 경우 => 메인페이지에 alert와 로그아웃처리함
	        
	        session.setAttribute("alertMsg", "성공적으로 회원탈퇴되었습니다. 그동안 이용해주셔서 감사합니다");
	    
	        // invalidate() : 세션이 만료되어 alertMsg사용못하게됨
	        // removeAttribute(키값)을 이용해서 로그인한 사용자의 정보를 지워주는 방식으로 로그아웃처리함
	        
	        session.removeAttribute("loginUser");//로그아웃
	        
	        response.sendRedirect(request.getContextPath()); //기본 myPage로 이동
	        
	    }else { //실패 => 에러메시지
	        
	        request.setAttribute("error", "회원탈퇴에 실패하였습니다");
	        
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	        
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
