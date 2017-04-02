<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="main-menu">
    <ul id="categories-menu" class="dropdown-ul">
        <c:forEach var="category" items="${categories}">
            <li class="dropdown">
                <a href="${pageContext.request.contextPath}/category/${category.name}" class="dropdown-btn">
                        ${category.name}</a>
                <div class="dropdown-menu">
                    <c:forEach var="subcategory" items="${category.subCategories}">
                        <a href="${pageContext.request.contextPath}/category/${subcategory.name}">${subcategory.name}</a>
                    </c:forEach>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>