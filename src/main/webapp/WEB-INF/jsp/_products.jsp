<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="relative-grid">
    <c:forEach var="entry" items="${products}">
        <li>
            <div class="product">
                <c:url var="productLink" value="/category/${entry.value}/product/${entry.key.name}"/>
                <a href="${productLink}">
                    <div class="relative-box">
                        <c:forEach var="visualization" items="${entry.key.visualizations}">
                            <c:if test="${visualization.type == 0}">
                                <img src="${visualization.url}" alt="Image"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </a>
                <a href="${productLink}">
                    <div class="price">
                        <span><strong>${entry.key.price}</strong> грн.</span>
                    </div>
                </a>
                <div class="actions">
                    <c:url var="addToCart" value="/addToCart?sku=${entry.key.sku}"/>
                    <form method="post" action="${addToCart}">
                        <input type="submit"
                                <c:if test="${entry.key.amount <= 0}"><c:out value="disabled='disabled'"/></c:if>
                               value="Добавить в корзину"/>
                    </form>
                </div>
            </div>
            <div class="title">
                <a href="${productLink}">${entry.key.name}</a>
            </div>
        </li>
    </c:forEach>
</ul>