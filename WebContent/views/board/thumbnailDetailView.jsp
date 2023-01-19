<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.Attachment, com.kh.board.model.vo.Board"%>
    
<%
	Board b = (Board)request.getAttribute("b");
	ArrayList<Attachment> list = (ArrayList<Attachment>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
</style>
</head>
<body>
	
	<%@ include file = "../common/menubar.jsp" %>
	
	<div class="outer">
	
		<br>
		<h2 style="text-align:center">사진 게시판 상세보기</h2>
		<br>
		
		<table class="detail-area" align="center">
			<tr>
				<td width="70">제목</td>
				<td colspan="3" width="600"><%= b.getBoardTitle() %></td>
			</tr>
			
			<tr>
				<td>작성자</td>
				<td><%= b.getBoardWriter() %></td>
				<td>작성일</td>
				<td><%= b.getCreateDate() %></td>
			</tr>
			
			<tr>
				<td>내용</td>
				<td colspan="3">
					<p style="height:50px"><%= b.getBoardContent() %></p>
				</td>
			</tr>
			
			<tr>
				<td>대표사진</td>
				<td colspan="3">
					<div>
						<img src="<%= contextPath %>/<%= list.get(0).getFilePath()+list.get(0).getChangeName() %>" width="500" height="300">
					</div>
				</td>
			</tr>
			
			<tr>
				<td>상세사진</td>
				<td colspan="3">
				<% for(int i=1; i<list.size(); i++){ %>
					<img src="<%= contextPath %>/<%= list.get(i).getFilePath()+list.get(i).getChangeName() %>" width="165" height="150">
				<% } %>
				</td>
			</tr>
		</table>
	
	</div>
	
	
</body>
</html>