<%@ page isErrorPage="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="spform"
	uri="http://www.springframework.org/tags/form"%>     
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
 function update(index) {
	 document.getElementById('person').action = '/springmvc/mvc/case04/person/' + index;
	 document.getElementById('person').submit();
}
</script>
<link rel="stylesheet" href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
<meta charset="UTF-8">
<title>Person Form index=${ index }</title>
<style type="text/css">
  .error{
      color: #FF0000
  }
</style>
</head>
<body style="padding: 15px">
	<spform:form class="pure-form" 
	             method="post" 
	             modelAttribute="person"
	             action="${pageContext.request.contextPath}/mvc/case04/person/${ index != null?index:'' }">
	<fieldset>
	  <legend>Person Form</legend>
	  <input type="hidden" id="_method" name="_method" value="${ _method }">
      姓名:
      <spform:input path="name"/>
	  <spform:errors path="name" cssClass="error"></spform:errors>
	  <p/>
	  年齡:
	  <spform:input path="age"/>
	  <spform:errors path="age" cssClass="error"></spform:errors>
	  <p/>
	  會員:
	  <spform:radiobutton path="member" value="true"/>會員
	  <spform:radiobutton path="member" value="false"/>非會員
	  <spform:errors path="member" cssClass="error"></spform:errors>
	  <p/>
	  生日:
	  <spform:input path="birth" type="date"/>
	  <spform:errors path="birth" cssClass="error"></spform:errors>
	  <p/>
	  <button type="submit" class="pure-button pure-button-primary" ${ _method=='POST'?'':'disabled' }>新增</button> 
      <button type="submit" class="pure-button pure-button-primary" ${ _method=='PUT'?'':'disabled' }>修改</button>
      <p/>
      <spform:errors path="*" cssClass="error"></spform:errors>	
	</fieldset>
	</spform:form>
	<table class="pure-table pure-table-bordered">
     <thead>
        <tr>
          <th>index</th>
          <th>姓名</th>
          <th>年齡</th>
          <th>會員</th>
          <th>生日</th>
        </tr>
     </thead>
     <tbody>
        <c:forEach varStatus="status" var="person" items="${ people }">
        <tr>
          <td>
             <a href="${pageContext.request.contextPath}/mvc/case04/person/${status.index}">${status.index}</a>
          </td>
          <td>${ person.name }</td>
          <td>${ person.age }</td>
          <td>${ person.member }</td>
          <td>${ person.birth }</td>
        </tr>
        </c:forEach>
     </tbody>
    </table>
</body>
</html>