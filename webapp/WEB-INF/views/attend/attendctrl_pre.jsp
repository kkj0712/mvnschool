<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<script>
$(document).ready(function(){
	getData(1);
});
//테이블 목록 가져오기
function getData(pageNum){
	$.get("attendCtrl.do", 
			{"pageNum" : pageNum}, 
			function(d){
				$("#result").html(d);
	})
};//getData
</script>
<div id="result" align="center"></div>