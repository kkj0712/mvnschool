$(document).ready(function(){
	//작성버튼을 눌렀을 경우
	$("#sendBtn").click(function(){
		//공백체크
		if($("#title").val()==""){
			$("#title_check").text("제목을 입력하세요");
			$("#title_check").css("color", "red");
			return false;
		}else{
			$("#title_check").text("");
		}
		if($("#content").val()==""){
			$("#content_check").text("내용을 입력하세요");
			$("#content_check").css("color", "red");
			return false;
		}else{
			$("#content_check").text("");
		}
		
		var formData = new FormData($("#frm")[0]);
		
		$.ajax({
			url: "boardInsert.do",
			type: "post",
			enctype: "multipart/form-data",
			data: formData,
			processData: false,
			contentType: false,
			success: function(val){
				if(val == 1){
					alert("글을 입력하였습니다."); location.href="board_pre.do";
				}else{
					alert("전송 실패");
				}
			},
			error: function(e){
				alert(e);
			}
		})//ajax
	}); //sendBtn
	
	//취소버튼을 눌렀을 경우
	$("#resetBtn").click(function(){
		var flag = confirm("작성한 글은 저장되지 않습니다.");
		if(flag) location.href="/mvnschool/board_pre.do";
	}); //resetBtn
	
	//파일 삭제 뱃지버튼
	$("#fileDelBtn").click(function(){
		var flag = confirm("첨부 파일을 삭제하시겠습니까?");
		if(flag){
			var formData = new FormData($("#frm")[0]);
			var bnum = $("#bnum").val();
			var fileupload = $("#fileupload").val();
			
			$.ajax({
				url: "fileDelete.do",
				type: "post",
				enctype: "multipart/form-data",
				data: formData,
				processData: false,
				contentType: false,
				success: function(val){
					if(val == 1){
						location.href="boardUpdateForm.do?bnum="+bnum;
					}
				},
				error: function(e){
					alert(e);
				}
			})//ajax
		}
	}); //fileDelBtn
	
	//글수정 버튼
	$("#boardUpdateBtn").click(function(){
		//공백체크
		if($("#title").val()==""){
			$("#title_check").text("제목을 입력하세요");
			$("#title_check").css("color", "red");
			return false;
		}else{
			$("#title_check").text("");
		}
		if($("#content").val()==""){
			$("#content_check").text("내용을 입력하세요");
			$("#content_check").css("color", "red");
			return false;
		}else{
			$("#content_check").text("");
		}
		
		var formData = new FormData($("#frm")[0]);
		var bnum = $("#bnum").val();
		
		$.ajax({
			url: "boardUpdatePro.do",
			type: "post",
			enctype: "multipart/form-data",
			data: formData,
			processData: false,
			contentType: false,
			success: function(val){
				if(val == 1){
					alert("글을 수정하였습니다."); location.href="boardView.do?bnum="+bnum;
				}else{
					alert("전송 실패");
				}
			},
			error: function(e){
				alert(e);
			}
		})//ajax
	}); //boardUpdateForm
});//document

//글삭제 버튼 (파일도 삭제)
function boardDel(bnum){
	var flag = confirm("게시글을 삭제하시겠습니까?");
	if(flag){
		$.ajax({
			url: "boardDelete.do",
			type: "post",
			data: {
				"bnum": bnum
			},
			success: function(val){
				if(val == 1){
					alert("삭제 완료"); location.href="board_pre.do";
				}
			},
			error: function(e){
				alert(e);
			}
		})
	}
}