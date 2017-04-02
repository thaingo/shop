<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<amp-sidebar id="sidebar" layout="nodisplay" side="left">
    <ul>
        <li><a href="${pageContext.request.contextPath}/">Главная</a></li>
        <li><a href="${pageContext.request.contextPath}/shop">Магазин</a></li>
        <li><a href="#">Обратная связь</a></li>
        <li><a href="#">Войти</a></li>
    </ul>
</amp-sidebar>
<div class="menu-container">
    <div class="logo-container">
        <amp-img src="/resources/images/menu.png"
                 alt="Logo"
                 width="32"
                 height="32"
                 layout="fixed"
                 title="Click to return to Homepage"
                 on="tap:sidebar.toggle">
        </amp-img>
        <a href="${pageContext.request.contextPath}/">
            <div class="logo">
                <amp-img src="/resources/images/logo-new.png"
                         alt="Logo"
                         width="128"
                         height="32"
                         layout="responsive"
                         title="Click to return to Homepage">
                </amp-img>
            </div>
        </a>
        <form method="GET"
              action="#"
              target="_top">
            <input type="search" name="search" placeholder="Search">
        </form>
    </div>
</div>
