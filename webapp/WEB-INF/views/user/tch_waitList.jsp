<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<br/><br/>
<div class="container-fluid text-center">
		<table class="table table-hover">
			<colgroup>
				<col width="5%"/>
				<col width="5%"/>
				<col width="10%"/>
				<col width="10%"/>
				<col width="40%"/>
				<col width="10%"/>
				<col width="20%"/>
			</colgroup>
		<thead>
			<tr>
				<th scope="col"><input type="checkbox" name="select_all" id="select_all" value="select_all"></th>
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
						<td><input type="checkbox" name="select_tch" id="select_tch" value="${list.userno}"></td>
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
			
			<!-- 버튼 -->
			<div class="text-right">
				<a id="tchProveBtn" class="btn btn-primary">교사 승인</a>
			</div>
</div>
</body>
<script type="text/javascript" src="resources/js/user.js" charset="utf-8"></script>
</html>