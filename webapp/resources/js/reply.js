$(document).ready(function(){
	//문서 열자마자 댓글 리스트 부르기
	$.getJSON("replyList.do",
			{bnum: $("#bnum").val()},
			function(d){
				var htmlStr="<table class='table table-striped'>";

				$.each(d.jarr, function(key,val){
					htmlStr+="<tr>";
					htmlStr+="<th>"+val.name+"</th>";
					htmlStr+="<td>"+val.msg+"</td>";
					htmlStr+="<td>"+val.regdate+"</td>";
					
					if(val.userno == $("#sessionUserno").val()){
						var text = "'"+val.rnum+"'";
						htmlStr+='<td><a href="javascript:replyDel('+text+')">삭제</a><br>';
					}else{
						htmlStr+="<td></td>";
					}
					
					htmlStr+="</tr>";
				})
					htmlStr+="</table>";
					$("#replyList").html(htmlStr);
	}) //getJSON
	
	//댓글 버튼을 누르면 댓글 입력
	$("#replyBtn").click(function(){
		var msg = $("#msg").val();
		var bnum = $("#bnum").val();
		var userno = $("#sessionUserno").val();
		
		//공백확인
		if(msg == ""){
			alert("댓글 작성");
			$("#msg").focus();
			return false;
		}else{
			$.ajax({
				url: "replyInsert.do",
				type: "post",
				data: {
					"msg": msg, "bnum": bnum, "userno": userno
				},
				success: function(d){
					$.getJSON("replyList.do",
							{bnum: $("#bnum").val()},
							function(d){
								var htmlStr="<table class='table table-striped'>";
								$.each(d.jarr, function(key,val){
									htmlStr+="<tr>";
									htmlStr+="<th>"+val.name+"</th>";
									htmlStr+="<td>"+val.msg+"</td>";
									htmlStr+="<td>"+val.regdate+"</td>";
									
									if(val.userno == $("#sessionUserno").val()){
										var text = "'"+val.rnum+"'";
										htmlStr+='<td><a href="javascript:replyDel('+text+')">삭제</a><br>';
									}else{
										htmlStr+="<td></td>";
									}
									
									htmlStr+="</tr>";
								})
									htmlStr+="</table>";
									$("#replyList").html(htmlStr);
					}) //getJSON
				},
				error: function(e){
					alert(e);
				}
			})//ajax
		}
	}); //replyBtn
	
});//document

//댓글 삭제
function replyDel(rnum){
	var bnum = $("#bnum").val();
	
	if(confirm("댓글을 삭제하시겠습니까?")){
		$.ajax({
			url: "replyDelete.do",
			type: "post",
			data: {
				"rnum": rnum, "bnum": bnum
			},
			success: function(d){
				$.getJSON("replyList.do",
						{bnum: $("#bnum").val()},
						function(d){
							var htmlStr="<table class='table table-striped'>";
							$.each(d.jarr, function(key,val){
								htmlStr+="<tr>";
								htmlStr+="<th>"+val.name+"</th>";
								htmlStr+="<td>"+val.msg+"</td>";
								htmlStr+="<td>"+val.regdate+"</td>";
								
								if(val.userno == $("#sessionUserno").val()){
									var text = "'"+val.rnum+"'";
									htmlStr+='<td><a href="javascript:replyDel('+text+')">삭제</a><br>';
								}else{
									htmlStr+="<td></td>";
								}
								
								htmlStr+="</tr>";
							})
								htmlStr+="</table>";
								$("#replyList").html(htmlStr);
				}) //getJSON
			},
			error: function(e){
				alert(e);
			}
		})//ajax
	}
}