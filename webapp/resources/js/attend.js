$(document).ready(function(){
	//전체선택 구현 (id=select_all을 누르면 모든 checkbox 타입의 input box의 checked를 true로 바꿔라)
	$("#select_all").on("click",function(){
		if($("input:checkbox[id = 'select_all']").prop("checked")){
			$("input[type=checkbox]:not(:disabled)").prop("checked", true);
		}else{
			$("input[type=checkbox]:not(:disabled)").prop("checked", false);
		}
	});
	
	//수강취소 버튼을 눌렀을 경우 체크박스의 value 값을 배열에 담고 json형태로 바꿔서 넘긴다.
	$("#cancelBtn").click(function(){
		var jarr = [];
		
		$("input[name='select_att']:checked").each(function(){
			var attno = $(this).val();
			
			jarr.push({"attno": attno});
		});
		var jStr = JSON.stringify(jarr);
		
		if(jarr.length >= 1){
			$.ajax({
				url: "attCancel.do",
				type: "post",
				data: { 
					"jStr" : jStr
				},
				success: function(val){
					if(val>=1){
						alert("수강신청이 취소되었습니다.");
						location.href="myattend_pre.do";
					}
				},
				error: function(e){
					alert("error: "+e);
				}
			});//ajax
		}//if
	});//attendBtn
	
	
	//승인버튼을 눌렀을경우
	$("#proveBtn").click(function(){
		var checkbox = $("input[name='select_stu']:checked");
		var jarr = [];
		
		$(checkbox).each(function(i){
			var tr = checkbox.parent().parent().eq(i);
			var td = tr.children();
			
			var stuno = $(this).val();
			var subno = td.eq(2).text();
			
			jarr.push({"stuno": stuno, "subno": subno});
		});
		var jStr = JSON.stringify(jarr);

		if(jarr.length >= 1){
			$.ajax({
				url: "attProve.do",
				type: "post",
				data: { 
					"jStr" : jStr
				},
				success: function(val){
					console.log(val);
					if(val>=1){
						alert("수강신청이 승인되었습니다.");
						location.href="attendCtrl_pre.do";
					}
				},
				error: function(e){
					alert("error: "+e);
				}
			});//ajax
		}//if
	});//proveBtn
	
	//반려버튼을 눌렀을경우
	$("#rejectBtn").click(function(){
		var checkbox = $("input[name='select_stu']:checked");
		var jarr = [];
		
		$(checkbox).each(function(i){
			var tr = checkbox.parent().parent().eq(i);
			var td = tr.children();
			
			var stuno = $(this).val();
			var subno = td.eq(2).text();
			
			jarr.push({"stuno": stuno, "subno": subno});
		});
		var jStr = JSON.stringify(jarr);

		if(jarr.length >= 1){
			$.ajax({
				url: "attReject.do",
				type: "post",
				data: { 
					"jStr" : jStr
				},
				success: function(val){
					console.log(val);
					if(val>=1){
						alert("수강신청이 반려되었습니다.");
						location.href="attendCtrl_pre.do";
					}
				},
				error: function(e){
					alert("error: "+e);
				}
			});//ajax
		}//if
	});//rejectBtn
	
});