<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
	<title><spring:message code="label.title" /></title>
</head>
<body>

<h2><spring:message code="label.title" /></h2>
<spring:message code="label.logged"/>
<sec:authorize access="isAuthenticated()">
    <strong><sec:authentication property="principal.username"/></strong><br/>
    <a href="<c:url value="/logout" />">
        <spring:message code="label.logout" />
    </a><br/></br>

    <h3>
        <a href="<c:url value="/craft" />">
            Крафтинг!!!
        </a>
    </h3>

</sec:authorize>
<sec:authorize access="isAnonymous()">
    <strong><spring:message code="label.guest"/></strong><br/>
    <a href="<c:url value="/login.jsp" />">
        <spring:message code="label.trylogin" />
    </a></br>
    <a href="<c:url value="/register" />">
        <spring:message code="label.registration" />
    </a>
</sec:authorize>
<br/><br/>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <a href="<c:url value="/admin" />">
        <spring:message code="label.manager" />
    </a>
</sec:authorize>

<h3><spring:message code="label.itemtemplates" /></h3>
<c:if test="${!empty itemTemplateList}">
	<table class="data">
	    <tr>
	        <th><spring:message code="label.name" /></th>
	        <th>Цена</th>

	    </tr>
		<c:forEach items="${itemTemplateList}" var="itemTemplate" >
            <tr>
				<td>${itemTemplate.id}</td>
				<td>${itemTemplate.cost}</td>
			</tr>
		</c:forEach>
	</table>
</c:if>

<h3><spring:message code="label.recipes" /></h3>
<c:if test="${!empty recipeList}">
	<table class="data">
		<c:forEach items="${recipeList}" var="recipe">
			<tr>
				<td>${recipe.id}</td>
				<td>:</td>
                <c:forEach items="${recipe.ingredients}" var="ingredient" varStatus="status">
                    <td>${ingredient.id}${not status.last ? ' + ' : ''}</td>
                </c:forEach>
                <td> -> </td>
                <td>${recipe.result.id}</td>
			</tr>
		</c:forEach>
	</table>
</c:if>

</body>
</html>