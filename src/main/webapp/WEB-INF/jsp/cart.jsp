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
        <div class="row">
            <div class="col-sm-12 col-md-10 col-md-offset-1">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Наименование</th>
                        <th>Количество</th>
                        <th class="text-center">Цена</th>
                        <th class="text-center">Общая</th>
                        <th> </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${itemMap.entrySet()}">
                        <tr>
                            <td class="col-sm-8 col-md-6">
                                <div class="media">
                                    <c:forEach var="visualization" items="${item.key.visualizations}">
                                        <c:if test="${visualization.type == 0}">
                                            <a class="thumbnail pull-left" href="#">
                                                <img class="media-object"
                                                     src="${visualization.url}"
                                                     style="width: 72px; height: 72px;"></a>
                                        </c:if>
                                    </c:forEach>
                                    <div class="media-body">
                                        <h4 class="media-heading"><a href="#">${item.key.name}</a></h4>
                                            <%--<h5 class="media-heading"> by <a href="#">Brand name</a></h5>--%>
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
                                    </div>
                                </div>
                            </td>
                            <td class="col-sm-1 col-md-2" style="text-align: center">
                                <form method="post"
                                      action="${pageContext.request.contextPath}/updateCart?sku=${item.key.sku}"
                                      target="_top">
                                    <table>
                                        <tr>
                                            <td><input type="number" class="form-control" name="quantity" min="1"
                                                       max="${item.key.amount}" step="1"
                                                       value="${item.value}" required/></td>
                                            <td>
                                                <label for="update" class="btn btn-default"><i
                                                        class="glyphicon glyphicon-refresh"></i></label>
                                                <input id="update" type="submit" class="hidden"/>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </td>
                            <td class="col-sm-1 col-md-1 text-center"><strong>${item.key.price}</strong></td>
                            <td class="col-sm-1 col-md-1 text-center"><strong>${item.key.price*item.value}</strong></td>
                            <td class="col-sm-1 col-md-1">
                                <form>
                                    <button class="btn btn-danger" formmethod="post"
                                            formaction="${pageContext.request.contextPath}/deleteFromCart?sku=${item.key.sku}">
                                        <span class="glyphicon glyphicon-remove"></span> Удалить
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td>  </td>
                        <td>  </td>
                        <td>  </td>
                        <td><h5>Сумма</h5></td>
                        <td class="text-right"><h5><strong>${subTotal}</strong></h5></td>
                    </tr>
                    <tr>
                        <td>  </td>
                        <td>  </td>
                        <td>  </td>
                        <td><h5>Доставка</h5></td>
                        <td class="text-right"><h5><strong>${estimated}</strong></h5></td>
                    </tr>
                    <tr>
                        <td>  </td>
                        <td>  </td>
                        <td>  </td>
                        <td><h3>Общая стоимость</h3></td>
                        <td class="text-right"><h3><strong>${total}</strong></h3></td>
                    </tr>
                    <tr>
                        <td>  </td>
                        <td>  </td>
                        <td>  </td>
                        <td></td>
                        <td class="text-right">
                            <c:url var="clearCart" value="/clearCart"/>
                            <form>
                                <button class="btn btn-danger" formmethod="post" formaction="${clearCart}">
                                    <span class="glyphicon glyphicon-trash"></span> Очистить
                                </button>
                            </form>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="row">
        <h2>Заказать</h2>
        <form:form method="post" modelAttribute="customerForm"
                   action="${pageContext.request.contextPath}/cart/buyByOne"
                   target="_top">
            <table class="table table-responsive">
                <tbody>
                <tr>
                    <td><form:label path="email">E-mail *</form:label></td>
                    <td><form:input path="email" type="email"
                                    placeholder="Ваш e-mail"
                                    value="${customerForm.email}" required="required"/></td>
                    <td><form:errors path="email"/></td>
                </tr>
                <tr>
                    <td><form:label path="address">Адрес *</form:label></td>
                    <td><form:input path="address" type="text"
                                    placeholder="Ваш адрес"
                                    value="${customerForm.address}" required="required"/></td>
                    <td><form:errors path="address"/></td>
                </tr>
                <tr>
                    <td><form:label path="phone">Номер телефона</form:label></td>
                    <td><form:input path="phone" name="phone" type="tel"
                                    placeholder="Ваш номер телефона"
                                    value="${customerForm.phone}"/></td>
                    <td><form:errors path="phone"/></td>
                </tr>
                <tr>
                    <td><form:label path="name">Имя</form:label></td>
                    <td><form:input path="name" type="text"
                                    placeholder="Ваше имя"
                                    value="${customerForm.name}"/></td>
                    <td><form:errors path="name"/></td>
                </tr>
                <tr>
                    <td>
                        <label for="buy" class="btn btn-success">Купить <i
                                class="glyphicon glyphicon-play"></i></label>
                        <input id="buy" type="submit" value="Купить" class="hidden"/>
                    <td>
                </tr>
                </tbody>
            </table>
        </form:form>
    </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>