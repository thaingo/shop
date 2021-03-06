<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="home" value="/"/>
<c:url var="shop" value="/shop"/>
<c:url var="cart" value="/cart"/>
<c:url var="contact" value="#"/>
<c:url var="login" value="/login"/>
<c:url var="logout" value="/logout"/>
<c:url var="search" value="/search"/>

<div class="container">
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
                    <li><a href="${home}">Главная</a></li>
                    <li><a href="${shop}">Магазин</a></li>
                    <li><a href="${contact}">Обратная связь</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="${cart}"><span class="glyphicon glyphicon-shopping-cart"></span> ${cartSize}</a></li>
                    <sec:authorize access="!isAuthenticated()">
                        <li><a href="${login}"><span class="glyphicon glyphicon-log-in"></span> Войти</a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <li><a href="${logout}"><span class="glyphicon glyphicon-log-out"></span> Выйти</a></li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ADMIN')">
                        <li class="dropdown">
                            <a href="#" data-toggle="dropdown" class="dropdown-toggle"><span
                                    class="glyphicon glyphicon-edit"></span><b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <c:url var="addProduct" value="/admin/add/product"/>
                                    <a href="${addProduct}">Продукт</a>
                                </li>
                                <li>
                                    <c:url var="addCategory" value="/admin/add/category"/>
                                    <a href="${addCategory}">Категорию</a>
                                </li>
                                <li>
                                    <c:url var="editAttributes" value="/admin/edit/attributes"/>
                                    <a href="${editAttributes}">Атрибут</a>
                                </li>
                            </ul>
                        </li>
                    </sec:authorize>
                    <li>

                    </li>
                </ul>
                <form class="navbar-form navbar-right" role="search" method="get"
                      action="${search}">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Найти" name="query">
                        <input type="text" class="hidden" name="page" value="0">
                        <input type="text" class="hidden" name="size" value="${offset}">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </nav>
</div>