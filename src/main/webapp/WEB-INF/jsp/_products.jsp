<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="container">
    <div class="row">
        <c:forEach var="entry" items="${products}">
            <div class="col-md-3 col-sm-6">
                <c:url var="productLink" value="/category/${entry.value}/product/${entry.key.name}"/>
                <span class="thumbnail">
            <a href="${productLink}">
                <c:forEach var="visualization" items="${entry.key.visualizations}">
                    <c:if test="${visualization.type == 0}">
                        <img src="${visualization.url}" alt="Image"/>
                    </c:if>
                </c:forEach>
      			<h4>${entry.key.name}</h4>
            </a>
      			<div class="ratings">
                    <span class="glyphicon glyphicon-thumbs-up"></span> ${entry.key.likes}
                    <span class="glyphicon glyphicon-thumbs-down"></span> ${entry.key.dislikes}
                    <span class="price">${entry.key.price}</span>
                </div>
                    <c:set var="desc" value="${entry.key.description}"/>
                    <c:set var="size" value="${desc.length()}"/>
                    <c:choose>
                        <c:when test="${size > 100}">
                            <p>${fn:substring(desc, 0, 99)}...</p>
                        </c:when>
                        <c:otherwise>
                            <p>${desc}...</p>
                        </c:otherwise>
                    </c:choose>

      			<hr class="line">
      			<div class="row">
                    <div class="col-md-6 col-sm-6">
                        <form>
                            <c:url var="addToCart" value="/addToCart?sku=${entry.key.sku}"/>
                            <button formaction="${addToCart}" formmethod="post"
                                    <c:if test="${entry.key.amount <= 0}"><c:out value="disabled='disabled'"/></c:if>
                                    class="btn btn-primary left">
                                <span class="glyphicon glyphicon-shopping-cart"></span> Добавить
                            </button>
                        </form>
                    </div>
                    <div class="col-md-6 col-sm-6">
                        <form>
                            <button formaction="${productLink}/buyByOne?sku=${entry.key.sku}" formmethod="post"
                                    <c:if test="${entry.key.amount <= 0}"><c:out value="disabled='disabled'"/></c:if>
                                    class="btn btn-success right">
                                Купить
                            </button>
                        </form>
                    </div>
      			</div>
    		</span>
            </div>
        </c:forEach>
    </div>
</div>