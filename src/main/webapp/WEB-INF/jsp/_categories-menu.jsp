<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul id="categories-menu" class="dropdown-ul">
    <c:forEach var="category" items="${categories}">
        <li class="dropdown">
            <c:url var="link" value="/category/${category.name}"/>
            <a href="${link}" class="dropdown-btn">
                    ${category.name}</a>
            <div class="dropdown-menu">
                <c:forEach var="subcategory" items="${category.subCategories}">
                    <c:url var="link" value="/category/${subcategory.name}"/>
                    <a href="${link}">${subcategory.name}</a>
                </c:forEach>
            </div>
        </li>
    </c:forEach>
</ul>
