package com.kh.board.controller;

import java.io.File;
import java.io.IOException;
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
import com.oreilly.servlet.MultipartRequest;


@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public BoardUpdateController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	       request.setCharacterEncoding("UTF-8");
	       
	       //enctype이 mulipart/form-data인지 확인 후 true라면 코드 실행
	       if(ServletFileUpload.isMultipartContent(request)) {
	           
	           //1_1. 전송파일의 용량제한
	           int maxSize = 10 * 1024 * 1024;
	           
	           //1_2. 전달된 파일을 저장시킬 서버의 폴더의 물리적인 경로 알아내기
	           String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/");
	           
	           //2. 전달된 파일명 수정작업 후 서버에 업로드
	           MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy() );
	           
	           //3. 본격적으로 sql문 실행시 필요한 값
	           // - Board테이블에 update 항상 수행
	           
	           int boardNo = Integer.parseInt(multiRequest.getParameter("bno"));

	           // 저장된 데이터는 categoryNo지만 넘어온 값은 categoryName이기때문에 문자열
	           String category = multiRequest.getParameter("category");
	           
	           String boardTitle = multiRequest.getParameter("title");
	           
	           String boardContent = multiRequest.getParameter("content");
	           
	           Board b = new Board();
	           b.setBoardNo(boardNo);
	           b.setCategory(category);
	           b.setBoardTitle(boardTitle);
	           b.setBoardContent(boardContent);
	           
	           //새롭게 전달된 첨부파일이 있는 경우에만 at변수에 필요한 값을 추가할 것
	           Attachment at = null;
	           
	           if(multiRequest.getOriginalFileName("upfile") != null) {
	               
	               // 3가지 공통적으로 필요한 변수 셋팅
	               at = new Attachment();
	               at.setOriginName(multiRequest.getOriginalFileName("upfile"));
	               at.setChangeName(multiRequest.getFilesystemName("upfile"));
	               at.setFilePath("resources/board_upfiles/");
	               
	               // 첨부파일이 있을 경우, 원본파일의 파일번호, 수정명을 hidden으로 넘겨받았음.
	               if(multiRequest.getParameter("originFileNo") != null) {
	                   //기존에 파일이 있는 경우
	                   // => update Attachment
	                   // 기존의 파일번호를 가지고 
	                   at.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo")));
	                   
	                   // 기존의 첨부파일 삭제
	                   new File(savePath+multiRequest.getParameter("originFileName")).delete();
	                   
	               }else { 
	                   // 기존에 파일이 없는 경우
	                   // => insert Attachment
	                   // ref_bno + 현재 게시물번호
	                   at.setRefNo(boardNo);
	                   
	               }
	           }
	           
	           // 모두 하나의 트랜잭션으로 처리하기
	           int result = new BoardService().updateBoard(b,at);
	           
	           //case1 : 새로운 첨부파일이 없는 경우(x) => b,null => Board Update
	           
	           //case2 : 새로운 첨부파일이 있는 경우(o) , 기존 첨부파일도 있는 경우(o) => b, at에 fileNo담겨있음
	           //          Board Update, Attachment Update
	           
	           //case3 : 새로운 첨부파일이 있는 경우(o), 기존 첨부파일은 없는 경우(x) => b , at에 refNo가 담겨있음
	           //          Board update, Attachment Insert
	           
	           if(result > 0) { // 성공 => 상세조회 페이지로 redirect
	               request.getSession().setAttribute("alertMsg", "일반게시물이 수정되었습니다");
	               response.sendRedirect(request.getContextPath()+"/detail.bo?bno="+boardNo);
	               
	           }else { // 실패 => errorPage
	               request.setAttribute("errorMsg", "일반게시물 수정에 실패하였습니다");
	               request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
	           }
	       }
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
