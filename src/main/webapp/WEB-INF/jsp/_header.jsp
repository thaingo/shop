<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="home" value="/"/>
<c:url var="shop" value="/shop"/>
<c:url var="cart" value="/cart"/>
<c:url var="contact" value="#"/>
<c:url var="login" value="#"/>

<header>
    <a href="index.jsp" id="logo"></a>
    <nav>
        <a href="#" id="menu_icon"></a>
        <ul class="main-menu">
            <li>
                <form method="get" action="${pageContext.request.contextPath}/search"
                      target="_top">
                    <table>
                        <tr>
                            <td><input id="search" type="text" name="query" value="${query}" placeholder="Search.."/>
                            <td><input type="submit" value="Найти"/></td>
                        </tr>
                    </table>
                </form>
            </li>
            <li><a href="${home}">Главная</a></li>
            <li><a href="${shop}">Магазин</a></li>
            <li><a href="${cart}">Корзина (${cartSize})</a></li>
            <li><a href="${contact}">Обратная связь</a></li>
            <li><a href="${login}">Войти</a></li>
        </ul>
    </nav>
</header>
