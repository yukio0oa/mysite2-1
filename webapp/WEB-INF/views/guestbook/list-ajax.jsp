<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css?a=1" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
var isEnd = false;
var page = 0;
var render = function( vo, prepend ){
	var html = "<li>" +
			   "<strong>" + vo.name + "</strong>" +
			   "<p>" + vo.content + "</p>" +
			   "<strong>" + vo.regDate + "</strong>" +
			   "<a href='' title='삭제'>삭제</a>" +
			   "</li>";
	
	if( prepend == true ) {
		$( "#list" ).prepend( html );
	} else {
		$( "#list" ).append( html );
	}
}
var fetchList = function(){
	if( isEnd == true ) {
		return;
	}
	console.log( "!" );
	++page;
	$.ajax( {
		url : "/mysite2/api/guestbook/list/" + page,
		type: "get",
	    dataType: "json",
	    data: "",
	//  contentType: "application/json",
	    success: function( response ){
	    	if( response.result != "success" ) {
	    		console.log( response.message );
	    		return;
	    	} 
	    	
	    	if( response.data.length == 0 ) {
	    		isEnd = true;
	    		return;	
	    	}
	    	
	    	$( response.data ).each( function(index, vo){
	    		render( vo, false );
	    	});
	    },
	    error: function( XHR, status, error ){
	       console.error( status + " : " + error );
	   	}
  });
}

$(function(){
	$( "#write-form" ).submit( function(event){
		// 폼의 submit 기본 이벤트 처리를 막는다.
		event.preventDefault();
		
		/* ajax 입력 */
		$.ajax( {
			url : "/mysite2/api/guestbook/add",
			type: "post",
		    dataType: "json",
		    data: "name=" + $("input[name='name']").val() + "&" + 
		          "password=" + $("input[name='password']").val() + "&" +
		          "content=" + $("textarea").val(),
		    success: function( response ){
				console.log( response );
				render( response.data, true );
		    },
		    error: function( XHR, status, error ){
		       console.error( status + " : " + error );
		   	}
	    });
		
		return false;
	});
	
	$( window ).scroll(function(){
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $(document).height();
		
		if( scrollTop + windowHeight + 10 > documentHeight ) {
			fetchList();	
		}
	});
	
	//첫 페이지 로딩
	fetchList();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="write-form" action="" method="post">
					<input type="text" name="name" placeholder="이름">
					<input type="password" name="password" placeholder="비밀번호">
					<textarea name="content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list">
				</ul>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp" />
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>