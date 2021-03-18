<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
$(document).ready(function(){
	var path = location.pathname;
	var subview = 'tch_subview.do';
	var updateForm = 'updateForm.do';
	var checkStu = 'tch_chkStu_pre.do';
	var pwdChange = 'pwd_change.do';
	var userCtrl = 'userView.do';
	
	$("a[href='"+path+ "']").addClass("active");
	
	if(path.includes(subview) || path.includes(updateForm) || path.includes(checkStu)){
		$("#subviewAct").addClass("active");
	};
	
	if(path.includes(pwdChange)){
		$("#myPage").addClass("active");
	}
	
	if(path.includes(userCtrl)){
		$("#userCtrl").addClass("active");
	}
	
	if(path.includes('boardView.do') || path.includes('boardForm.do') || path.includes('boardUpdateForm.do')){
		$("#freeBoard").addClass("active");
	}
});
</script>
<title>Insert title here</title>
</head>
<body>
<header class="bg-primary">
	<nav class="navbar navbar-expand-sm navbar-dark">
	  <ul class="navbar-nav">
	    <li class="nav-item">
	      <a class="nav-link" href="mypage.do" id="myPage">마이페이지</a>
	    </li>
	    
	    <c:choose>
		    <c:when test="${sessionScope.role==1}">
			    <li class="nav-item">
			      <a class="nav-link" href="submenual_pre.do">수강편람</a>
			    </li>
			    <li class="nav-item">
			      <a class="nav-link" href="myattend_pre.do">수강신청목록</a>
			    </li>
			    <li class="nav-item">
			      <a class="nav-link" href="prvAttend_pre.do">수강목록</a>
			    </li>
			</c:when>
			
		    <c:when test="${sessionScope.role==2}">
			    <li class="nav-item">
			      <a class="nav-link" href="subInsert.do">강의입력</a>
			    </li>
			    <li class="nav-item">
			      <a class="nav-link" href="attendCtrl_pre.do">수강신청 관리</a>
			    </li>
			    <li class="nav-item">
			      <a class="nav-link" href="tch_subList_pre.do" id="subviewAct">강의목록</a>
			    </li>
			</c:when>
			
		    <c:when test="${sessionScope.role==3}">
			    <li class="nav-item">
			      <a class="nav-link" href="userCtrl_pre.do" id="userCtrl">회원관리</a>
			    </li>
			    <li class="nav-item">
			      <a class="nav-link" href="tchWaitList_pre.do" id="tchWaitList">교사 회원신청</a>
			    </li>
			</c:when>
	    </c:choose>
	    
	    <li class="nav-item">
	      <a class="nav-link" href="board_pre.do" id="freeBoard">자유게시판</a>
	    </li>
	    
	  </ul>
	</nav>
   	  <a class="btn-logout" href="logout.do">로그아웃</a>
</header>