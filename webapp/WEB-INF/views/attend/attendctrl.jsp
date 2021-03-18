<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid text-center">
<!-- 현재 로그인한 유저(교사)의 userno 정보 들고오기 -->
<input type="hidden" value="${teacherno}" id="teacherno" name="teacherno">

		<table class="table table-hover">
			<colgroup>
				<col width="5%"/>
				<col width="5%"/>
				<col width="10%"/>
				<col width="35%"/>
				<col width="35%"/>
				<col width="10%"/>
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" name="select_all" id="select_all" value="select_all"></th>
					<th scope="col">No</th>
					<th scope="col">과목고유키</th>
					<th scope="col">과목명</th>
					<th scope="col">학생명</th>
					<th scope="col">신청일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="list" varStatus="st">
					<tr>
						<td><input type="checkbox" name="select_stu" id="select_stu" value="${list.stuno}"></td>
						<td>${rowNo-st.index}</td>
						<td>${list.subno}</td>
						<td>${list.subname}</td>
						<td>${list.name}</td>
						<td>${list.regdate}</td>
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
			
			<div class="text-right mr-3">
			 <div class="button">
			 	  <a class="btn btn-primary" id="proveBtn">승인</a>
			 	  <a class="btn btn-secondary" id="rejectBtn">반려</a>
				  </div>
			</div>
</div>
</body>
<script type="text/javascript" src="resources/js/attend.js" charset="utf-8"></script>
</html>