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

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resources/css/styles.css"/>"/>

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/jquery.slick/1.6.0/slick.css"/>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/jquery.slick/1.6.0/slick-theme.css"/>

    <%--jQuery--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <script type="text/javascript" src="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick.min.js"></script>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.0.0/jquery-migrate.min.js"></script>
    <script type="text/javascript" src="/resources/scripts.js"></script>

    <title>${pageContext.request.contextPath} | ${product.name}</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<div class="container">
    <div class="page-header">
        <jsp:include page="_categories-menu.jsp"/>
    </div>
    <div class="container-fluid">
        <div class="content-wrapper">
            <div class="item-container">
                <div class="container">
                    <div class="col-md-12">
                        <div class="myCarousel">
                            <c:forEach var="visualization" items="${product.visualizations}">
                                <div class="container">
                                    <img src="${visualization.url}" class="img-responsive" alt="Image"/>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="col-md-7">
                            <c:url var="linkToProduct" value="/category/${category.name}/product/${product.name}"/>
                            <div class="product-title">${product.name}</div>
                            <%--<div class="product-desc">${product.description}</div>--%>
                            <div class="ratings">
                                <form>
                                    <c:url var="addLike" value="${linkToProduct}/addLike?sku=${product.sku}"/>
                                    <button class="btn btn-default btn-sm" formmethod="post"
                                            formaction="${addLike}"
                                            <c:if test="${liked == true}"><c:out value="disabled='disabled'"/></c:if>>
                                        <span class="glyphicon glyphicon-thumbs-up"></span> ${product.likes}
                                    </button>
                                    <c:url var="addDislike" value="${linkToProduct}/addDislike?sku=${product.sku}"/>
                                    <button class="btn btn-default btn-sm" formmethod="post"
                                            formaction="${addDislike}"
                                            <c:if test="${disliked == true}"><c:out
                                                    value="disabled='disabled'"/></c:if>>
                                        <span class="glyphicon glyphicon-thumbs-down"></span> ${product.dislikes}
                                    </button>
                                </form>
                            </div>
                            <hr>
                            <div class="product-price">${product.price}</div>
                            <c:choose>
                                <c:when test="${product.amount > 0}">
                                    <div class="product-stock">В наличии</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="product-stock">Нет в наличии</div>
                                </c:otherwise>
                            </c:choose>
                            <hr>
                            <div class="btn-group cart">
                                <form>
                                    <c:url var="addToCart" value="/addToCart?sku=${product.sku}"/>
                                    <button class="btn btn-success" formmethod="post"
                                            formaction="${addToCart}"
                                            <c:if test="${product.amount <= 0}"><c:out
                                                    value="disabled='disabled'"/></c:if>>
                                        <span class="glyphicon glyphicon-shopping-cart"></span> Добавить
                                    </button>
                                </form>
                            </div>
                            <div class="btn-group wishlist">
                                <form>
                                    <c:url var="buyByOne" value="${linkToProduct}/buyByOne?sku=${product.sku}"/>
                                    <button class="btn btn-danger" formmethod="post"
                                            formaction="${buyByOnel}"
                                            <c:if test="${product.amount <= 0}"><c:out
                                                    value="disabled='disabled'"/></c:if>>
                                        Купить
                                    </button>
                                </form>
                            </div>
                            <c:url var="edit" value="/admin/edit/product/${product.sku}"/>
                            <a href="${edit}">Редактировать</a>
                        </div>
                    </div>
                </div>
                <div class="container-fluid">
                    <div class="col-md-12 product-info">
                        <ul id="myTab" class="nav nav-tabs nav_tabs">
                            <li class="active"><a href="#service-one" data-toggle="tab">ОПИСАНИЕ</a></li>
                            <li><a href="#service-two" data-toggle="tab">ХАРАКТЕРИСТИКИ</a></li>
                        </ul>
                        <div id="myTabContent" class="tab-content">
                            <div class="tab-pane fade in active" id="service-one">
                                <section class="container product-info">
                                    ${product.description}
                                </section>
                            </div>
                            <div class="tab-pane fade" id="service-two">
                                <section class="container">
                                    <table class="table table-responsive">
                                        <thead>
                                        <tr>
                                            <th>Имя</th>
                                            <th>Значение</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="attribute" items="${product.attributes}">
                                            <tr>
                                                <td>${attribute.attribute.name}</td>
                                                <td>${attribute.value}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </section>
                            </div>
                        </div>
                        <hr>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>