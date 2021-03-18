<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<form>
	<!-- 이 글을 쓴 User의 userno -->
	<input type="hidden" id="userno" name="userno"
		value="${board.userno}">
	<!-- 현재 로그인한 User의 userno -->
	<input type="hidden" id="sessionUserno" name="sessionUserno"
		value="${sessionScope.userno}">
	<!-- 현재 게시글의 bnum -->
	<input type="hidden" id="bnum" name="bnum" value="${board.bnum}">
	
	<br />
	<div class="container-fluid">
		
		<a onclick="window.history.back()" id="a-back">&#60;&#60; 돌아가기 </a><br />
		<br />
	
		<div class="card">

			<!-- 제목, 작성자 -->
			<div class="card-header pt-3">
				<div class="row align-items-center justify-content-between">
					<div class="col-8">
						<h5 class="card-title board--title">${board.title}</h5>
					</div>
					<div class="col-4 text-right">
						<h6 class="text-muted">${board.name}</h6>
					</div>
				</div>
			</div>

			<!-- 작성일, 조회수 -->
			<div class="card-body">
				<div class="text-right text-muted m-0">

					<fmt:parseDate value="${board.regdate}"
						pattern="yyyy-MM-dd HH:mm:ss" var="parseRegdate" type="both" />
					<fmt:formatDate var="dateParse" pattern="yyyy-MM-dd HH:mm"
						value="${parseRegdate}" />


					<ul class="boardUl">
						<li class="d-flex justify-content-between">
							<div class="d-flex">
								<p>
									<small>글번호 ${board.bnum} </small> <small>조회수
										${board.hit}</small>
								</p>
							</div>
							<div>
								<p>
									<small>${dateParse}</small>
								</p>
							</div>
						</li>
					</ul>
				</div>
				${board.content} <br />

				<c:if test="${board.fileupload ne null}">
					<div>
						첨부파일 <a href="fileDownload.do?fileName=${board.fileupload}">${board.fileupload}</a>
					</div>
				</c:if>

			</div>
			
			<br />
			
			<!-- 수정, 삭제 버튼 영역 : userno 대조 -->
			<c:if test="${sessionScope.userno == board.userno}">
				<div class="text-right">
					<a id="boardUpdateForm" 
						href="/mvnschool/boardUpdateForm.do?bnum=${board.bnum}"
						class="btn btn-outline-secondary btn-sm">글수정</a> 
					<a onclick="javascript:boardDel('${board.bnum}')"
						class="btn btn-outline-danger btn-sm">삭제</a>
				</div>
			</c:if>

		</div> <!-- 게시글 본문 끝-->
		
		<!-- 댓글 목록 -->
		<div id="replyList"></div>
		
		<br />
		
		<!-- 댓글 입력 -->
		<div class="text-right">
			<textarea id="msg" class="form-control"></textarea>
			<a id="replyBtn" class="btn btn-outline-primary btn-sm">댓글</a>
		</div>
	</div>
</form>
<script type="text/javascript" src="resources/js/board.js" charset="utf-8"></script>
<script type="text/javascript" src="resources/js/reply.js" charset="utf-8"></script>