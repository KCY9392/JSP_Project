package com.kh.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;


@WebServlet("/delete.bo")
public class BoardDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public BoardDeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    int boardNo = Integer.parseInt(request.getParameter("bno"));
	    
	    int result = new BoardService().deleteBoard(boardNo);
	    
	    if(result>0) {
	        request.getSession().setAttribute("alertMsg", "게시물을 성공적으로 삭제하였습니다");
	        response.sendRedirect(request.getContextPath()+"/list.bo");
	        
	    }else { // 실패
	        request.setAttribute("errorMsg", "일반게시물 삭제에 실패하였습니다");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	    }
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
