<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test='${param.color == "red" }'>
			<h1 style='color: #ff0000'>Hello World</h1>	
		</c:when>
		<c:when test='${param.color == "green" }'>
			<h1 style='color: #00ff00'>Hello World</h1>
		</c:when>
		<c:when test='${param.color == "blue" }'>
			<h1 style='color: #0000ff'>Hello World</h1>
		</c:when>
		<c:otherwise>
			<h1 style='color: #000000'>Hello World</h1>	
		</c:otherwise>		
	</c:choose>
</body>
</html>