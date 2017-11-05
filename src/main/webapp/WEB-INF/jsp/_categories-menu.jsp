<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul id="categories-menu" class="list-inline">
    <c:forEach var="category" items="${rootCategories}">
        <li class="dropdown">
            <c:url var="link" value="/category/${category.name}">
                <c:param name="page" value="0"/>
                <c:param name="size" value="${offset}"/>
            </c:url>
            <a href="${link}" data-toggle="dropdown" class="dropdown-toggle">${category.name} <b class="caret"></b></a>
            <ul class="dropdown-menu">
                <c:forEach var="subcategory" items="${category.subCategories}">
                    <c:url var="link" value="/category/${subcategory.name}">
                        <c:param name="page" value="0"/>
                        <c:param name="size" value="${offset}"/>
                    </c:url>
                    <li><a href="${link}">${subcategory.name}</a></li>
                </c:forEach>
            </ul>
        </li>
    </c:forEach>
</ul>