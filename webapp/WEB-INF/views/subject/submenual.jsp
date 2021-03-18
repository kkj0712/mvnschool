<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid text-center">
<!-- 현재 로그인한 유저의 userno 정보 들고오기 -->
<input type="hidden" value="${userno}" id="userno" name="userno">

		<table class="table table-hover">
			<colgroup>
				<col width="10%"/>
				<col width="10%"/>
				<col width="30%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
			</colgroup>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" name="select_all" id="select_all" value="select_all"></th>
					<th scope="col">과목코드</th>
					<th scope="col">강의명</th>
					<th scope="col">교사명</th>
					<th scope="col">정원</th>
					<th scope="col">현재 수강인원</th>
				</tr>
			</thead>
			<tbody>
			
				<!-- 수강편람 테이블의 데이터가 바깥에서 돌아가는 중-->
				<c:forEach items="${sublist}" var="sublist" varStatus="st">
					
					<!-- if문의 break를 걸어줄 수 있는 flag=false 선언 -->
					<c:set var="loop_flag" value="false" />
						
					<!-- 내가 신청한 강의의 subno 리스트 -->
					<c:forEach items="${mysublist}" var="mysublist" varStatus="st">
						
						<!-- flag가 그대로 false 라면-->
						<c:if test="${not loop_flag }"> 
							
							<!-- 정원==현재 수강인원 or 수강편람테이블의 subno==내가 신청한 강의의 subno => 체크박스 disabled -->
							<c:if test="${sublist.cnt eq sublist.currentCnt || sublist.subno eq mysublist.subno }">
								<tr>
									<td><input type="checkbox" name="select_sub" id="select_sub" value="${sublist.subno}" disabled></td>
									<td>${sublist.subno}</td>
									<td><a onclick="window.open('subview.do?subno=${sublist.subno}','','width=600, height=400')" id="subview">${sublist.subname}</a></td>
									<td>${sublist.teachername}<input type="hidden" id="teacherno" name="teacherno" value="${sublist.teacherno}"></td>
									<td>${sublist.cnt}</td>
									<td>${sublist.currentCnt}</td>
									
									<!-- 반복문에서 빠져나오기 위해 flag는 true가 된다. mysublist의 c:forEach에서 벗어남 -->
									<c:set var="loop_flag" value="true" />
								</tr>
							</c:if>
						</c:if>	
					</c:forEach> <!-- 내가 신청한 강의의 subno 리스트 -->
					
					<!-- flag가 그대로 false 라면. 만약에 이 조건문이 없다면? loop_flag는 true가 되고, disabled 없이 sublist.subno를 찍는다.  -->
					<c:if test="${not loop_flag}">
						<tr>
							<td><input type="checkbox" name="select_sub" id="select_sub" value="${sublist.subno}"></td>
							<td>${sublist.subno}</td>
							<td><a onclick="window.open('subview.do?subno=${sublist.subno}','','width=600, height=400')" id="subview">${sublist.subname}</a></td>
							<td>${sublist.teachername}<input type="hidden" id="teacherno" name="teacherno" value="${sublist.teacherno}"></td>
							<td>${sublist.cnt}</td>
							<td>${sublist.currentCnt}</td>
						</tr>				
					</c:if>
							
				</c:forEach> <!-- 수강편람 테이블의 데이터-->
				
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
		
		<!-- 수강신청 버튼 -->
		<div class="text-right mr-3">
		 <div class="button">
		 	  <a class="btn btn-primary" id="attendBtn">수강신청</a>
			  </div>
		</div>
</div>
</body>
<script type="text/javascript" src="resources/js/subject.js" charset="utf-8"></script>
</html>