<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<script>
$(document).ready(function(){
	getData(1);
});
//테이블 목록 가져오기
function getData(pageNum){
	$.get("submenual.do", 
			{"pageNum" : pageNum}, 
			function(d){
				$("#result").html(d);
	})
};//getData
</script>
<!-- getData로 넘어오는 테이블 값들. 페이지가 로드되자마자 수강편람 리스트를 불러오도록 자바스크립트 함수 넣기 -->
<div id="result" align="center"></div>

