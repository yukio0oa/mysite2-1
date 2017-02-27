<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
$(function(){
	$( "#join-form" ).submit( function(){
		/* 회원 가입 폼 유효성 검증(validation) */
		
		//1.이름
		var name = $( "#name" ).val();
		if( name == "" ) {
			alert( "이름이 비어 있습니다." );
			$( "#name" ).focus();
			return false;
		}
		
		//2. 이메일
		var email = $( "#email" ).val();
		if( email == "" ) {
			alert( "이메일이 비어 있습니다." );
			$( "#email" ).focus();
			return false;
		}
		
		var isVisible = $("#imgEmailChecked").is( ":visible" );
		if( isVisible == false ){
			alert( "이메일 중복 체크를 해 주세요.");
			return;
		}

		//3. 비밀번호
		var password = $( "input[type='password']" ).val();
		if( password == "" ) {
			alert( "비밀번호가 비어 있습니다." );
			$( "input[type='password']" ).focus();
			return false;
		}
		
		//4. 약관 동의
		var isChecked = $( "#agree-prov" ).is( ":checked" );
		if( isChecked == false ) {
			alert( "약관 동의를 해 주세요." );
			return false;
		}
		
		return true;
	});
	
	
	$( "input[type='button']" ).click( function(){
		var email = $( "#email" ).val();
		if( email == "" ) {
			$( "#email" ).focus();
			return;
		}
		
		/* ajax 통신 */
		$.ajax( {
		    url : "/mysite2/user/checkemail?email=" + email,
		    type: "get",
		    dataType: "json",
		    data: "",
		//  contentType: "application/json",
		    success: function( response ){
		       if( response.result == "fail" ) {
		    	   console.log( "error:" + response.message );
		    	   return;
		       }
		    
		       //통신 성공(response.result == "success" )
		       if( response.data == "exist" ) {
		    	   $( "#dialogMessage" ).dialog();
		    	   
		    	   $("#email").
		    	   val("").
		    	   focus();
		    	   return;
		       }
		       
		       //존재하지 않는 경우(사용가능)
		       $( "#imgEmailChecked" ).show();
		       $( "input[type='button']" ).hide();
		    },
		    error: function( XHR, status, error ){
		       console.error( status + " : " + error );
		    }

		  });
	});
	
	$( "#email" ).change( function(){
		
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"/>
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post" action="${pageContext.request.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="">

					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="">
					<input type="button" value="중복체크">
					<img id="imgEmailChecked" src="/mysite2/assets/images/check.png?a=132" style="display:none">
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"/>
		<c:import url="/WEB-INF/views/include/footer.jsp"/>
	</div>
	
	<div id="dialogMessage" title="이메일 중복체크" style="display:none">
  		<p>존재하는 이메일 입니다.<br>다른 이메일을 사용해 주세요</p>
	</div>	
</body>
</html>