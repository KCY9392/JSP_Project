<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.member.model.vo.Member"%>

<%
    String contextPath = request.getContextPath();
	
	Member loginUser = (Member)session.getAttribute("loginUser");
	//로그인 전 or 로그인실패 : null
	//로그인 성공 후 : 로그인 한 회원의 정보가 담긴 member객체가 담겨있음
	
	String alertMsg = (String)session.getAttribute("alertMsg");
	// 서비스 요청전 : null
	// 서비스 요청성공 후 : alert로 띄워줄 메시지 문구가 담겨있음
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>B CLASS</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<style>
    button{
        border: 2px solid white;
        background-color: pink;
        color: white;
    }
    #login-form, #user-info{
        float: right;
        font-weight: bold;
    }

	#user-info a{
		text-decoration : none;
		color : black;
		font-size : 12px;
	}
	
    .nav-area{
        background-color: rgb(253, 228, 232);
    }

    .menu{
        display: table-cell; /*인라인 요소처럼 배치*/
        width: 150px;
        height: 50px;
    }

    .menu a{
        text-decoration: none;
        color: rgba(248, 129, 189, 0.69);
        font-size: 20px;
        font-weight: bold;
        display: block;
        width: 100%;
        height: 100%;
        line-height: 50px;
    }

    .menu a:hover{
        background-color: rgb(243, 216, 221);
    }
</style>

</head>
<body>
	
	<script>
		let msg = "<%= alertMsg %>"; // let msg = "로그인에 성공하였습니다."
		
		if(msg != "null"){
			alert(msg);
			
			/*알림창을 띄워준 후, session에 남아있는 alertMsg 지워줘야한다
			  안그러면 menubar.jsp가 로딩될때마다 매번 alert가 계속 뜰것. */
			<% session.removeAttribute("alertMsg"); %>
		}
		
		
		
	</script>
	
	
    <h1 style="text-align:center; color:hotpink">Welcome B class</h1>
    
<div class="login-area">

<% if(loginUser == null){%>
    <form id="login-form" action="<%= contextPath %>/login.me" method="post"
        style="border: 5px solid rgb(248, 215, 221)">
        <table>
            <tr>
                <th>아이디 &nbsp;&nbsp;&nbsp;: </th>
                <td><input type="text" name="userId" required></td>
            </tr>
            <tr>
                <th>비밀번호 : </th>
                <td><input type="password" name="userPwd" required></td>
            </tr>
            <tr>
                <th colspan="2">
                    <center>
                	<input type="checkbox" id="saveId"><label for="saveId">아이디 저장</label><br>
                        <button type="button" onclick="submitLogin()">로그인</button>
                        <button type="button" onclick="enrollPage();">회원가입</button>
                    </center>
                </th>
            </tr>
        </table>

    </form>

    <script>
   		$(function(){
   			getCookie()
   		})
    	
        function enrollPage(){
        	
			// location.href = /jsp/views/member/memberEnrollForm.jsp
			
			// 웹 애플리케이션의 디렉토리 구조가 url에 노출되면 보안에 취약하다
			// 단순한 정적인 페이지라도 반드시 servlet을 거쳐가야한다
			location.href = "<%= contextPath %>/enrollForm.me";
			
        }
        
        function submitLogin(){
        	
        	let userId = $("input[name=userId]").val();
        	
        	if($("#saveId").is(":checked")){//true면 체크된 상태
        		document.cookie = "saveId="+userId+"; path=/; max-age ="+60*60*24*7; //7일로 보관되는 쿠키 최대시간 설정
        	}else{//체크안된상태
        		document.cookie = "saveId="+userId+"; path=/; max-age ="+0; //쿠키 보관최대시간을 0으로 설정해서 해당쿠키를 제거
        	}
        	let form = $("#login-form");
        	form.submit();
        }
        
        function getCookie(){
        
        	let value = "";
        	if(document.cookie.length > 0){
        		let index = document.cookie.indexOf("saveId="); //saveId = admin; path=/; max-age=0 or 5660~;
        		if(index != -1){
        			index += "saveId=".length;
        			let end = document.cookie.indexOf(";", index);
        			console.log(index, end);
        			if(end == -1){
        				value = document.cookie.substring(index);
        			}else{
	        			value = document.cookie.substring(index, end);        				
        			}
        		}
        	}
        	$("input[name=userId]").val(value);
        	$("#saveId").attr("checked",true);
        }
        
        
    </script>
    <% } else { %>
    <!-- 로그인 성공 후 -->

    <div id="user-info">
        <b><%= loginUser.getUserName() %></b>님 환영합니당<br><br>
        <div align="center">
            <a href="<%= contextPath %>/myPage.me">마이페이지</a>
            <a href="<%= contextPath %>/logout.me">&nbsp;로그아웃</a>
        </div>
    </div>
    <% } %>
</div>

    <br clear="both"><!-- float 속성 해제 -->
    <br>

    <div class="nav-area" align="center">
        <div class="menu"><a href="<%= contextPath %>">HOME</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.no">공지사항</a></div>
        <div class="menu"><a href="<%= contextPath %>/board/list.bo?currentPage=1">일반게시판</a></div>
        <div class="menu"><a href="<%= contextPath %>/thumb/list.th">사진게시판</a></div>
    </div>

</body>
</html>