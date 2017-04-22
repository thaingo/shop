<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width; initial-scale=1; maximum-scale=1"/>
    <meta name="description" content="Интернет-магазин dj оборудования"/>
    <meta name="author" content="tkaczenko"/>
    <title>Интернет-магазин DJ</title>
</head>
<body>
<div class="wrapper">
    <jsp:include page="_header.jsp"/>

    <div class="content">
        <jsp:include page="_categories-menu.jsp"/>

        <form:form method="post" modelAttribute="product"
                   action="${pageContext.request.contextPath}/edit/product/${product.sku}"
                   target="_top">
            <table>
                <tr>
                    <td><form:label path="name">Имя</form:label></td>
                    <td><form:input path="name" type="text"
                                    placeholder="Наименование"
                                    value="${product.name}" required="required"/></td>
                    <td><form:errors path="name"/></td>
                </tr>
                <tr>
                    <td><form:label path="price">Цена</form:label></td>
                    <td><form:input path="price" type="number"
                                    placeholder="Цена"
                                    value="${product.price}" required="required"/></td>
                    <td><form:errors path="price"/></td>
                </tr>
                <tr>
                    <td><form:label path="amount">Количество</form:label></td>
                    <td><form:input path="amount" type="number"
                                    placeholder="Количество"
                                    value="${product.amount}" required="required"/></td>
                    <td><form:errors path="amount"/></td>
                </tr>
                <tr>
                    <td><form:label path="description">Описание</form:label></td>
                    <td><form:textarea path="description" rows="5" cols="30"
                                       value="${product.description}"/></td>
                    <td><form:errors path="description"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Обновить"/></td>
                </tr>
            </table>
        </form:form>

        <jsp:include page="_footer.jsp"/>
    </div>
</div>
</body>
</html>