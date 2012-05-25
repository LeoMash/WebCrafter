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
function validateNewItemForm()
{
    if(document.formAddNewItem.id.value=="")
    {
      alert(<spring:message code="label.error_nameEmpty" />);
      document.formAddNewItem.id.focus();
      return false;
    } else if(document.formAddNewItem.cost.value=="")
    {
      alert(<spring:message code="label.error_nameEmpty" />);
      document.formAddNewItem.id.focus();
      return false;
    }

}

function validateNewRecipeForm()
{
    if(document.formAddNewRecipe.id.value=="")
    {
      alert(<spring:message code="label.error_nameEmpty" />);
      document.formAddNewRecipe.id.focus();
      return false;
    } else if(document.formAddNewRecipe.result.value=="0") {
      alert(<spring:message code="label.error_resultNotSelected" />);
      document.formAddNewRecipe.result.focus();
      return false;
    } else if(document.formAddNewRecipe.ingredients.value=="") {
      alert(<spring:message code="label.error_ingredientsNotSelected" />);
      document.formAddNewRecipe.result.focus();
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
                <td><a href="deleteItemTemplate.do/${itemTemplate.id}"><spring:message code="label.delete" /></a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<%-- add new template form --%>
<h3><spring:message code="label.additemtemplate" /></h3>
<form:form name="formAddNewItem" action="addItemTemplate.do" method="post"
           modelAttribute="newItemTemplate" onSubmit="return validateNewItemForm()">
<table>
    <tr>
        <td><form:label path="id">Имя</form:label>
        <td><form:input name="id" path="id" value="${newItemTemplate.id}"/></td>
    </tr>
    <tr>
        <td><form:label path="cost">Цена</form:label>
        <td><form:input name="cost" path="cost" value="${newItemTemplate.cost}"/></td>
    </tr>
    <tr>
            <td><input type="submit" value="<spring:message code="label.add"/>" /></td>
    </tr>
</table>
</form:form>

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
                <td><a href="deleteRecipe.do/${recipe.id}"><spring:message code="label.delete" /></a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>

<%-- add new recipe form --%>
<h3><spring:message code="label.addrecipe" /></h3>
<form:form  name="formAddNewRecipe" action="addRecipe.do" method="post"
            modelAttribute="newRecipe" onSubmit="return validateNewRecipeForm()">
<table>
    <tr>
        <td><form:label path="id" value="${newRecipe.id}">
            <spring:message code="label.name" />
        </form:label><td>
        <form:input name="id" path="id" /></td>
    </tr>
    <tr>
        <td>
            <form:label path="ingredients">
                <spring:message code="label.ingredients" />
            </form:label>
        </td>
        <td>
             <form:select name="ingredients" path="ingredients" multiple="true" items="${itemTemplateList}" itemValue="id" itemLabel="id" />
        </td>
    </tr>


<%--
    <c:if test="${!empty newRecipe.ingredients}">
        <c:forEach items="${newRecipe.ingredients}" var="ingredient" varStatus="status">
            <c:if test="${not status.last}">
                <tr>
                    <td colspan="2">
                         <form:select path="ingredients[${status.index}]">
                             <form:option value="${ingredient.id}" label="${ingredient.id}" />
                             <form:options items="${itemTemplateList}" itemValue="id" itemLabel="id" />
                         </form:select>
                    </td>
                    <td><a href=""><spring:message code="label.delete" /></a></td>
                </tr>
            </c:if>
        </c:forEach>
    </c:if>
    <tr>
        <td colspan="2">
             <form:select path="ingredients[${fn:length(ingredients)}]">
                 <form:option value="0" label="Select" />
                 <form:options items="${itemTemplateList}" itemValue="id" itemLabel="id" />
             </form:select>
        </td>
    </tr>
--%>

    <tr>
        <td><form:label path="result">
            <spring:message code="label.result" />
        </form:label></td>
        <td>
             <form:select name="result" path="result">
                 <form:option value="0" label="Select" />
                 <form:options items="${itemTemplateList}" itemValue="id" itemLabel="id" />
             </form:select>
        </td>
    </tr>

    <tr>
        <td><input type="submit" value="<spring:message code="label.add"/>" /></td>
    <tr>
</table>
</form:form>


</body>
</html>