<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="ru"
      xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width; initial-scale=1; maximum-scale=1"/>
    <meta name="description" content="Интернет-магазин dj оборудования"/>
    <meta name="author" content="tkaczenko"/>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resources/css/styles.css"/>"/>

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <%--jQuery--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <script src="/resources/js/scripts.js"></script>

    <title>Интернет-магазин DJ</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<div class="container">
    <div class="page-header">
        <jsp:include page="_categories-menu.jsp"/>
        <jsp:include page="_category-nav.jsp"/>
    </div>
    <form:form method="post" modelAttribute="productForm"
               action="${pageContext.request.contextPath}/edit/product/${product.sku}"
               target="_top">
        <table>
            <tr>
                <td><form:input path="sku" type="text" cssClass="hidden"/></td>
            </tr>
            <tr>
                <td><form:label path="name">Имя</form:label></td>
                <td><form:input path="name" type="text"
                                placeholder="Наименование" required="required"/></td>
                <td><form:errors path="name"/></td>
            </tr>
            <tr>
                <td><form:label path="price">Цена</form:label></td>
                <td><form:input path="price" type="number"
                                placeholder="Цена" required="required"/></td>
                <td><form:errors path="price"/></td>
            </tr>
            <tr>
                <td><form:label path="amount">Количество</form:label></td>
                <td><form:input path="amount" type="number"
                                placeholder="Количество" required="required"/></td>
                <td><form:errors path="amount"/></td>
            </tr>
            <tr>
                <td><form:label path="description">Описание</form:label></td>
                <td><form:textarea path="description" rows="5" cols="30"/></td>
                <td><form:errors path="description"/></td>
            </tr>
            <tr>
                <td><form:label path="categories">Категории</form:label></td>
                <td>
                    <form:select path="categories" multiple="true">
                        <c:forEach var="item" items="${cats}">
                            <c:choose>
                                <c:when test="${productCats.contains(item)}">
                                    <form:option value="${item.id}" selected="selected">${item.name}</form:option>
                                </c:when>
                                <c:otherwise>
                                    <form:option value="${item.id}">${item.name}</form:option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>
                </td>
                <td><form:errors path="categories"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Обновить"/></td>
            </tr>
        </table>
    </form:form>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>