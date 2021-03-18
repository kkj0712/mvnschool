<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>강의 상세정보</title>
</head>
<body>
<div>
<div class="container-fluid">
	<div class="col-md-8" style="float: none; margin: 0 auto;">
		<form action="" method="post">
			<!-- subno값 -->
			<input type="hidden" id="subno" name="subno" value="${subject.subno}">
			
			  <br/><br/>
			  
			  <a onclick="window.history.back()" id="a-back">&#60;&#60; 돌아가기 </a><br/><br/>
			  
			  <div class="row">
			  	  <div class="col-md-8">
					  <div class="input-group mb-3">
					      <h5>강의명  </h5>
							<input type="text" id="subname" name="subname" value="${subject.subname}">
					  </div>
					  <div id="subname_check"></div>
				  </div>

			  	  <div class="col-md-4">
					  <div class="input-group mb-3">
					      <h5>정원  </h5>
							<input type="text" id="cnt" name="cnt" value="${subject.cnt}">
					  </div>
					  <div id="cnt_check"></div>
				  </div><br/><br/>
			  </div>
			  
	 		  <div class="input-group mb-3">
			      <h5>강의설명  </h5>
			      	<textarea class="form-control" rows="5" id="submemo" name="submemo">${subject.submemo}</textarea>
			      	<div id="submemo_check"></div>
			  </div><br/>
			  
			  <div class="text-right">
				  <div class="button">
				  	  <a id="updateProBtn" class="btn btn-primary">수정</a>
				  	  <a id="deleteBtn" class="btn btn-secondary">삭제</a>
			  	  </div>
		  	  </div>
			  
		  </form>
	</div>
</div>
</div>
</body>
<script type="text/javascript" src="resources/js/subject.js" charset="utf-8"></script>
</html>
