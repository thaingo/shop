<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="dropdown-ul">
    <c:forEach var="category" items="${categories}">
        <li class="dropdown">
            <a href="category?id=${category.id}" class="dropdown-btn">${category.name}</a>
            <div class="dropdown-menu">
                <c:forEach var="subcategory" items="${category.subCategories}">
                    <a href="category?id=${subcategory.id}">${subcategory.name}</a>
                </c:forEach>
            </div>
        </li>
    </c:forEach>
</ul>
