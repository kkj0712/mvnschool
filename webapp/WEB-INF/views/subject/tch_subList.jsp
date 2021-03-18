<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid text-center">
		<table class="table table-hover">
			<colgroup>
				<col width="10%"/>
				<col width="30%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
			</colgroup>
			<thead>
				<tr>
					<th scope="col">과목코드</th>
					<th scope="col">강의명</th>
					<th scope="col">정원</th>
					<th scope="col">현재 수강인원</th>
					<th scope="col">수강생목록</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${tch_sublist}" var="tch_sublist" varStatus="st">
					<tr>
						<td>${tch_sublist.subno}</td>
						<td><a href="tch_subview.do?subno=${tch_sublist.subno}">${tch_sublist.subname}</a></td>
						<td>${tch_sublist.cnt}</td>
						<td>${tch_sublist.currentCnt}</td>
						<td><a href="tch_chkStu_pre.do?subno=${tch_sublist.subno}">확인</a></td>
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
				<a href="javascript:getData(${pu.endPage+1})">[다음]</a>
			</c:if>
		</div>
		
</div>
</body>
<script type="text/javascript" src="resources/js/subject.js" charset="utf-8"></script>
</html>