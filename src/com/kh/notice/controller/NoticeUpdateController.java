package com.kh.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.vo.Member;
import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;


@WebServlet("/update.no")
public class NoticeUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public NoticeUpdateController() {
        super();
    }

    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    if(!(request.getSession().getAttribute("loginUser")!= null &&
	               ((Member)request.getSession().getAttribute("loginUser")).getUserId().equals("admin"))) {
	        request.setAttribute("errorMsg", "공지사항 수정권한이 없습니다");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	        return;
	    }
	    
	    request.setCharacterEncoding("UTF-8");
	
	    int noticeNo = Integer.parseInt(request.getParameter("nno"));
	    String noticeTitle = request.getParameter("title");
	    String noticeContent = request.getParameter("content");
	    
	    Notice n = new Notice();
	    
	    n.setNoticeNo(noticeNo);
	    n.setNoticeTitle(noticeTitle);
	    n.setNoticeContent(noticeContent);
	    
	    int result = new NoticeService().updateNotice(n);
	    
	    if(result > 0) {//성공했을 경우 => 상세보기 화면으로 이동
	        
	        request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 수정되었습니다");
	        response.sendRedirect(request.getContextPath()+"/detail.no?nno="+noticeNo);
	        // /jsp/detail.no?nno=1
	        
	    }else {//실패했을 경우 => 에러페이지로 이동
	        
	        request.setAttribute("errorMsg","공지사항 수정을 실패하였습니다");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	        
	    }
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
