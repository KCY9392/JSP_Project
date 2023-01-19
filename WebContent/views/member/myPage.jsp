<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.common.AESCryptor"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>myPage</title>

<style>
    .outer{
            background-color: rgba(249, 167, 208, 0.164);
            color: palevioletred;
            font-weight: bold;
            width: 1000px;
            margin: auto;
            margin-top: 50px;
        }
        
     #mypage-form table{
     	margin: auto;
     }
     
     #mypage-form input{
        margin: 4px;
    }
</style>

</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>
	<%
        String userId = loginUser.getUserId();
        String userName = loginUser.getUserName();
        String phone = loginUser.getPhone() == null ? "" : loginUser.getPhone();
        String email = loginUser.getEmail() == null ? "" : loginUser.getEmail();
        email = AESCryptor.decrypt(email);
        String address = loginUser.getAddress() == null ? "" : loginUser.getAddress();
        String interest = loginUser.getInterest() == null ? "" : loginUser.getInterest();
    %>
    <div class="outer">
        <br>
        <h2 style="text-align:center;">마이페이지</h2>

		<br>
		
        <form id="mypage-form" action="<%= contextPath %>/update.me" method="post">
            
            <table>
                <tr>
                    <td>* 아이디</td>
                    <td><input type="text" name="userId" value="<%= userId %>" readonly></td><!-- readonly 속성으로 값 변경 막기 -->
                    <td></td>
                </tr>

                <tr>
                    <td>* 이름</td>
                    <td><input type="text" name="userName" maxlength="6" required  value="<%= userName %>"></td>
                    <td></td>
                </tr>

                <tr>
                    <td>&nbsp;&nbsp;번호</td>
                    <td><input type="text" name="phone" value="<%= phone %>"></td>
                    <td></td>
                </tr>

                <tr>
                    <td>&nbsp;&nbsp;이메일</td>
                    <td><input type="text" name="email" value="<%= email %>"></td>
                    <td></td>
                </tr>

                <tr>
                    <td>&nbsp;&nbsp;주소</td>
                    <td><input type="text" name="address" value="<%= address %>"></td>
                    <td></td>
                </tr>

                <tr>
                    <td>&nbsp;&nbsp;관심분야</td>
                    <td colspan="2">
                    <!--  운동, 영화 (한줄짜리 문자열로 들어가있다) -->
                    	<input type="checkbox" name="interest" id="sports" value="운동">
                        <label for="sports">운동</label>

                        <input type="checkbox" name="interest" id="hiking" value="등산">
                        <label for="hiking">등산</label>

                        <input type="checkbox" name="interest" id="fishing" value="낚시">
                        <label for="fishing">낚시</label>

                        <br>

                        <input type="checkbox" name="interest" id="cooking" value="요리">
                        <label for="cooking">요리</label>

                        <input type="checkbox" name="interest" id="game" value="게임">
                        <label for="game">게임</label>

                        <input type="checkbox" name="interest" id="movie" value="영화">
                        <label for="movie">영화</label>
                    </td>
                </tr>

            </table>

			<script>
				$(function() {
					let interests = "<%= interest %>"; // "운동, 영화"
					
					$("input[type=checkbox]").each(function() {
						
						// 문자열변수명.search() : 문자열이 포함되어있다면, 그 해당 인덱스 값을, 일치하지않으면, -1을 반환
						
						// 순차적으로 접근한 input요소의 value값이 interest에 포함되어있을 경우
						// -> 해당 input요소에 checked 속성을 부여할 것.
						
						if(interests.search($(this).val()) != -1){
							// interest 문자열로부터 현재 체크박스의 value가 포함되어있지않다면 -1반환 
							$(this).attr("checked",true);
						}
					})
				})
				
			</script>
			
			<br><br>
			
			<div align="center">
				<button type="submit" class="btn btn-secondary btn-sm">정보변경</button>
				
				<button type="button" class="btn btn-warning btn-sm" 
				 		data-toggle="modal" data-target="#updatePwdForm">비밀번호 변경</button>
				<button type="button" class="btn btn-danger btn-sm" 
						data-toggle="modal" data-target="#deleteForm">회원탈퇴</button>
			</div>
			
			<script>
				$(  {
					let 
				})
			</script>
			<br>
        </form>
    </div>
	
	<div id="updatePwdForm" class="modal" tabindex="-1">
		  <div class="modal-dialog">
		    <div class="modal-content">
		    
		    
		      <div class="modal-header">
		        <h5 class="modal-title">비밀번호 변경</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      
		      
		      <div class="modal-body" align="center">
		        <form action="<%= contextPath%>/updatePwd.me" method="post">
		        	<input type="hidden" name="userId" value="<%= userId %>">
		        	<!-- 변경할 회원의 Id값을 몰래 보냄 -->
		        	
		        	<table>
		        		<tr>
		        			<td>현재 비밀번호</td>
		        			<td><input type="password" name="userPwd" required></td>
		        		</tr>
		        		
		        		<tr>
		        			<td>변경할 비밀번호</td>
		        			<td><input type="password" name="updatePwd" required></td>
		        		</tr>
		        		
		        		<tr>
		        			<td>변경할 비밀번호 재입력</td>
		        			<td><input type="password" name="checkPwd" required></td>
		        		</tr>
		        	</table>
		        	
		        	<br>
		        	
		        	<button type="submit" class="btn btn-secondary btn-sm" onclick="return validatePwd();">비밀번호 변경</button>
		        
		        </form>
		        
		        <script>
		        	function validatePwd(){
		        		if($("input[name=updatePwd]").val() != $("input[name=checkPwd]").val()){
		        			alert("비밀번호가 일치하지 않습니다");
		        			
		        			return false;
		        		} // 기본값은 return true이므로 생략 가능.
		        	}
		        </script>
		        
		      </div>
		    </div>
		 </div>
	</div>
	
	
	<div id="deleteForm" class="modal" tabindex="-1">
		  <div class="modal-dialog">
		    <div class="modal-content">
		    
		    
		      <div class="modal-header">
		        <h5 class="modal-title">회원탈퇴</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      
		      
		      <div class="modal-body" align="center">
		        <form action="<%= contextPath%>/delete.me" method="post">
		        	
		        	<b>탈퇴 후 복구가 불가능합니다 <br>
		        	   정말로 탈퇴하시겠습니까? <br><br>
		        	</b>
		        	
		        	<table>
		        		<tr>
		        			<td>현재 비밀번호 &nbsp;</td>
		        			<td><input type="password" name="userPwd" required></td>
		        		</tr>
		        	</table>
		        	
		        	<br>
		        	
		        	<button type="submit" class="btn btn-danger btn-sm">탈퇴하기</button>
		        
		        </form>
		        
		        
		      </div>
		    </div>
		 </div>
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>