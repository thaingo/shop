<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="relative-grid">
    <c:forEach var="product" items="${products}">
        <li>
            <div class="product">
                <c:url var="productLink" value="/category/${category.name}/product/${product.name}"/>
                <a href="${productLink}">
                    <div class="relative-box">
                        <c:forEach var="visualization" items="${product.visualizations}">
                            <c:if test="${visualization.type == 0}">
                                <img src="${visualization.url}" alt="Image"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </a>
                <a href="${productLink}">
                    <div class="price">
                        <span><strong>${product.price}</strong> грн.</span>
                    </div>
                </a>
                <div class="actions">
                    <c:url var="addToCart" value="/addToCart?sku=${product.sku}"/>
                    <a href="${addToCart}">Добавить в корзину</a>
                </div>
            </div>
            <div class="title">
                <a href="${productLink}">${product.name}</a>
            </div>
        </li>
    </c:forEach>
</ul>