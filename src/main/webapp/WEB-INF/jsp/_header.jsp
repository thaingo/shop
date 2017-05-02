<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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