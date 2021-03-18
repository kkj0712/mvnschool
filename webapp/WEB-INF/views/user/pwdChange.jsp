<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
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
<title>Insert title here</title>
</head>
<body>
<br/><br/>
	<div class="container-fluid">
		
	
		<div class="row">
			<div class="col-md-6" style="float: none; margin: 0 auto;">
				
				<h4>비밀번호 변경</h4>
				<hr>
				<br/><br/>
				
				<div class="input-group mb-3">
				    <div class="input-group-prepend">
				      <span class="input-group-text">비밀번호</span>
				    </div>
				      <input type="password" class="form-control" id="change_pwd" name="my_name" placeholder="6자리 이상">
				    <div class="input-group-prepend">
				      <span class="input-group-text">비밀번호 확인</span>
				    </div>
				      <input type="password" class="form-control" id="change_pwd_check" name="my_name" placeholder="6자리 이상">
				</div>
					<div id="pwd_confirm"></div>
				<br/>
				
				<div class="text-right">
					<a id="pwd_btn" class="btn btn-primary">변경</a>
					<a href="/mvnschool/mypage.do" class="btn btn-secondary">취소</a>
				</div>
				
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="resources/js/user.js" charset="utf-8"></script>
</html>