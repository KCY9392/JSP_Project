<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>

<style>

    .outer{
        background-color: rgba(249, 167, 208, 0.164);
        color: palevioletred;
        font-weight: bold;
        width: 1000px;
        margin: auto;
        margin-top: 50px;
    }

    #enroll-form table{
        margin: auto;
    }

    #enroll-form input{
        margin: 4px;
    }

</style>

</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>
    <!-- ../ : 현재폴더로부터 한번 빠져나감(즉, 한단계 상위폴더로 이동)-->

    <div class="outer">
        <br>
        <h2 style="text-align:center;">회원가입</h2>

        <form id="enroll-form" action="<%= contextPath %>/insert.me" method="post">
            <!-- menubar.jsp에서 contextPath 변수를 설정했고, 해당 파일을 
                 include를 시켜서 포함했기때문에, contextPath 변수를 사용가능하다 -->
            
            <table>

                <tr>
                    <td>* 아이디</td>
                    <td><input type="text" name="userId" maxlength="12" required></td>
                    <td><button type="button" id="idcheck" onclick="idCheck();">중복확인</button></td>
                </tr>

                <tr>
                    <td>* 비밀번호</td>
                    <td><input type="password" name="userPwd" maxlength="15" required></td>
                    <td></td>
                </tr>

                <tr>
                    <td>* 비밀번호 확인</td>
                    <td><input type="password" maxlength="15" required></td>
                    <td></td>
                </tr>

                <tr>
                    <td>* 이름</td>
                    <td><input type="text" name="userName" maxlength="6" required></td>
                    <td></td>
                </tr>

                <tr>
                    <td>&nbsp;&nbsp;전화번호</td>
                    <td><input type="text" name="phone" placeholder="-를 포함해서 입력하세요"></td>
                    <td></td>
                </tr>

                <tr>
                    <td>&nbsp;&nbsp;이메일</td>
                    <td><input type="email" name="email"></td>
                    <td></td>
                </tr>

                <tr>
                    <td>&nbsp;&nbsp;주소</td>
                    <td><input type="text" name="address"></td>
                    <td></td>
                </tr>

                <tr>
                    <td>&nbsp;&nbsp;관심분야</td>
                    <td colspan="2">
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

            <br>

            <div align="center">
                <button type="submit" disabled>회원가입</button>
                <button type="reset">초기화</button>
            </div>

        </form>
    </div>

    <script>
        function idCheck(){
        	//아이디를 입력하는 input 요소 객체
            let $userId = $("#enroll-form input[name=userId]");
            // name이 userId인 요소가 menubar.jsp에도 있어서 확실하게 어디에 속해있는 요소인지 잘 적어줘야함
        	
            $.ajax({
            	url : "idCheck.me",
            	data : {checkId : $userId.val()},
            	success : result => {
            		if(result == "NNNN"){// 아이디 사용불가
            			alert("이미 존재하거나 회원탈퇴한 아이디입니다.");
            			$userId.focus();
            		} else { // 아이디 사용가능
            			if(confirm("사용가능한 아이디입니다. 사용하시겠습니까?")){
            				$("#enroll-form :submit").removeAttr("disabled"); //회원가입 버튼 활성화
            				$userId.attr("readonly",true); //아이디값 확정
            				$("#idcheck").attr("disabled");
            			}else{ // 아아디 사용안함
            				$userId.focus();
            			}
            		}
            	},
            	error : function(){
            		console.log("아이디 중복체크용 ajax통신실패");
            	}
            });
        }
    </script>


</body>
</html>