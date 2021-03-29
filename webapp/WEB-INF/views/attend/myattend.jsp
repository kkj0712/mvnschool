<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid text-center">
<!-- 현재 로그인한 유저의 userno 정보 들고오기 -->
<input type="hidden" value="${userno}" id="userno" name="userno">

		<table class="table table-hover">
			<colgroup>
				<col width="5%"/>
				<col width="10%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="10%"/>
				<col width="10%"/>
				<col width="10%"/>
				<col width="25%"/>
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" name="select_all" id="select_all" value="select_all"></th>
					<th scope="col">과목코드</th>
					<th scope="col">강의명</th>
					<th scope="col">교사명</th>
					<th scope="col">정원</th>
					<th scope="col">현재 수강인원</th>
					<th scope="col">상태</th>
					<th scope="col">신청일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="list" varStatus="st">
					<tr>
						<td><input type="checkbox" name="select_att" id="select_att" value="${list.attno}"></td>
						<td>${list.subno}</td>
						<td><a onclick="window.open('subview.do?subno=${list.subno}','','width=600, height=400')" id="subview">${list.subname}</a></td>
						<td>${list.teachername}</td>
						<td>${list.cnt}</td>
						<td>${list.currentCnt}</td>
						
						<!-- 수강신청 상태 -->
						<c:if test="${list.status eq 1}">
							<td>대기</td>
						</c:if>
						<c:if test="${list.status eq 2}">
							<td>승인</td>
						</c:if>
						<c:if test="${list.status eq 3}">
							<td>반려</td>
						</c:if>
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
					<a href="javascript:getData(${pu.endPage+1})">[다음]</a>
				</c:if>
			</div>
			
			<!-- 신청 취소 버튼 -->
			<div class="text-right mr-3">
			 <div class="button">
			 	  <a class="btn btn-secondary" id="cancelBtn">신청취소</a>
				  </div>
			</div>
</div>
</body>
<script type="text/javascript" src="resources/js/attend.js" charset="utf-8"></script>
</html>