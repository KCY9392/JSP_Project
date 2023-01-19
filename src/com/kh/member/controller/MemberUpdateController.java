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


@WebServlet("/update.me")
public class MemberUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public MemberUpdateController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	    // 1) 인코딩 설정
	    request.setCharacterEncoding("UTF-8");
	    
	    // 2) 요청시 전달받은 값들 뽑아서 변수 밑 객체에 담기
	    String userId = request.getParameter("userId");
	    String userName = request.getParameter("userName");
	    String phone = request.getParameter("phone");
	    String email = request.getParameter("email");
	    String address = request.getParameter("address");
	    String[] interestArr = request.getParameterValues("interest");
	    
	    String interest = interestArr == null ? "" : String.join(", ", interestArr);
	    
	    // Member객체 생성
	    Member m = new Member(userId, userName, phone, email, address, interest);
	    
	    // 3) 요청처리
	    Member updateMem = new MemberService().updateMember(m);
	    
	    // 4) 돌려받은 처리결과를 가지고 사용자가 보게될 응답화면 지정.
	    if(updateMem == null) { // update 실패
	        
	        request.setAttribute("errorMsg", "회원정보 수정을 실패하였습니다");
	        
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	        
	    }else { // update 성공
	        
	        HttpSession session = request.getSession();
	        
	        session.setAttribute("loginUser",updateMem); // 덮어씌워지기
	        session.setAttribute("alertMsg", "성공적으로 회원정보를 수정하였습니다");
	        
	        // 응답페이지 => /jsp/myPage.me
	        // url 재요청
	        response.sendRedirect(request.getContextPath()+"/myPage.me");
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}
