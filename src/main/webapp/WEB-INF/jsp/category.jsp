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
    <meta name="description" content="Интернет-магазин dj оборудования"/>
    <meta name="author" content="tkaczenko"/>
    <title>${pageContext.request.contextPath} | ${category.name}</title>
</head>
<body>
<div class="wrapper">
    <jsp:include page="_header.jsp"/>

    <div class="content">
        <jsp:include page="_categories-menu.jsp"/>
        <jsp:include page="_category-nav.jsp"/>
        <jsp:include page="_subcategories-list.jsp"/>

        <div class="relative-section">
            <jsp:include page="_products_by_category.jsp"/>
        </div>

        <jsp:include page="_footer.jsp"/>
    </div>
</div>
</body>
</html>