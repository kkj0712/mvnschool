<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<br/><br/>
	<div class="container-fluid">
		<div class="col-md-4" style="float: none; margin: 0 auto;">
			
			<form action="" method="post">
			  <div class="input-group mb-3">
			    <div class="input-group-prepend">
			      <span class="input-group-text">아이디</span>
			    </div>
			      <input type="text" class="form-control" id="my_userid" name="my_userid" value="${user.userid}" readonly="readonly">
			  </div>
			  <br/>
			   
			  <div class="input-group mb-3">
			    <div class="input-group-prepend">
			      <span class="input-group-text">이름</span>
			    </div>
			      <input type="text" class="form-control" id="my_name" name="my_name" value="${user.name}" readonly="readonly">
			  </div>
			  <br/>
			  
			  <div class="input-group mb-3">
			    <div class="input-group-prepend">
			      <span class="input-group-text">이메일</span>
			    </div>
			      <input type="text" class="form-control" id="my_email" name="my_email" value="${user.email}">
			  </div>
			  	  <div id="my_email_check"></div>
			  <br/>
			  
			  <div class="input-group mb-3">
			    <div class="input-group-prepend">
			      <span class="input-group-text">주소</span>
			    </div>
			    <input type="text" class="form-control" id="my_address" name="my_address" value="${user.address}">
			  </div>
			  <br/>
	
			  <div class="input-group mb-3">
			    <div class="input-group-prepend">
			      <span class="input-group-text">전화번호</span>
			    </div>
			    <input type="text" class="form-control" id="my_phone" name="my_phone" value="${user.phone}">
			  </div>
			  <br/>
			   
			  <div class="text-center">
				  <div class="button">
				  	  <a class="btn btn-primary" href="pwd_change.do">비밀번호 변경</a>
				 	  <a id="my_save" class="btn btn-secondary">저장</a>
			  	  </div>
			  </div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript" src="resources/js/user.js" charset="utf-8"></script>
</html>