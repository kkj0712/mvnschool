<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>Haksa Online</title>
</head>
<body>
<div class="container">
	<div class="row">
		<div class="col-sm-4"></div>
		
		<div class="col-sm-4">
			<form action="" method="post">
				<h1 id="logo-h1"><a href="">Haksa Login</a></h1>
				
				<div class="input-group mb-3">
				    <div class="input-group-prepend">
				      <span class="input-group-text">아이디</span>
				    </div>
				    <input type="text" class="form-control" name="login_userid" id="login_userid">
				</div>
				    <div id="login_id_check"></div>
				
				<div class="input-group mb-3">
				    <div class="input-group-prepend">
				      <span class="input-group-text">비밀번호</span>
				    </div>
					<input type="password" class="form-control" name="login_password" id="login_password">
				</div>
					<div id="login_pwd_check"></div>
				
			    <div class="input-group-prepend">
					<a id="loginBtn" class="btn btn-primary">로그인</a>
					<a id="joinBtn" class="btn btn-secondary" href="join.do">회원가입</a>
				</div>
			</form>
		</div>
		
		<div class="col-sm-4"></div>
	</div>
</div>
</body>
<script type="text/javascript" src="resources/js/user.js" charset="utf-8"></script>
</html>