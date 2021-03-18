<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<br/><br/>
<div class="container">
	<a onclick="window.history.back()" id="a-back">&#60;&#60; 돌아가기 </a><br/><br/> 
	<h4> ${subname}의 수강생목록</h4>
</div>
<div class="container-fluid text-center">
	<table class="table table-hover">
		<colgroup>
			<col width="5%"/>
			<col width="10%"/>
			<col width="10%"/>
			<col width="40%"/>
			<col width="10%"/>
			<col width="25%"/>
		</colgroup>
		<thead>
			<tr>
				<th scope="col">No.</th>
				<th scope="col">이름</th>
				<th scope="col">아이디</th>
				<th scope="col">주소</th>
				<th scope="col">전화번호</th>
				<th scope="col">이메일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="list" varStatus="st">
				<tr>
					<td>${rowNo-st.index}</td>
					<td>${list.name}</td>
					<td>${list.userid}</td>
					<td>${list.address}</td>
					<td>${list.phone}</td>
					<td>${list.email}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div align="center">
		<c:if test="${pu.startPage>pu.pageBlock}"> <!-- 이전 -->
			<a href="javascript:getData(${pu.startPage-pu.pageBlock})">[이전]</a>
		</c:if>
		
		<c:forEach begin="${pu.startPage}" end="${pu.endPage}" var="i"> <!-- 페이지 출력 -->
			<c:if test="${i==pu.currentPage}">
				<c:out value="${i}" />
			</c:if>
			<c:if test="${i!=pu.currentPage}"> <!-- 현재 페이지 -->
				<a href="javascript:getData(${i})">${i}</a>
			</c:if>
		</c:forEach>
		
		<c:if test="${pu.endPage<pu.totPage}"> <!-- 다음 -->
			<a href="javascript:getData(${pu.endPage+1}")>[다음]</a>
		</c:if>
	</div>
</div>