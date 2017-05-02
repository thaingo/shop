<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

    <title>Интернет-магазин DJ</title>
</head>
<body>
<c:url var="home" value="/"/>
<c:url var="shop" value="/shop"/>
<c:url var="cart" value="/cart"/>
<c:url var="contact" value="#"/>
<c:url var="login" value="#"/>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${home}">Интернет-магазин DJ</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="${home}">Главная</a></li>
                <li><a href="${shop}">Магазин</a></li>
                <li><a href="${contact}">Обратная связь</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${cart}"><span class="glyphicon glyphicon-shopping-cart"></span> ${cartSize}</a></li>
                <li><a href="${login}"><span class="glyphicon glyphicon-log-in"></span> Войти</a></li>
            </ul>
            <form class="navbar-form navbar-right" role="search" method="get"
                  action="${pageContext.request.contextPath}/search">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Найти" name="query">
                    <div class="input-group-btn">
                        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</nav>
<div class="container">
    <div class="page-header">
        <jsp:include page="_categories-menu.jsp"/>
    </div>
    <jsp:include page="_products.jsp"/>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>