<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<script>
$(document).ready(function(){
	getData(1, "", "");
	
	//검색버튼을 눌렀을 경우
	$("#searchBtn").click(function(){
		getData("1", $("#field").val(), $("#word").val());
	}); //searchBtn
});
//테이블 목록 가져오기
function getData(pageNum, field, word){
	$.get("boardList.do", 
			{"pageNum": pageNum, "field": field, "word": word},
			function(d){
				$("#result").html(d);
	})
};//getData
</script>
<!-- getData로 넘어오는 테이블 값들. 페이지가 로드되자마자 수강편람 리스트를 불러오도록 자바스크립트 함수 넣기 -->
<div id="result" align="center"></div>

<!-- 검색 -->
<div align="left">
	<form name="search" id="search">
		<select name="field" id="field">
			<option value="name">작성자</option>
			<option value="title">제목</option>
			<option value="content">내용</option>
		</select>
		<input type="text" name="word" id="word">
		<a id="searchBtn" class="btn btn-info">검색</a>
	</form>
</div>
