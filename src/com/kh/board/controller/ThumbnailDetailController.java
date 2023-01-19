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


@WebServlet("/detail.th")
public class ThumbnailDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public ThumbnailDetailController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    int boardNo = Integer.parseInt(request.getParameter("bno"));
	    
	    BoardService bService = new BoardService();
	    
	    int result = bService.increaseCount(boardNo);
        
        if(result > 0) { // 성공 
            //필요한 데이터 => 게시글 정보(Board), 첨부파일들 정보(Attachment) => 상세조회페이지
            
            Board b = bService.selectBoard(boardNo);
            
            ArrayList<Attachment> list = bService.selectAttachmentList(boardNo);
            
            System.out.println(list);
            
            request.setAttribute("b", b);
            
            request.setAttribute("list", list);
            
            request.getRequestDispatcher("views/board/thumbnailDetailView.jsp").forward(request, response);
            
            
        }else { //실패
            request.setAttribute("errorMsg", "사진게시글 조회 실패");
            request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
        }
        
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
