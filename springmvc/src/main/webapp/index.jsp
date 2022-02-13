<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 Index <%=new Date() %>
 <ol>
    <li>
     <a href="./mvc/case01/hello/welcome">./mvc/case01/hello/welcome</a>
    </li>
    <li>
     <a href="bmi">./mvc/case01/hello/bmi/vic?h= & w=</a>
    </li>
    <li>
     <a href="加減">./mvc/case01/hello/calc/sub?y=30</a>
    </li>
 </ol>
</body>
</html>