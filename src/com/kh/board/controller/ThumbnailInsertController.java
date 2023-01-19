package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.kh.member.model.vo.Member;
import com.oreilly.servlet.MultipartRequest;


@WebServlet("/insert.th")
public class ThumbnailInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public ThumbnailInsertController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    if(ServletFileUpload.isMultipartContent(request)) {
	        
	        // 1_1. 전송용량 제한
	        int maxSize = 10 * 1024 * 1024; // 10mByte
	        
	        // 1_2. 저장할 폴더의 물리적인 경로
	        String savePath = request.getServletContext().getRealPath("/resources/thumbnail_upfiles/");
	        
	        // 2. 전달된 파일명 수정 작업 후 서버에 업로드
	        MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
	        
	        // 3. db에 기록할 데이터들 뽑아서 Board객체에 넣기
	        // Board에 insert할 값 뽑기
	        
	        Board b = new Board();
	        b.setBoardWriter(((Member)request.getSession().getAttribute("loginUser")).getUserNo()+"");
	        //받아온값이 int형인데 ""을 더해서 강제로 String변환
	        b.setBoardTitle(multiRequest.getParameter("boardTitle"));
	        b.setBoardContent(multiRequest.getParameter("boardContent"));
	        
	        // Attachment에 여러번 insert할 데이터 뽑기
	        // 단, 여러개의 첨부파일이 있을것이기때문에, attachment객체들을 ArrayList에 담을 것임
	        // => 적어도 1개 이상은 담길예정
	        ArrayList<Attachment> list = new ArrayList<>();
	        
	        for(int i=1; i<=4; i++) { // file의 개수는 최대4개이고, 파일name을 file1,file2,file3,file4로 넘겼기때문에 i=1부터
	            
	            String key = "file"+i;
	            
	            if(multiRequest.getOriginalFileName(key) != null) {
	                //첨부파일이 있는 경우
	                // Attachment객체 생성 + 원본명, 수정명, 파일경로 넣기 + 파일level 담기
	                // list에 추가하기
	                Attachment at = new Attachment();
	                at.setOriginName(multiRequest.getOriginalFileName(key));
	                at.setChangeName(multiRequest.getFilesystemName(key));
	                at.setFilePath("/resources/thumbnail_upfiles/");
	                at.setFileLevel(i); // 대표이미지 1, 상세이미지 2 3 4
	                
	                list.add(at);
	            }
	        }
	        
	        int result = new BoardService().insertThumbnailBoard(b, list);
	        
	        if(result > 0) { // 성공 => list.th 재요청
	            request.getSession().setAttribute("alertMsg", "성공적으로 업로드되었습니다");
	            
	            response.sendRedirect(request.getContextPath()+"/list.th");
	            
	        }else { // 실패
	            request.setAttribute("errorMsg", "사진게시판 작성에 실패하였습니다");
	            
	            request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	        }
	        
	    }
	    
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
