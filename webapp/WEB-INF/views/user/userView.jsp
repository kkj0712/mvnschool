<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<br />
<br />

<div class="container-fluid">
	<div class="col-md-4" style="float: none; margin: 0 auto;">

		<a onclick="window.history.back()" id="a-back">&#60;&#60; 돌아가기 </a><br />
		<br />

		<!-- 회원의 userno -->
		<input type="hidden" id="user_userno" name="user_userno" value="${user.userno}">

		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<span class="input-group-text">이름</span>
			</div>
			<input type="text" class="form-control" id="user_name" name="user_name" value="${user.name}" readonly="readonly">
		</div>
		<div id="user_name_check"></div>
		<br />

		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<span class="input-group-text">이메일</span>
			</div>
			<input type="text" class="form-control" id="user_email" name="user_email" value="${user.email}">
		</div>
		<div id="user_email_check"></div>
		<br />

		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<span class="input-group-text">주소</span>
			</div>
			<input type="text" class="form-control" id="user_address" name="user_address" value="${user.address}">
		</div>
		<div id="user_address_check"></div>
		<br />

		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<span class="input-group-text">전화번호</span>
			</div>
			<input type="text" class="form-control" id="user_phone" name="user_phone" value="${user.phone}">
		</div>
		<div id="user_phone_check"></div>
		<br />

		<div class="text-right">
			<div class="button">
				<a id="userUpdateBtn" class="btn btn-primary">수정</a> 
				<a id="resetBtn" class="btn btn-secondary" onclick="window.history.back()">취소</a>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="resources/js/user.js" charset="utf-8"></script>