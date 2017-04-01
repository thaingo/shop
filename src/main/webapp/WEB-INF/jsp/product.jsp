<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width; initial-scale=1; maximum-scale=1"/>
    <meta name="description" content="Cool online store"/>
    <meta name="author" content="tkaczenko"/>

    <!-- Styles -->
    <link rel="stylesheet" href="<c:url value="/resources/css/styles.css"/>"/>

    <!-- Icons -->
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico"/>">

    <title>Online store</title>
</head>
<body>
<div class="wrapper">
    <jsp:include page="_header.jsp"/>

    <div class="content">
        <jsp:include page="_categories-menu.jsp"/>

        <p>
            <c:if test="${not empty category.parentCategory}">
                <a href="${pageContext.request.contextPath}/category/${category.parentCategory.name}">
                        ${category.parentCategory.name}
                </a>
                &nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;
            </c:if>
            <a href="${pageContext.request.contextPath}/category/${category.name}">${category.name}</a>
            &nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;
            <a href="${pageContext.request.contextPath}/category/${category.name}/product/${product.name}">
                ${product.name}
            </a>
        </p>

        <table style="width: 100%">
            <tr>
                <th>Visualizations</th>
                <th>Name</th>
                <th>Price</th>
                <th>Likes</th>
                <th>Dislikes</th>
                <th>Description</th>
                <th>Specification</th>
            </tr>
            <tr>
                <td></td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.likes}</td>
                <td>${product.dislikes}</td>
                <td>${product.description}</td>
                <td>
                    <table>
                        <tr>
                            <th>Key</th>
                            <th>Value</th>
                        </tr>
                        <c:forEach var="attribute" items="${product.attributes}">
                            <tr>
                                <td>${attribute.attribute.name}</td>
                                <td>${attribute.value}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
            <c:forEach var="visualization" items="${product.visualizations}">
                <tr>
                    <td><img src="${visualization.url}" alt="Image"/></td>
                </tr>
            </c:forEach>
        </table>

        <jsp:include page="_footer.jsp"/>
    </div>
</div>
</body>
</html>