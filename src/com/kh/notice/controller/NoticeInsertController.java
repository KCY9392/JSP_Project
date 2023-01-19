package com.kh.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;


@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public NoticeInsertController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");
	    
	    String userNo = request.getParameter("userNo");
	    String noticeTitle = request.getParameter("title");
	    String noticeContent = request.getParameter("content");
	    
	    Notice n = new Notice();
	    
	    n.setNoticeTitle(noticeTitle);
	    n.setNoticeContent(noticeContent);
	    n.setNoticeWriter(userNo);
	    
	    int result = new NoticeService().insertNotice(n);
	    
	    if(result > 0) {//성공했다면 => list.no 리스트페이지로 Redirect
	        
	        request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 등록되었습니다");
	        
	        response.sendRedirect(request.getContextPath()+"/list.no");
	        // jsp/list.no로 보냄
	        
	    }else {//실패했다면 => 에러페이지
	        
	        request.setAttribute("errorMsg", "공지사항 등록에 실패하였습니다");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	    }
	    
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
