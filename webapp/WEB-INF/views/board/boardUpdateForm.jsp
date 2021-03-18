<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<form action="" id="frm" method="post" enctype="multipart/form-data">
	<!-- 이 글을 쓴 User의 userno -->
	<input type="hidden" id="userno" name="userno" value="${board.userno}">
	<!-- 현재 로그인한 User의 userno -->
	<input type="hidden" id="sessionUserno" name="sessionUserno"
		value="${sessionScope.userno}">
	<!-- 현재 게시글의 bnum -->
	<input type="hidden" id="bnum" name="bnum" value="${board.bnum}">
	<!-- 현재 게시글의 fileupload 이름 -->
	<input type="hidden" id="fileupload" name="fileupload" value="${board.fileupload}">

	<br />
	<div class="container-fluid">

		<a onclick="window.history.back()" id="a-back">&#60;&#60; 돌아가기 </a><br />
		<br />

		<div class="form-group">
			제목 <input type="text" class="form-control" id="title" name="title"
				value="${board.title}">
			<div id="title_check"></div>
		</div>

		<div class="form-group">
			파일 <input type="file" class="form-control" id="file" name="file" value="${board.fileupload}">
			<c:if test="${board.fileupload ne null}">
				<div style="float: left">
					<a href="fileDownload.do?fileName=${board.fileupload}">${board.fileupload}
						&nbsp;</a>
				</div>
				<div style="float: left">
					<a id="fileDelBtn" class="badge badge-light">삭제</a>
				</div>
			</c:if>
		</div>
		<br />

		<div class="form-group">
			내용
			<textarea class="form-control" rows="10" id="content" name="content">${board.content}</textarea>
			<div id="content_check"></div>
		</div>

		<div class="text-center">
			<a id="boardUpdateBtn" class="btn btn-primary">수정</a> <a
				id="resetBtn" class="btn btn-secondary">취소</a>
		</div>
	</div>
	<br />

</form>
<script type="text/javascript" src="resources/js/board.js"
	charset="utf-8"></script>