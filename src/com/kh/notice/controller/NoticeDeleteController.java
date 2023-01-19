package com.kh.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.vo.Member;
import com.kh.notice.model.service.NoticeService;


@WebServlet("/delete.no")
public class NoticeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public NoticeDeleteController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    
	    if(!(request.getSession().getAttribute("loginUser")!= null && ((Member)request.getSession().getAttribute("loginUser")).getUserId().equals("admin"))) {
	        request.setAttribute("errorMsg", "공지사항 수정권한이 없습니다");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	        return;
	    }
	    
	    request.setCharacterEncoding("UTF-8");
	    
	    int noticeNo = Integer.parseInt(request.getParameter("nno"));
	    
	    int result = new NoticeService().deleteNotice(noticeNo);
	    
	    if(result > 0) {//성공했다면 => /jsp/list.no
	        
	        request.getSession().setAttribute("alertMsg", "공지사항을 성공적으로 삭제하였습니다");
	        response.sendRedirect(request.getContextPath()+"/list.no");
	        
	    }else { //실패했다면
	        
	        request.setAttribute("errorMsg", "공지사항 삭제를 실패하였습니다");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	        
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
