<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<br/>
<div class="container-fluid">
	<div class="text-right">
		<a href="boardForm.do" class="btn btn-outline-primary">글쓰기</a>
	</div>

	<table class="table table-hover">
		<colgroup>
			<col width="5%" />
			<col width="20%" />
			<col width="50%" />
			<col width="15%" />
			<col width="5%" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col">No.</th>
				<th scope="col">작성자</th>
				<th scope="col">제목</th>
				<th scope="col">작성일</th>
				<th scope="col">조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="list" varStatus="st">
				<tr>
					<td>${rowNo-st.index}</td>
					<td>${list.name}</td>
					<td><a href="boardView.do?bnum=${list.bnum}">${list.title}</a></td>
					
					<fmt:parseDate value="${list.regdate}" pattern="yyyy-MM-dd HH:mm:ss" var="parseRegdate" type="both" />
					<fmt:formatDate var="dateParse" pattern="yyyy-MM-dd HH:mm" value="${parseRegdate}" />
					<td>${dateParse}</td>
					<td>${list.hit}</td>
				</tr>
			</c:forEach> 
		</tbody>
	</table>
	
	<!-- 페이지 -->
	<div align="center">
		<c:if test="${pu.startPage>pu.pageBlock}"> <!-- 이전 -->
			<a href="javascript:getData(${pu.startPage-pu.pageBlock},'${pu.field}','${pu.word}')">[이전]</a>
		</c:if>
		
		<c:forEach begin="${pu.startPage}" end="${pu.endPage}" var="i"> <!-- 페이지 출력 -->
			<c:if test="${i==pu.currentPage}">
				<c:out value="${i}" />
			</c:if>
			<c:if test="${i!=pu.currentPage}"> <!-- 현재 페이지 -->
				<a href="javascript:getData(${i},'${pu.field}','${pu.word}')">${i}</a>
			</c:if>
		</c:forEach>
		
		<c:if test="${pu.endPage<pu.totPage}"> <!-- 다음 -->
			<a href="javascript:getData(${pu.endPage+1},'${pu.field}','${pu.word}')">[다음]</a>
		</c:if>
	</div>
</div>