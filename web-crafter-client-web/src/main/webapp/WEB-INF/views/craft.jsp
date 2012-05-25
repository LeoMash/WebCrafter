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

<a href="<c:url value="/index" />">
	<spring:message code="label.back" />
</a><br/>


<h2><spring:message code="label.title" /></h2>
<h2>Крафтинг</h2>
Деньги: ${userCrafter.amount}

<h3><spring:message code="label.itemtemplates" /></h3>
<c:if test="${!empty userCrafter.items}">
	<table class="data">
	    <tr>
	        <th><spring:message code="label.name" /></th>
	        <th>Количество</th>
	    </tr>
		<c:forEach items="${userCrafter.items}" var="itemInfo" >
            <tr>
				<td>${itemInfo.key}</td>
				<td>${itemInfo.value}</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<h3><spring:message code="label.recipes" /></h3>
<form:form action="craft.do" method="post" modelAttribute="recipeId">
     <form:select name="recipeId" path="recipeId">
         <form:options items="${recipeList}" itemValue="id" itemLabel="id" />
     </form:select>

<%--
     <select >
		<c:forEach items="${recipeList}" var="recipe">
            <option value="${recipe.id}" label="${recipe.id}"/>
		</c:forEach>
     </select>
--%>
    <input type="submit" value="Применить рецепт" />
</form:form>


<h3>Магазин</h3>
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
                <td><a href="sell.do/${itemTemplate.id}">Продать</a></td>
                <td><a href="buy.do/${itemTemplate.id}">Купить</a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>


<form:form action="resetItems.do" method="post" modelAttribute="userCrafter">
    <form:input type="hidden" path="id" value="${userCrafter.id}" />
    <input type="submit" value="Сброс" />
</form:form>

</body>
</html>