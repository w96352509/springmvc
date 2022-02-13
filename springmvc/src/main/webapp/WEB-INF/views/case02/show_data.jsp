<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Show time</title>
</head>
<body>
	name = ${ names }    <p />
	<c:forEach varStatus="status" var="name" items="${names}">
	   ${status.index} ${name} <br/>
	</c:forEach>
	<hr />
	fruits = ${ fruits } <p />
	<c:forEach varStatus="status" var="fruit" items="${fruits}">
	   ${status.index} ${fruit} ${fruit.key} ${fruit.value} <br/>
	</c:forEach>
</body>
</html>