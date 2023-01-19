package com.kh.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;


@WebServlet(name="boardDeleteServlet", urlPatterns="/detail.bo")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public BoardDetailController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	    int boardNo = Integer.parseInt(request.getParameter("bno"));
	    
	    BoardService bService = new BoardService();
	
	    // 조회수 증가 / 게시글 조회 (Board) / 첨부파일 조회(Attachment)
	    
	    int result = bService.increaseCount(boardNo);
	    
	    if(result > 0) { // 성공 => 게시글, 첨부파일 조회 => 상세조회 페이지
	        
	        Board b = bService.selectBoard(boardNo);
	        Attachment at = bService.selectAttachment(boardNo);
	        
	        request.setAttribute("b", b);
	        request.setAttribute("at", at);
	        
	        request.getRequestDispatcher("views/board/boardDetailView.jsp").forward(request, response);
	        
	        
	    }else { //실패
	        request.setAttribute("errorMsg", "게시글 상세조회 실패");
	        request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
