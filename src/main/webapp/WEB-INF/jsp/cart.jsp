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
        <h2>${mess}</h2>
        <table>
            <tr>
                <th></th>
                <th>Имя</th>
                <th>Цена</th>
                <th>Количество</th>
            </tr
            <c:forEach var="item" items="${itemMap.entrySet()}">
                <tr>
                    <td>
                        <c:forEach var="visualization" items="${item.key.visualizations}">
                            <c:if test="${visualization.type == 0}">
                                <img src="${visualization.url}" alt="Image"/>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${item.key.name}</td>
                    <td>${item.key.price}</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/updateCart?sku=${item.key.sku}"
                              target="_top">
                            <table>
                                <tr>
                                    <td><input type="number" name="quantity" min="1" max="100" step="1"
                                               value="${item.value}"/></td>
                                    <td><input type="submit" value="Обновить"/></td>
                                </tr>
                            </table>
                        </form>
                    </td>
                    <td>
                        <form method="post"
                              action="${pageContext.request.contextPath}/deleteFromCart?sku=${item.key.sku}"
                              target="_top">
                            <input type="submit" value="Удалить"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td><h4>|Сумма: ${subTotal} грн|</h4></td>
                <td>Кол-во: ${numOfItems}</td>
                <td>
                    <c:url var="clearCart" value="/clearCart"/>
                    <form method="post" action="${clearCart}">
                        <input type="submit" value="Очистить корзину"/>
                    </form>
                </td>
            </tr>
            <tr>
                <td><h4>|Полная стоимость: ${total} грн|</h4></td>
                <td>
                    <form:form method="post" modelAttribute="customerForm"
                               action="${pageContext.request.contextPath}/cart/buyByOne"
                               target="_top">
                        <table>
                            <tr>
                                <td><form:input path="name" type="text"
                                                placeholder="Ваше имя"/></td>
                                <td><form:errors path="name"/></td>
                            </tr>
                            <tr>
                                <td><form:input path="email" type="email"
                                                placeholder="Ваш e-mail"/></td>
                                <td><form:errors path="email"/></td>
                            </tr>
                            <tr>
                                <td><form:input path="address" type="text"
                                                placeholder="Ваш адрес"/></td>
                                <td><form:errors path="address"/></td>
                            </tr>
                            <tr>
                                <td><form:input path="phone" name="phone" type="tel"
                                                placeholder="Ваш номер телефона"/></td>
                                <td><form:errors path="phone"/></td>
                            </tr>
                            <tr>
                                <td><input type="submit" value="Заказать"/></td>
                            </tr>
                        </table>
                    </form:form>
                </td>
            </tr>
        </table>

        <jsp:include page="_footer.jsp"/>
    </div>
</div>
</body>
</html>