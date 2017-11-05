<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${not empty category}">
    <ul class="breadcrumb">
        <c:if test="${not empty category.parentCategory}">
            <c:url var="parentCategoryLink" value="/category/${category.parentCategory.name}">
                <c:param name="page" value="0"/>
                <c:param name="size" value="${offset}"/>
            </c:url>
            <c:url var="edit" value="/admin/edit/category/${category.parentCategory.id}"/>
            <li><a href="${parentCategoryLink}">${category.parentCategory.name}</a></li>
            <sec:authorize access="hasRole('ADMIN')">
                &nbsp;&nbsp;&nbsp;<a href="${edit}">Редактировать</a>
            </sec:authorize>
        </c:if>
        <c:url var="categoryLink" value="/category/${category.name}">
            <c:param name="page" value="0"/>
            <c:param name="size" value="${offset}"/>
        </c:url>
        <li class="active"><a href="${categoryLink}">${category.name}</a></li>
        <sec:authorize access="hasRole('ADMIN')">
            <c:url var="edit" value="/admin/edit/category/${category.id}"/>
            &nbsp;&nbsp;&nbsp;<a href="${edit}">Редактировать</a>
        </sec:authorize>
    </p>
    </ul>
</c:if>
