<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<script>
$(document).ready(function(){
	getData(1, $("#setRole").val());
	//확인버튼 클릭
	$("#confirmRole").click(function(){
		getData(1, $("#setRole").val());
	});
	
});
//테이블 목록 가져오기
function getData(pageNum, setRole){
	$.get("userCtrl.do", 
			{"pageNum" : pageNum, "setRole" : setRole}, 
			function(d){
				$("#result").html(d);
	})
};//getData
</script>

<!-- 회원구분 선택 영역-->
<br/>
<div class="container-fluid text-right">
	<form name = "searchRole" id = "searchRole">
	구분
		<select name = "setRole" id = "setRole">
			<option value = "student" selected>학생</option>
			<option value = "teacher">교사</option>
		</select>
		<a id="confirmRole" class="btn btn-primary btn-sm">확인</a>
	</form>
</div>

<!-- getData로 넘어오는 테이블 값들. 페이지가 로드되자마자 수강편람 리스트를 불러오도록 자바스크립트 함수 넣기 -->
<div id="result" align="center"></div>