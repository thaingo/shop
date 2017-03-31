<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="relative-grid">
    <c:forEach var="product" items="${products}">
        <li>
            <div class="product">
                <a href="product?id=${product.sku}">
                    <div class="relative-box">
                        <c:forEach var="visualization" items="${product.visualizations}">
                            <c:if test="${visualization.type == 0}">
                                <img src="${visualization.url}" alt="Image"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </a>
                <a href="product.jsp?id=${product.sku}">
                    <div class="price">
                        <span><strong>${product.price}</strong> грн.</span>
                    </div>
                </a>
                <div class="actions">
                    <a href="#">Add to cart</a>
                </div>
            </div>
            <div class="title">
                <a href="product.jsp?id=${product.sku}">${product.name}</a>
            </div>
        </li>
    </c:forEach>
</ul>