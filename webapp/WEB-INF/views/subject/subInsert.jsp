<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<!-- 교사의 정보 -->
<input type="hidden" id="name" name="name" value="${name}">
<input type="hidden" id="userno" name="userno" value="${userno}">

<div class="container-fluid">
	<div class="col-md-8" style="float: none; margin: 0 auto;">
	
		<form action="" method="post">
			  <br/><br/>
			  
			  <div class="row">
			  	  <div class="col-md-8">
					  <div class="input-group mb-3">
					      <h5>강의명  </h5>
						  <input type="text" class="form-control" id="subname" name="subname">
					  </div>
					  <div id="subname_check"></div>
				  </div>

			  	  <div class="col-md-4">
					  <div class="input-group mb-3">
					      <h5>정원  </h5>
						  <input type="number" class="form-control" id="cnt" name="cnt">
					  </div>
					  <div id="cnt_check"></div>
				  </div><br/><br/>
			  </div>
			  
	 		  <div class="input-group mb-3">
			      <h5>강의설명  </h5>
			      <textarea class="form-control" rows="5" id="submemo" name="submemo"></textarea>
				  <div id="submemo_check"></div>
			  </div><br/>
			  
			  <div class="text-right">
				  <div class="button">
				  	  <a id="insertBtn" class="btn btn-primary">강의입력</a>
				 	  <a id="resetBtn" class="btn btn-secondary">취소</a>
			  	  </div>
		  	  </div>
			  
		  </form>
		  
	</div>
</div>
<script type="text/javascript" src="resources/js/subject.js" charset="utf-8"></script>
</body>
</html>