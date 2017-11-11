<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="ru"
      xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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

    <title>Интернет-магазин DJ | Войти</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<div class="container">
    <div class="page-header">
        <jsp:include page="_categories-menu.jsp"/>
    </div>
    <div class="container">
        <div class="page-header">
            <label>Войти</label>
        </div>
        <c:url var="login" value="/login"/>
        <form:form modelAttribute="loginForm" action="${login}" method="post">
        <div class="form-group">
            <form:label path="username">Логин</form:label>
            <form:input path="username" type="text" class="form-control"/>
            <form:errors path="username"/>
            <br>
            <form:label path="password">Пароль</form:label>
            <form:input path="password" type="password" class="form-control"/>
            <form:errors path="password"/>
            <br>
            <input type="submit" value="Войти" class="btn btn-success"/>
            </form:form>
        </div>
    </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>