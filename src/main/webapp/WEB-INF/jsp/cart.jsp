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

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resources/css/styles.css"/>"/>

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <%--jQuery--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <title>Интернет-магазин DJ</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<div class="container">
    <div class="page-header">
        <h2>${mess}</h2>
    </div>
    <div class="container">
        <h2>Оформить заказ</h2>
        <form:form method="post" modelAttribute="customerForm"
                   action="${pageContext.request.contextPath}/cart/buyByOne"
                   target="_top">
            <table class="table table-responsive">
                <tbody>
                <tr>
                    <td><form:label path="email">E-mail *</form:label></td>
                    <td><form:input path="email" type="email" class="form-control"
                                    placeholder="Ваш e-mail"
                                    value="${customerForm.email}" required="required"/></td>
                    <td><form:errors path="email"/></td>
                </tr>
                <tr>
                    <td><form:label path="address">Адрес *</form:label></td>
                    <td><form:input path="address" type="text" class="form-control"
                                    placeholder="Ваш адрес"
                                    value="${customerForm.address}" required="required"/></td>
                    <td><form:errors path="address"/></td>
                </tr>
                <tr>
                    <td><form:label path="phone">Номер телефона</form:label></td>
                    <td><form:input path="phone" name="phone" type="tel" class="form-control"
                                    placeholder="Ваш номер телефона"
                                    value="${customerForm.phone}"/></td>
                    <td><form:errors path="phone"/></td>
                </tr>
                <tr>
                    <td><form:label path="name">Имя</form:label></td>
                    <td><form:input path="name" type="text" class="form-control"
                                    placeholder="Ваше имя"
                                    value="${customerForm.name}"/></td>
                    <td><form:errors path="name"/></td>
                </tr>
                <tr>
                    <td>
                        <label for="buy" class="btn btn-success">Купить <i
                                class="glyphicon glyphicon-chevron-right"></i></label>
                        <input id="buy" type="submit" value="Купить" class="hidden"/>
                    <td>
                </tr>
                </tbody>
            </table>
        </form:form>
    </div>
</div>
<div class="container">
    <table id="cart" class="table table-hover table-condensed">
        <thead>
        <tr>
            <th style="width:50%">Наименование</th>
            <th style="width:10%">Цена</th>
            <th style="width:8%">Количество</th>
            <th style="width:22%" class="text-center">Стоимость</th>
            <th style="width:10%"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${itemMap.entrySet()}">
            <tr>
                <td data-th="Наименование">
                    <div class="col-sm-2 hidden-xs">
                        <c:forEach var="visualization" items="${item.key.visualizations}">
                            <c:if test="${visualization.type == 0}">
                                <img class="img-responsive" src="${visualization.url}">
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="col-sm-10">
                        <h4 class="nomargin">${item.key.name}</h4>
                        <p>
                            <c:choose>
                                <c:when test="${item.key.amount > 0}">
                                    <span>Статус: </span><span
                                        class="text-success"><strong>В наличии</strong></span>
                                </c:when>
                                <c:otherwise>
                                    <span>Статус: </span><span
                                        class="text-success"><strong>Нет в наличии</strong></span>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </td>
                <td data-th="Цена">${item.key.price}</td>
                <td data-th="Количество" class="text-center">
                    <form method="post"
                          action="${pageContext.request.contextPath}/updateCart?sku=${item.key.sku}"
                          target="_top">
                        <input type="number" class="form-control text-center" name="quantity" min="1"
                               max="${item.key.amount}" step="1"
                               value="${item.value}" required/>

                        <label for="update" class="btn btn-info btn-sm"><i
                                class="glyphicon glyphicon-refresh"></i></label>
                        <input id="update" type="submit" class="hidden"/>
                    </form>
                </td>
                <td data-th="Стоимость" class="text-center">${item.key.price * item.value}</td>
                <td data-th="" class="text-center">
                    <form>
                        <button class="btn btn-danger btn-sm" formmethod="post"
                                formaction="${pageContext.request.contextPath}/deleteFromCart?sku=${item.key.sku}">
                            <span class="glyphicon glyphicon-remove"></span>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr class="visible-xs">
            <td class="text-center"><strong>Сумма ${subTotal}</strong></td>
        </tr>
        <tr>
            <td>
                <c:url var="home" value="/"/>
                <a href="${home}" class="btn btn-warning"><i class="glyphicon glyphicon-chevron-left"></i>
                    Продолжить
                    покупки</a>
            </td>
            <td colspan="2" class="hidden-xs"></td>
            <td class="hidden-xs text-center"><strong>Сумма ${subTotal}</strong></td>
            <td>
                <c:url var="clearCart" value="/clearCart"/>
                <form>
                    <button class="btn btn-danger btn-block" formmethod="post" formaction="${clearCart}">
                        <span class="glyphicon glyphicon-trash"></span> Очистить
                    </button>
                </form>
            </td>
        </tr>
        </tfoot>
    </table>
</div>
<table class="table table-responsive table-hover">
    <tbody>
    <tr>
        <td><h5>Доставка</h5></td>
        <td class="text-right"><h5><strong>${estimated}</strong></h5></td>
    </tr>
    <tr>
        <td><h3>Полная стоимость</h3></td>
        <td class="text-right"><h3><strong>${total}</strong></h3></td>
    </tr>
</table>
<jsp:include page="_footer.jsp"/>
</body>
</html>