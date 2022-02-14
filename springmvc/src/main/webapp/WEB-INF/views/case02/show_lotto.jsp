<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
<meta charset="UTF-8">
<title>Show Lotto</title>
</head>
<body style="padding: 15px">
	
	<form class="pure-form" method="post" action="./add">
    <fieldset>
        <button type="submit" class="pure-button pure-button-primary">
        	電腦選號
        </button>
    </fieldset>
    </form>
   <p />
    回家作業
    <form class="pure-form" method="post" action="./count">
    <fieldset>
        <button type="submit" class="pure-button pure-button-primary">
        	統計每一顆號碼出現的次數
        </button>
    </fieldset>
    號碼:(出現次數)
   ${stat} 
  </form>
   <p />
    <table class="pure-table pure-table-bordered">
    	<thead>
    		<tr>
    			<th>index</th><th>樂透號碼</th><th>修改</th><th>刪除</th>
    		</tr>
    	</thead>
    	<tbody>
    		<c:forEach varStatus="status" var="lotto" items="${ lottos }">
    		<tr>
    			<td>${ status.index }</td>
    			<td>${ lotto }</td>
    			<td>
    				<button type="botton" 
    						onclick="window.location.href='./update/${status.index}';"
    						class="pure-button pure-button-primary">
        				修改
        			</button>
    			</td>
    			<td>
    				<button type="botton" 
    						onclick="window.location.href='./delete/${status.index}';"
    						class="pure-button pure-button-primary">
        				刪除
        			</button>
    			</td>
    		</tr>
    		</c:forEach>
    	</tbody>
    </table>
</body>
</html>