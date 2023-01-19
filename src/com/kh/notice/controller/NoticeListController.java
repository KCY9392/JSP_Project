package com.kh.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;


@WebServlet("/list.no")
public class NoticeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NoticeListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    // 공지사항 전체 리스트 조회 한 후 조회결과를 담아서 응답페이지로 포워딩.
	    ArrayList<Notice> list = new NoticeService().selectNoticeList();
	    
	    System.out.println(list);
	    
	    request.setAttribute("list", list);
	    
	    request.getRequestDispatcher("views/notice/noticeListView.jsp").forward(request, response);
	    
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
