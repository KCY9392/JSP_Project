<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글작성 페이지</title>
<style>
	.outer{
	        background-color: rgba(249, 167, 208, 0.164);
	        color: palevioletred;
	        font-weight: bold;
	        height: 500px;
	        width: 1000px;
	        margin: auto;
	        margin-top: 50px;
	    }
	    
	#enroll-form>table{
        border: 2px solid white;
    }
    
    #enroll-form input, #enroll-form textarea{
    	width:100%; /* 가로길이를 부모요소의 100%로 맞춤 */
    	box-sizing : border-box;
    }

    
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		
		<h2 style="text-align:center;">공지사항 작성하기</h2>
		<br>
		
		<form id="enroll-form" action="<%= contextPath %>/insert.no" method="post">
			<!--
				 현재 로그인한 유저가 누구인지 알아내는 방법
				 
				 1. hidden타입의 input태그로 현재 session에 있는 loginUser를 통해 알아내는 방법 - 지금 사용할것
				 
				 2. 서블릿 단에서 session.getAttribute()로 알아내는 방법	
						 
			-->
			
			<input type="hidden" name="userNo" value="<%= loginUser.getUserNo() %>">
			
			<table align="center">
	                <tr>
	                    <th width="50">제목</th>
	                    <td width="350"><input type="text" name="title" required></td>
	                </tr>
	
	                <tr>
	                    <th>내용</th>
	                    <td></td>
	                </tr>
	
	                <tr>
	                    <td colspan="2">
	                    	<textarea name="content" rows="10" style="resize:none;" required></textarea>
	                </tr>
	        </table>
	        
	        <br><br>
	        
	        <div align="center">
	        	<button type="submit">등록하기</button>
	        	<button type="button" onclick="history.back();">뒤로가기</button>
	        	<!-- history.back() : 이전페이지로 돌아가게 해주는 함수 -->
	        </div>
	        
	        <br>
		</form>
	
	</div>
	
</body>
</html>