<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${not empty category}">
    <p>
        <c:if test="${not empty category.parentCategory}">
            <c:url var="parentCategoryLink" value="/category/${category.parentCategory.name}"/>
            <c:url var="edit" value="/admin/edit/category/${category.parentCategory.id}"/>
            <a href="${parentCategoryLink}">${category.parentCategory.name}</a>
            <sec:authorize access="hasRole('ADMIN')">
                &nbsp;&nbsp;&nbsp;<a href="${edit}">Редактировать</a>/&nbsp;&nbsp;&nbsp;
            </sec:authorize>
        </c:if>
        <c:url var="categoryLink" value="/category/${category.name}"/>
        <a href="${categoryLink}">${category.name}</a>
        <sec:authorize access="hasRole('ADMIN')">
            <c:url var="edit" value="/admin/edit/category/${category.id}"/>
            <a href="${edit}">Редактировать</a>
        </sec:authorize>
    </p>
</c:if>
