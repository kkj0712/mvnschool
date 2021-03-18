<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<div class="container-fluid">
	<div class="col-md-8" style="float: none; margin: 0 auto;">
	
		<!-- 파일업로드는 multipart/form-data -->
		<form action="" id="frm" method="post" enctype="multipart/form-data">
			<br/><br/>
			<div class="form-group">
				제목
				<input type="text" class="form-control" id="title" name="title">
				<div id="title_check"></div>
			</div>
			
			<div class="form-group">
				파일 
				<input type="file" class="form-control" id="file" name="file">
			</div>
			
			<div class="form-group">
				내용 
				<textarea class="form-control" rows="10" id="content" name="content"></textarea>
				<div id="content_check"></div>
			</div>
			
			<div class="text-center">
				<a id="sendBtn" class="btn btn-primary">작성</a>
				<a id="resetBtn" class="btn btn-danger">취소</a>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript" src="resources/js/board.js" charset="utf-8"></script>
