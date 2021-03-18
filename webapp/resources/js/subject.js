$(document).ready(function(){
	//전체선택 구현 (id=select_all을 누르면 모든 checkbox 타입의 input box의 checked를 true로 바꿔라)
	$("#select_all").on("click",function(){
		if($("input:checkbox[id = 'select_all']").prop("checked")){
			$("input[type=checkbox]:not(:disabled)").prop("checked", true);
		}else{
			$("input[type=checkbox]:not(:disabled)").prop("checked", false);
		}
	});
	
	//수강신청 버튼을 눌렀을 경우 체크된 row값을 가져와서 json배열에 담는다.
	$("#attendBtn").click(function(){
		
		var checkbox = $("input[name='select_sub']:checked");
		var userno = $("#userno").val();
		var jarr = [];
		
		$(checkbox).each(function(i){
			var tr = checkbox.parent().parent().eq(i);
			var td = tr.children();
		
			var subno = td.eq(1).text();
			var subname = td.eq(2).text();
			var teachername = td.eq(3).text();
			//var teacherno = $("#teacherno").val(); 각각 과목의 교사 고유키가 다른데 이렇게 접근하면 안됨.
			var teacherno = td.eq(3).children("input").val();
			jarr.push({"subno": subno, "subname": subname, "teachername": teachername, "teacherno": teacherno});
		});
		var jStr = JSON.stringify(jarr);
		
		if(jarr.length >= 1){
			$.ajax({
				url: "attend.do",
				type: "post",
				data: { 
					"jStr" : jStr,
					"userno" : userno
				},
				success: function(val){
					if(val>=1){
						alert("강의가 신청되었습니다. 수강신청목록에서 확인할 수 있습니다.");
						location.href="submenual_pre.do";
					}
				},
				error: function(e){
					alert("error: "+e);
				}
			});//ajax
		}//if
	});//attendBtn
	
	//강의입력 버튼을 눌렀을때 강의가 subject에 insert된다.
	$("#insertBtn").click(function(){
		//공백확인
		if($("#subname").val()==""){
			$("#subname_check").text("강의명을 입력하세요");
			$("#subname_check").css("color", "red");
			return false;
		}else{
			$("#subname_check").text("");
		}
		if($("#cnt").val()==""){
			$("#cnt_check").text("정원을 입력하세요");
			$("#cnt_check").css("color", "red");
			return false;
		}else{
			$("#cnt_check").text("");
		}
		if($("#submemo").val()==""){
			$("#submemo_check").text("정원을 입력하세요");
			$("#submemo_check").css("color", "red");
			return false;
		}else{
			$("#submemo_check").text("");
		}
		//강의입력
		$.ajax({
			url: "subInsertPro.do",
			type: "post",
			data: {
				"teachername" : $("#name").val(),
				"teacherno" : $("#userno").val(),
				"subname" : $("#subname").val(),
				"cnt" : $("#cnt").val(),
				"submemo": $("#submemo").val()
			},
			success: function(val){
				if(val==1){
					alert("강의가 입력 되었습니다."); location.href="/mvnschool/tch_subList_pre.do";
				}else{
					alert("강의입력 실패");
				}
			},
			error: function(e){
				alert(e);
			}
		}) //ajax
	});//insertBtn
	
	//resetBtn
	$("#resetBtn").click(function(){
		var result = confirm("취소할 경우 입력한 정보는 저장되지 않습니다.");
		if(!result){
			return false;
		}else{
			location.href="mypage.do";
		}
	});//resetBtn
	
	//강의수정하기 updateProBtn
	$("#updateProBtn").click(function(){
		//공백확인
		if($("#subname").val()==""){
			$("#subname_check").text("강의명을 입력하세요");
			$("#subname_check").css("color", "red");
			return false;
		}else{
			$("#subname_check").text("");
		}
		if($("#cnt").val()==""){
			$("#cnt_check").text("정원을 입력하세요");
			$("#cnt_check").css("color", "red");
			return false;
		}else{
			$("#cnt_check").text("");
		}
		if($("#submemo").val()==""){
			$("#submemo_check").text("정원을 입력하세요");
			$("#submemo_check").css("color", "red");
			return false;
		}else{
			$("#submemo_check").text("");
		}
		var subno = $("#subno").val();
		$.ajax({
			url: "updatePro.do",
			type: "post",
			data: {
				"subno": $("#subno").val(),
				"subname" : $("#subname").val(),
				"cnt": $("#cnt").val(),
				"submemo": $("#submemo").val()
			},
			success: function(val){
				alert("강의가 수정되었습니다."); location.href="/mvnschool/tch_subview.do?subno="+subno;
			},
			error: function(e){
				alert(e);
			}
		}); //ajax
	});//updateProBtn
	
	
	//강의 삭제하기
	$("#deleteBtn").click(function(){
		var result = confirm("정말 삭제하시겠습니까?");
		if(!result){
			return false;
		}else{
			$.ajax({
				url: "subDelete.do",
				type: "post",
				data: { 
					"subno": $("#subno").val() 
				},
				success: function(val){
					if(val >= 1){
						alert("강의가 삭제되었습니다."); location.href="/mvnschool/tch_subList_pre.do";
					}else{
						alert("강의삭제 실패");
					}
				},
				error: function(e){
					alert(e);
				}
			});//ajax
		} //if
	});//deleteBtn
	
});