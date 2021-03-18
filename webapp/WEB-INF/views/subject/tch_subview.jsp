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
			<!-- 수정하기 폼으로 이동할때 들고가는 값 -->
			<input type="hidden" id="subno" name="subno" value="${subject.subno}">
			<input type="hidden" id="subname" name="subname" value="${subject.subname}">
			<input type="hidden" id="cnt" name="cnt" value="${subject.cnt}">
			<input type="hidden" id="submemo" name="submemo" value="${subject.submemo}">
			
			  <br/><br/>
			  
			  <a href="/mvnschool/tch_subList_pre.do"> &#60;&#60; 돌아가기 </a><br/><br/>
			  
			  <div class="row">
			  	  <div class="col-md-8">
					  <div class="input-group mb-3">
					      <h5>강의명  </h5>: ${subject.subname}
					  </div>
				  </div>

			  	  <div class="col-md-4">
					  <div class="input-group mb-3">
					      <h5>정원  </h5>: ${subject.cnt}
					  </div>
				  </div><br/><br/>
			  </div>
			  
	 		  <div class="input-group mb-3">
			      <h5>강의설명  </h5>: ${subject.submemo}
			  </div><br/>
			  
			  <div class="text-right">
				  <div class="button">
				  	  <a id="goUpdateBtn" class="btn btn-outline-primary" href="/mvnschool/updateForm.do?subno=${subject.subno}">수정</a>
			  	  </div>
		  	  </div>
			  
		  </form>
	</div>
</div>
</div>
</body>
<script type="text/javascript" src="resources/js/subject.js" charset="utf-8"></script>
</html>