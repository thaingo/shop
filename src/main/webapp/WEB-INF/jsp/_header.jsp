<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <a href="index.jsp" id="logo"></a>
    <nav>
        <a href="#" id="menu_icon"></a>
        <ul class="main-menu">
            <li><input id="search" type="text" name="search" placeholder="Search.."/></li>
            <li><a href="${pageContext.request.contextPath}/">Главная</a></li>
            <li><a href="${pageContext.request.contextPath}/shop">Магазин</a></li>
            <li><a href="#">Обратная связь</a></li>
            <li><a href="#">Войти</a></li>
        </ul>
    </nav>
</header>
