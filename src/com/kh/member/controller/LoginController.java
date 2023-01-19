package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;


@WebServlet(name="loginServlet",urlPatterns="/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public LoginController() {
        super();
    }
    
    private MemberService ms = new MemberService();

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 *	<HttpServletRequest 객체와 HttpServletResponse 객체>
		 *
		 *	- request	: 서버로 요청할때의 정보들이 담겨있음 ( 요청시 전달값, 요청전송 방식등 )
		 *	- response	: 요청에 대해 응답할때 필요한 객체
		 *
		 *	<Get방식과 Post방식 차이>
		 *	- get	: 사용자가 입력한 값들이 url 노출 o / 데이터길이제한 o / 대신 즐겨찾기할때는 편리
		 *	- post	: 					  	 x /		   x / 대신 timeout이 존재
		 * 
		 */
		
		// 1) 전달값에 한글이 있을 경우, 인코딩 처리해줘야함.
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청전 전달값 꺼내서 변수 또는 객체에 기록하기 (가공처리)
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		
		// 3) 해당 요청을 처리하는 service의 메소드 호출
		Member loginUser = ms.loginMember(userId, userPwd);
		
		
		// 4) 처리된 결과를 가지고 사용자가 보게될 뷰를 지정
		
		/*
		 *	응답페이지에 전달된 값이 있을 경우, 값을 어딘가에 담아야한다 (담아줄수 있는 Servlet scope 내장객체 4종류)
		 *
		 *	1) application : application에 담은 데이터는 웹 애플리케이션 전역에서 다 꺼내쓸 수 있음
		 *	2) session : session에 담은 데이터는 모든 jsp와 servlet에서 꺼내쓸 수 있음
		 *				 한번 담은 데이터는 내가 직접 지우기 전 / 서버가 멈추기 전 / 브라우저 종료 전 까지 접근가능
		 *	3) request : request에 담은 데이터는 해당 request를 포워딩한 응답 jsp에서만 꺼내 쓸 수 있음
		 *	4) page : 해당 jsp페이지에서만 꺼내쓸 수 있음
		 *	
		 *	자주 쓰이는 건 request와 session 
		 *
		 */
		
		if(loginUser == null) { // login 실패 => 에러페이지 응답
			
			request.setAttribute("errorMsg", "로그인에 실패하였습니다");
			
			// 응답페이지 jsp에 위임시, 필요한 객체 (RequestDispatcher 이용)
			// 포워딩 방식 : 해당 경로로 선택된 뷰가 보여질뿐이고, 요청할때 url은 변하지 않는다.
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			
			view.forward(request, response);
			
		}else { // login 성공 => index페이지 응답
			// login한 회원정보를 로그아웃하기전까지 계속 가져다쓰기때문에 session에 담기
			
			// Servlet에서 JSP내장객체 중 Session에 접근하고자 한다면, 우선 session객체를 얻어와야한다.
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			
			session.setAttribute("alertMsg", "로그인에 성공하였습니다");
			
			/*
			 * 1. 포워딩방식 응답 뷰 출력하기
			 * 
			 *  <원래 방식>
			 * 	RequestDispatcher view = request.getRequestDispatcher("실제 사용하고자 하는 index.jsp 경로");
			 * 	view.forword(request, response);
			 * 	
			 *  문제점 -> 해당 선택된 jsp가 보여질뿐이고, url에는 여전히 현재 이 서블릿 매핑 값이 남아있다.
			 *  -> localhost:8001/jsp/login.me가 그대로 남아있다는 의미
			 *  
			 * 2. url 재요청 방식 (sendRedirect 방식)
			 *  login.me 를 남지 않게하려면,
			 * 	이 방식으로 하면 localhost:8001/jsp 까지만 남게될것이다.
			 */
			 response.sendRedirect(request.getContextPath()); // 프로젝트의 기본경로 /jsp
		}
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
