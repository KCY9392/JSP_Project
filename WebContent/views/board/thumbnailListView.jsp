<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.ArrayList , com.kh.board.model.vo.Board"%>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thumbnail</title>
<style>
	.outer{
	            background-color: rgba(249, 167, 208, 0.164);
	            color: palevioletred;
	            font-weight: bold;
	            height:800px;
	            width: 1000px;
	            margin: auto; /*가운데 정렬*/
	            margin-top: 50px; /*위에서부터 50px 아래 여백 만들기*/
	 }
	        
	.list-area{
	   			margin:auto;
	   			width:760px;
	 }
</style>
</head>
<body>

	<%@ include file = "../common/menubar.jsp" %>
	
	<div class="outer">
		
		<br>
		<h2 style="text-align:center">사진 게시판</h2>
		<br>
		
		<% if(loginUser != null) { %>
			<div align="right" style="width:850px">
				<a href="<%= contextPath %>/enrollForm.th" class="btn btn-secondary">글 작성</a>
				<br><br>
			</div>
		<% } %>
		
		<div class="list-area">
			<% if(!list.isEmpty()){ %>
				<% for( Board b : list){ %>
					<div class="thumbnail" align="center" 
						<%--   onclick="location.href='<%= contextPath %>/detail.th?bno=<%= b.getBoardNo()%>'"--%>>
						<input type="hidden" value="<%= b.getBoardNo() %>">
						
						<img src="<%= contextPath %>/<%= b.getTitleImg() %>" width="200px" height="150px"> 
						<!-- 파일경로 + 썸네일 파일명 -> /jsp/resources/board_upfiles/xxx.jpg -->
						
						<p>
							No.<%= b.getBoardNo() %> <%= b.getBoardTitle() %> <br>
												조회수 : <%= b.getCount() %>
						</p>
					</div>		
				<% } %>
			<% }else{ %>
				등록된 게시물이 없습니다
			<% } %>
		</div>
		
		<script>
			$(function(){
				$(".thumbnail").click(function(){ // thumbnail 아래 모든 요소의 클릭이벤트 걸기
					location.href = "<%= contextPath%>/detail.th?bno="+$(this).children().eq(0).val();
				});
			})
		
		</script>
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	</div>
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>