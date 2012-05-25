<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
	<title><spring:message code="label.title" /></title>

<script>
function validateForm()
{
    if(document.form.name.value=="")
    {
      alert(<spring:message code="label.error_nameEmpty" />);
      document.formAddNewItem.id.focus();
      return false;
    }
}

</script>
</head>
<body>

<a href="<c:url value="/index" />">
	<spring:message code="label.back" />
</a><br/>

<h2><spring:message code="label.title" /></h2>

<h3><spring:message code="label.registration" /></h3>

<%-- add new user form --%>
<form:form name="form" action="register.do" method="post"
           modelAttribute="newUser" onSubmit="return validateForm()">
<table>
    <tr>
        <td><form:input name="name" path="userName"/></td>
        <td><form:password name="password" path="passwordHash"/></td>
        <td><input type="submit" value="<spring:message code="label.add"/>" /></td>
    </tr>
</table>
</form:form>

</body>
</html>