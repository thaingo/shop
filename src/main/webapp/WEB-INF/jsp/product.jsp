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
    <style>
        .error {
            color: #ff0000;
            font-style: italic;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="wrapper">
    <jsp:include page="_header.jsp"/>

    <div class="content">
        <jsp:include page="_categories-menu.jsp"/>
        <jsp:include page="_category-nav.jsp"/>

        <h2>${mess}</h2>

        <c:url var="linkToProduct" value="/category/${category.name}/product/${product.name}"/>

        <form:form method="post" modelAttribute="customerForm"
                   action="${linkToProduct}/buyByOne"
                   target="_top">
            <table>
                <tr>
                    <td><form:input path="name" type="text" class="feedback-input" id="name"
                                    placeholder="Ваше имя"/></td>
                    <td><form:errors path="name" cssClass="error"/></td>
                </tr>
                <tr>
                    <td><form:input path="email" type="email" class="feedback-input" id="phone"
                                    placeholder="Ваш e-mail"/></td>
                    <td><form:errors path="email" cssClass="error"/></td>
                </tr>
                <tr>
                    <td><form:input path="phone" name="phone" type="tel" class="feedback-input" id="phone1"
                                    placeholder="Ваш номер телефона"/></td>
                    <td><form:errors path="phone" cssClass="error"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Заказать" id="btn-send1"/></td>
                </tr>
            </table>
        </form:form>
        <c:url var="addToCart" value="/addToCart?sku=${product.sku}"/>
        <a href="${addToCart}">Добавить в корзину</a>
        <table style="width: 100%">
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Likes</th>
                <th>Dislikes</th>
                <th>Description</th>
                <th>Specification</th>
            </tr>
            <tr>
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