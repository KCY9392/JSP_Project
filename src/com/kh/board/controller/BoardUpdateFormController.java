package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;


@WebServlet("/updateForm.bo")
public class BoardUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public BoardUpdateFormController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    BoardService bService = new BoardService();
	    
	    int boardNo = Integer.parseInt(request.getParameter("bno"));
	    
	    ArrayList<Category> list = bService.selectCategoryList();
	    
	    Board b = bService.selectBoard(boardNo);
	    
	    Attachment at = bService.selectAttachment(boardNo);
	    
	    request.setAttribute("list", list);
	    request.setAttribute("b", b);
	    request.setAttribute("at", at);
	    
	    request.getRequestDispatcher("views/board/boardUpdateForm.jsp").forward(request, response);
	    
	    
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
