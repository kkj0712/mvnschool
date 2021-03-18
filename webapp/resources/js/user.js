$(document).ready(function(){
	var emailExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	var phoneExp= /^[0-9]{3}[0-9]{4}[0-9]{4}$/;
	
	//전체선택 구현 (id=select_all을 누르면 모든 checkbox 타입의 input box의 checked를 true로 바꿔라)
	$("#select_all").on("click",function(){
		if($("input:checkbox[id = 'select_all']").prop("checked")){
			$("input[type=checkbox]:not(:disabled)").prop("checked", true);
		}else{
			$("input[type=checkbox]:not(:disabled)").prop("checked", false);
		}
	});
	
	//아이디 실시간 중복체크
	$("#userid").blur(function(){
		//아이디가 공백일경우
		if($("#userid").val()==""){
			$("#id_check").text("아이디를 입력하세요");
			$("#id_check").css("color", "red");
		//공백이 아닐경우 중복체크
		}else{
			$.ajax({
				url: "idcheck.do",
				type: "post",
				data: {
					userid: $("#userid").val()
				},
				success: function(val){
					if(val.trim() == "no"){
						$("#id_check").text("사용중인 아이디 입니다");
						$("#id_check").css("color", "red");
						$("#userid").focus();
					}else{
						$("#id_check").text("사용가능한 아이디");
						$("#id_check").css("color", "blue");
					}
				},
				error: function(e){
					$("#id_check").text("체크불가");
					$("#id_check").css("color", "red");
					alert(e);
					console.log(e);
				}
			}) //ajax
		}
	}); //아이디 실시간 중복체크
	
	//회원가입 버튼 클릭
	$("#send").on("click", function(){
		//아이디 공백
		if($("#userid").val()==""){
			$("#id_check").text("이름을 입력하세요");
			$("#id_check").css("color", "red");
			return false;
		}else{
			$("#id_check").text("");
		}

		//이름 공백
		if($("#name").val()==""){
			$("#name_check").text("이름을 입력하세요");
			$("#name_check").css("color", "red");
			return false;
		}else{
			$("#name_check").text("");
		}
		
		//비밀번호 공백, 6자리 미만
		if($("#password").val()=="" || $("#password_check").val()=="" || $("#password").val().length<6 || $("#password_check").val().length<6){
			$("#pwd_check").text("비밀번호를 정확하게 입력하세요");
			$("#pwd_check").css("color", "red");
			return false;
		}else{
			$("#pwd_check").text("");
		}
		
		//비밀번호 불일치
		if($("#password").val() != $("#password_check").val()){
			$("#pwd_check").text("비밀번호가 일치하지 않습니다.");
			$("#pwd_check").css("color", "red");
			return false;
		}else{
			$("#pwd_check").text("");
		}
		
		//이메일 공백, 정규식 확인
		if($("#email").val()=="" || !$("#email").val().match(emailExp)){
			$("#email_check").text("이메일을 정확하게 입력하세요");
			$("#email_check").css("color", "red");
			return false;
		}else{
			$("#email_check").text("");
		}
		
		//전화번호 공백, 정규식 확인
		if($("#phone").val()=="" || !$("#phone").val().match(phoneExp)){
			$("#phone_check").text("전화번호를 정확하게 입력하세요");
			$("#phone_check").css("color", "red");
			return false;
		}else{
			$("#phone_check").text("");
		}

		//구분에 '교사'로 되어있을때 alert창 띄우기
		if($("#role option:selected").val()=="2"){
			alert("교사는 관리자 승인 후 회원가입 완료");
		}
		
		//회원가입 구현
		$.ajax({
			url: "insert.do",
			type: "post",
			data: {
				"userid": $("#userid").val(),
				"name": $("#name").val(),
				"password": $("#password").val(),
				"email": $("#email").val(),
				"address": $("#address").val(),
				"phone": $("#phone").val(),
				"role" : $("#role").val()
			},
			success: function(result){
				if(result == 1){
					alert("회원가입 되었습니다."); location.href="/";
				}else{
					alert("회원가입 실패");
				}
			},
			error: function(e){
				alert(e);
			}
		}) //ajax 회원가입		
	}); //회원가입 버튼 클릭
	
	//로그인 버튼 클릭
	$("#loginBtn").on("click",function(){
		
		//아이디 공백체크
		if($("#login_userid").val()==""){
			$("#login_id_check").text("아이디를 입력하세요");
			$("#login_id_check").css("color", "red");
			return false;
		}else{
			$("#login_id_check").text("");
		}
		
		//비밀번호 공백체크
		if($("#login_password").val()==""){
			$("#login_pwd_check").text("비밀번호를 입력하세요");
			$("#login_pwd_check").css("color", "red");
			return false;
		}else{
			$("#login_pwd_check").text("");
		}
		
		//비밀번호 6자리 미만
		if($("#login_password").val().length<6){
			$("#login_pwd_check").text("비밀번호는 6자리 이상이어야 합니다.");
			$("#login_pwd_check").css("color", "red");
			return false;
		}else{
			$("#login_pwd_check").text("");
		}
		
		//로그인 구현
		$.ajax({
			url: "login.do",
			type: "post",
			data: {
				"userid": $("#login_userid").val(),
				"password": $("#login_password").val(),
			},
			success: function(result){
				switch(result){
				case 1 : alert("학생 로그인 성공"); location.href="mypage.do"; break;
				case 2 : alert("교사 로그인 성공"); location.href="mypage.do"; break;
				case 3 : alert("관리자 로그인 성공"); location.href="mypage.do"; break;
				case 4 : alert("비밀번호 오류"); break;
				case -1 : alert("회원이 아닙니다"); location.href=""; break;
				case 9 : alert("교사 회원신청은 관리자의 승인 후 이용할 수 있습니다."); break;
				default: alert(result);
				}
			},
			error: function(e){
				alert(e);
			}
		}) //ajax 로그인
	}); //로그인 버튼 클릭
	
	//마이페이지 - 수정(저장 버튼)
	$("#my_save").on("click",function(){
		//이메일 공백체크
		if($("#my_email").val()==""){
			$("#my_email_check").text("이메일을 입력하세요");
			$("#my_email_check").css("color", "red");
			return false;
		}else{
			$("#my_email_check").text("");
		}
		
		//수정하기
		$.ajax({
			url: "mypage_update.do",
			type: "post",
			data: {
				"email": $("#my_email").val(),
				"address": $("#my_address").val(),
				"phone": $("#my_phone").val()
			},
			success: function(result){
				if(result == 1){
					alert("회원정보 저장 완료"); location.href="mypage.do";
				}else{
					alert("수정 실패");
				}
			},
			error: function(e){
				alert(e);
			}
		}) //ajax 회원정보수정	
	}); //마이페이지 - 수정(저장 버튼)
	
	
	//비밀번호 변경
	$("#pwd_btn").on("click", function(){
		
		//비밀번호 공백, 6자리 미만
		if($("#change_pwd").val()=="" || $("#change_pwd_check").val()=="" || $("#change_pwd").val().length<6 || $("#change_pwd_check").val().length<6){
			$("#pwd_confirm").text("비밀번호를 정확하게 입력하세요");
			$("#pwd_confirm").css("color", "red");
			return false;
		}else{
			$("#pwd_confirm").text("");
		}
		
		//비밀번호 불일치
		if($("#change_pwd").val() != $("#change_pwd_check").val()){
			$("#pwd_confirm").text("비밀번호가 일치하지 않습니다.");
			$("#pwd_confirm").css("color", "red");
			return false;
		}else{
			$("#pwd_confirm").text("");
		}
		
		//변경하기
		$.ajax({
			url: "pwd_update.do",
			type: "post",
			data: {
				"password": $("#change_pwd").val(),
			},
			success: function(result){
				if(result == 1){
					alert("비밀번호 변경 완료"); location.href="mypage.do";
				}else{
					alert("변경 실패");
				}
			},
			error: function(e){
				alert(e);
			}
		})//변경하기
		
	}); //비밀번호 변경
	
	//관리자 페이지에서 회원정보 수정버튼 눌렀을때
	$("#userUpdateBtn").click(function(){
		//공백체크
		if($("#user_email").val()==""){
			$("#user_email_check").text("이메일을 입력하세요");
			$("#user_email_check").css("color", "red");
			return false;
		}else{
			$("#my_email_check").text("");
		}
		
		if($("#user_address").val()==""){
			$("#user_address_check").text("주소를 입력하세요");
			$("#user_address_check").css("color", "red");
			return false;
		}else{
			$("#user_address_check").text("");
		}

		if($("#user_phone").val()=="" || !$("#user_phone").val().match(phoneExp)){
			$("#user_phone_check").text("전화번호를 입력하세요");
			$("#user_phone_check").css("color", "red");
			return false;
		}else{
			$("#user_phone_check").text("");
		}

		//회원정보 수정하기
		$.ajax({
			url: "userUpdate.do",
			type: "post",
			data: {
				"userno": $("#user_userno").val(),
				"email": $("#user_email").val(),
				"address": $("#user_address").val(),
				"phone": $("#user_phone").val()
			},
			success: function(result){
				if(result == 1){
					alert("회원정보 수정 완료");
				}else{
					alert("변경 실패");
				}
			},
			error: function(e){
				alert(e);
			}
		})//회원정보 수정 ajax
	}); //userUpdateBtn
	
	//교사 승인 버튼을 눌렀을경우 체크박스의 value(user의 userno) 값을 배열에 담고 json형태로 바꿔서 넘긴다.
	$("#tchProveBtn").click(function(){
		var checkbox = $("input[name='select_tch']:checked");
		var jarr = [];
		
		$(checkbox).each(function(i){
			var tr = checkbox.parent().parent().eq(i);
			var td = tr.children();
			
			var userno = $(this).val();
			var email = td.eq(6).text();
			
			jarr.push({"userno": userno, "email": email});
		});
		var jStr = JSON.stringify(jarr);
		
		if(jarr.length >= 1){
			$.ajax({
				url: "tchProve.do",
				type: "post",
				data: {
					"jStr": jStr
				},
				success: function(val){
					if(val>=1){
						alert("교사 승인이 완료되었습니다.");
						location.href="tchWaitList_pre.do";
					}
				}
			})
		}
	}); //tchProveBtn
	
}); //document ready