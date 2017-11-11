<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<c:url var="home" value="/"/>
<c:url var="shop" value="/shop"/>
<c:url var="cart" value="/cart"/>
<c:url var="contact" value="#"/>
<c:url var="login" value="/login"/>
<c:url var="logout" value="/logout"/>

<footer class="footer">
    <div class="container">
        <div class="col-lg-2  col-md-2 col-sm-4 col-xs-6">
            <h3> Основное </h3>
            <ul>
                <li class="active"><a href="${home}" target="_blank">Главная</a></li>
                <li><a href="${shop}" target="_blank">Магазин</a></li>
            </ul>
        </div>
        <div class="col-lg-2  col-md-2 col-sm-4 col-xs-6">
            <h3> Помощь </h3>
            <ul>
                <li><a href="${contact}" target="_blank">Обратная связь</a></li>
            </ul>
        </div>
        <div class="col-lg-2  col-md-2 col-sm-4 col-xs-6">
            <h3> Другое </h3>
            <ul>
                <li><a href="${cart}" target="_blank"><span
                        class="glyphicon glyphicon-shopping-cart"></span> ${cartSize}</a></li>
                <sec:authorize access="!isAuthenticated()">
                    <li>
                        <a href="${login}" target="_blank"><span class="glyphicon glyphicon-log-in"></span> Войти</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li>
                        <a href="${logout}" target="_blank"><span class="glyphicon glyphicon-log-in"></span> Выйти</a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
        <div class="col-lg-3  col-md-3 col-sm-6 col-xs-12 ">
            <h3> Подпишитесь </h3>
            <ul>
                <li>
                    <div class="input-append newsletter-box text-center">
                        <input type="text" class="full text-center" placeholder="Email ">
                        <button class="btn  bg-gray" type="button"> Подписаться <i
                                class="fa fa-long-arrow-right"></i>
                        </button>
                    </div>
                </li>
            </ul>
            <ul class="social">
                <li><a href="#"> <i class=" fa fa-facebook">   </i> </a></li>
                <li><a href="#"> <i class="fa fa-twitter">   </i> </a></li>
                <li><a href="#"> <i class="fa fa-google-plus">   </i> </a></li>
                <li><a href="#"> <i class="fa fa-pinterest">   </i> </a></li>
                <li><a href="#"> <i class="fa fa-youtube">   </i> </a></li>
            </ul>
        </div>
    </div>
</footer>

<div class="footer-bottom">
    <div class="container">
        <p class="pull-left"> &copy; Интернет-магазин "DJ" 2017.
            Developed by
            <a href="https://github.com/tkaczenko/"><b>Andrii Tkachenko</b></a>.
            Sources available at <a href="https://github.com/tkaczenko/shop/">Github</a>
        </p>
        <div class="pull-right">
            <ul class="nav nav-pills payments">
                <li><i class="fa fa-cc-visa"></i></li>
                <li><i class="fa fa-cc-mastercard"></i></li>
                <li><i class="fa fa-cc-amex"></i></li>
                <li><i class="fa fa-cc-paypal"></i></li>
            </ul>
        </div>
    </div>
</div>