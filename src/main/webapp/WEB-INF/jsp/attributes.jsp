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
    <h2>Аттрибуты</h2>
    <c:url var="refreshAttributes" value="/admin/edit/attributes"/>
    <form:form method="post" modelAttribute="attributesForm" action="${refreshAttributes}" target="_top">
        <div class="form-group">
            <c:forEach items="${attributesForm.attributes}" var="attribute" varStatus="i" begin="0">
                <div class="form-group">
                    <form:input path="attributes[${i.index}].oldName" type="text" cssClass="hidden"/>
                    <form:input path="attributes[${i.index}].newName" type="text" class="form-control"
                                value="${attribute.oldName}"/>
                </div>
            </c:forEach>
            <form:label path="numOfAttributes">Количество атрибутов для добавления</form:label>
            <form:input path="numOfAttributes" type="number" class="form-control" min="0" max="100"
                        step="1"/>
            <form:errors path="numOfAttributes"/>
            <br>
            <label for="update" class="btn btn-info btn-sm"><i
                    class="glyphicon glyphicon-refresh"></i> Обновить</label>
            <input id="update" type="submit" class="hidden"/>
        </div>
    </form:form>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>