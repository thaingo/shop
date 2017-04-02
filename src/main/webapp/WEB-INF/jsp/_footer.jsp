<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<footer class="footer">
    <div class="top">
        <div class="col-sm-3 mbottom30">
            <p class="mtop30">Подпишитесь и получайте новости об акциях и специальных предложениях</p>
            <form method="GET"
                  action="#"
                  target="_top">
                <input type="email" name="email" placeholder="Ваш e-mail"/>
                <input type="submit" name="subscribe" value="Подписаться" />
            </form>

        </div>
    </div>
    <div class="bottom">
        <div class="col-left">
            <ul class="reset-list">
                <li class="footer"><a href="${pageContext.request.contextPath}/" target="_blank">Главная</a> -</li>
                <li class="footer"><a href="${pageContext.request.contextPath}/shop" target="_blank">Магазин</a> -</li>
                <li class="footer"><a href="#" target="_blank">Обратная связь</a> -</li>
                <li class="footer"><a href="#" target="_blank">Войти</a></li>
            </ul>
        </div>
        <div class="col-right">
            <p class="copyright">
                © 2017 <strong>mysite.com</strong>. Интернет-магазин "DJ"
            </p>
        </div>
    </div>
</footer>
