<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="home" value="/"/>
<c:url var="shop" value="/shop"/>
<c:url var="cart" value="/cart"/>
<c:url var="contact" value="#"/>
<c:url var="login" value="#"/>

<div class="footer">
    <footer>
        <div id="footer_container">
            <ul class="navigation">
                <li><a href="${home}" target="_blank">Главная</a></li>
                <li><a href="${shop}" target="_blank">Магазин</a></li>
                <li><a href="${cart}" target="_blank">Корзина</a></li>
                <li><a href="${contact}" target="_blank">Обратная связь</a></li>
                <li><a href="${login}" target="_blank">Войти</a></li>
            </ul>
            <p class="copyright-text">&copy; Интернет-магазин "DJ" 2017.</p>
        </div>
    </footer>
</div>
