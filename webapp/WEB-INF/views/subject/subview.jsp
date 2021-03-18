<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>강의 상세정보</title>
</head>
<body>
<div>
	과목코드: ${subject.subno}
	강의명: ${subject.subname}
	교사명: ${subject.teachername}<br/>
	강의정보: ${subject.submemo}

</div>
</body>
</html>