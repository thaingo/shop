<!DOCTYPE html>

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
    <title>${pageContext.request.contextPath} | ${product.name}</title>
</head>
<body>
<div class="wrapper">
    <jsp:include page="_header.jsp"/>

    <div class="content">
        <jsp:include page="_categories-menu.jsp"/>
        <jsp:include page="_category-nav.jsp"/>

        <c:url var="linkToProduct" value="/category/${category.name}/product/${product.name}"/>

        <form method="post" action="${linkToProduct}/buyByOne?sku=${product.sku}">
            <input type="submit"
                    <c:if test="${product.amount <= 0}"><c:out value="disabled='disabled'"/></c:if>
                   value="Купить в 1 клик"/>
        </form>

        <c:url var="addToCart" value="/addToCart?sku=${product.sku}"/>
        <form method="post" action="${addToCart}">
            <input type="submit"
                    <c:if test="${product.amount <= 0}"><c:out value="disabled='disabled'"/></c:if>
                   value="Добавить в корзину"/>
        </form>
        <c:url var="addLike" value="${linkToProduct}/addLike?sku=${product.sku}"/>
        <form method="post" action="${addLike}">
            <input type="submit"
                    <c:if test="${liked == true}"><c:out value="disabled='disabled'"/></c:if>
                   value="Лайк (${product.likes})"/>
        </form>
        <c:url var="addDislike" value="${linkToProduct}/addDislike?sku=${product.sku}"/>
        <form method="post" action="${addDislike}">
            <input type="submit"
                    <c:if test="${disliked == true}"><c:out value="disabled='disabled'"/></c:if>
                   value="Дизалайк (${product.dislikes})"/>
        </form>
        <table style="width: 100%">
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Description</th>
                <th>Specification</th>
            </tr>
            <tr>
                <td>${product.name}</td>
                <td>${product.price}</td>
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