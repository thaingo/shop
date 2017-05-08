<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty category}">
    <p>
        <c:if test="${not empty category.parentCategory}">
            <c:url var="parentCategoryLink" value="/category/${category.parentCategory.name}"/>
            <c:url var="edit" value="/admin/edit/category/${category.parentCategory.id}"/>
            <a href="${parentCategoryLink}">${category.parentCategory.name}</a>
            &nbsp;&nbsp;&nbsp;<a href="${edit}">Редактировать</a>/&nbsp;&nbsp;&nbsp;
        </c:if>
        <c:url var="categoryLink" value="/category/${category.name}"/>
        <a href="${categoryLink}">${category.name}</a>
        <c:url var="edit" value="/admin/edit/category/${category.id}"/>
        <a href="${edit}">Редактировать</a>
    </p>
</c:if>
