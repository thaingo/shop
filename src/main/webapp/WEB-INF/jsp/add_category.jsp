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
    <h2>Добавить категорию</h2>
    <h3>${message}</h3>
    <c:url var="addCategory" value="/admin/add/category"/>
    <form:form method="post" modelAttribute="categoryForm" action="${addCategory}" target="_top">
        <div class="form-group">
            <form:input path="id" type="text" cssClass="hidden"/>
            <form:label path="name">Имя</form:label>
            <form:input path="name" type="text" class="form-control"
                        placeholder="Наименование" required="required"/>
            <form:errors path="name"/>
            <br>
            <form:input path="url" type="text" cssClass="hidden"/>
            <br>
            <form:label path="description">Описание</form:label>
            <form:textarea path="description" class="form-control" rows="10"/>
            <form:errors path="description"/>
            <br>
            <form:label path="parentCategory">Родительская категория</form:label>
            <form:select path="parentCategory" class="form-control">
                <form:option value="">NONE</form:option>
                <c:forEach var="item" items="${rootCategories}">
                    <form:option value="${item.id}">${item.name}</form:option>
                </c:forEach>
            </form:select>
            <form:errors path="parentCategory"/>
            <br>
            <div class="alert alert-warning">
                <strong>Внимание!</strong> Убедитесь, что <strong>Родительская категория</strong> NONE.
            </div>
            <form:label path="subCategories">Дочерние категории</form:label>
            <form:select path="subCategories" multiple="true" class="form-control">
                <c:forEach var="item" items="${childCategories}">\>
                    <form:option value="${item.id}">${item.name}</form:option>
                </c:forEach>
            </form:select>
            <form:errors path="subCategories"/>
            <br>
            <label for="add" class="btn btn-info btn-sm"><i
                    class="glyphicon glyphicon-plus"></i> Добавить</label>
            <input id="add" type="submit" class="hidden"/>
        </div>
    </form:form>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>