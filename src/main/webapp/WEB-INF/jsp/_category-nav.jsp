<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p>
    <c:if test="${not empty category.parentCategory}">
        <c:url var="parentCategoryLink" value="/category/${category.parentCategory.name}"/>
        <a href="${parentCategoryLink}">${category.parentCategory.name}</a>
        &nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;
    </c:if>
    <c:url var="categoryLink" value="/category/${category.name}"/>
    <a href="${categoryLink}">${category.name}</a>
</p>
