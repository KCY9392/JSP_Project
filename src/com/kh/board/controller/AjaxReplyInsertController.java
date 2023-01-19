package com.kh.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Reply;
import com.kh.member.model.vo.Member;


@WebServlet("/rinsert.bo")
public class AjaxReplyInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AjaxReplyInsertController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    String replyContent = request.getParameter("content");
	    int boardNo = Integer.parseInt(request.getParameter("bno"));
	    int userNo = ((Member)request.getSession().getAttribute("loginUser")).getUserNo();
	
	    Reply r = new Reply();
	    r.setReplyContent(replyContent);
	    r.setRefBoardNo(boardNo);
	    r.setBoardWriter(userNo+""); // ""더해서 String으로 변환
	
	    int result = new BoardService().insertReply(r);
	    
	    response.getWriter().print(result);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
