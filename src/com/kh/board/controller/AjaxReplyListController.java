package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Reply;


@WebServlet("/rlist.bo")
public class AjaxReplyListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public AjaxReplyListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    int boardNo = Integer.parseInt(request.getParameter("bno"));
	    
	    ArrayList<Reply> list = new BoardService().selectReplyList(boardNo);
	
	    // Gson을 이용해서 응답 => ArrayList -> JSONObject 배열형태로 변환
	    response.setContentType("application/json; charset=UTF-8");
	    new Gson().toJson(list , response.getWriter());
	    
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
