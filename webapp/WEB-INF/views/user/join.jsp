<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<title>Haksa Online</title>
</head>
<body>
<div class="container-fluid">
	<div class="col-md-4" style="float: none; margin: 0 auto;">
		
		<form action="" method="post">
		  <br/><br/>
		  <div class="input-group mb-3">
		    <div class="input-group-prepend">
		      <span class="input-group-text">구분</span>
		    </div>
		      <select name="role" id="role" class="custom-select">
			    <option value="1" selected>학생</option>
			    <option value="2">교사</option>
			  </select>
		  </div>		  
		  <br/>
		  
		  <div class="input-group mb-3">
		    <div class="input-group-prepend">
		      <span class="input-group-text">아이디</span>
		    </div>
		      <input type="text" class="form-control" id="userid" name="userid">
		  </div>
		  	<div id="id_check"></div>
		  <br/>
		   
		  <div class="input-group mb-3">
		    <div class="input-group-prepend">
		      <span class="input-group-text">이름</span>
		    </div>
		      <input type="text" class="form-control" id="name" name="name">
		  </div>
		  	<div id="name_check"></div>
		  <br/>
		  
		  <div class="input-group mb-3">
		    <div class="input-group-prepend">
		      <span class="input-group-text">비밀번호</span>
		    </div>
		    <input type="password" class="form-control" placeholder="6자리 이상" id="password" name="password">
		    <input type="password" class="form-control" placeholder="비밀번호 확인" id="password_check" name="password_check">
		  </div>
		  	<div id="pwd_check"></div>
		  <br/>
		  
		  <div class="input-group mb-3">
		    <div class="input-group-prepend">
		      <span class="input-group-text">이메일</span>
		    </div>
		      <input type="text" class="form-control" id="email" name="email" placeholder="email@email.com">
		  </div>
		  	<div id="email_check"></div>
		  <br/>
	
		  <div class="input-group mb-3">
		    <div class="input-group-prepend">
		      <span class="input-group-text">주소</span>
		    </div>
		    <input type="text" id="address" name="address" placeholder="주소" class="form-control">
		  </div>
		  <br/>

		  <div class="input-group mb-3">
		    <div class="input-group-prepend">
		      <span class="input-group-text">전화번호</span>
		    </div>
		    <input type="text" id="phone" name="phone" placeholder="하이픈(-)제외    예) 01012345678" class="form-control">
		  </div>
		  	<div id="phone_check"></div>
		  <br/>
		   
		  <div class="text-center">
			  <div class="button">
			  	  <a id="send" class="btn btn-primary">회원가입</a>
			 	  <a class="btn btn-secondary" href="/">취소</a>
		  	  </div>
		  </div>
		</form>
	</div>
</div>
</body>
<script type="text/javascript" src="resources/js/user.js" charset="utf-8"></script>
</html>