<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container text-xs-center">
    <div class="text-xs-center">
        <ul class="pagination">
            <c:choose>
                <c:when test="${currentPage >= 2}">
                    <c:set var="start" value="${currentPage - 2}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="start" value="${currentPage}"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${start + 4 <= totalPages}">
                    <c:set var="end" value="${start + 4}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="end" value="${totalPages}"/>
                </c:otherwise>
            </c:choose>
            <c:url var="link" value="/category/${category.getName()}">
                <c:param name="page" value="${currentPage - 1}"/>
                <c:param name="size" value="${offset}"/>
            </c:url>
            <c:choose>
                <c:when test="${hasPrevious}">
                    <li><a href="${link}"><i class="glyphicon glyphicon-chevron-left"></i></a></li>
                </c:when>
                <c:otherwise>
                    <li class="disabled"><a><i class="glyphicon glyphicon-chevron-left"></i></a></li>
                </c:otherwise>
            </c:choose>
            <c:forEach var="i" begin="${start}" end="${end}" step="1">
                <c:url var="link" value="/category/${category.getName()}">
                    <c:param name="page" value="${i}"/>
                    <c:param name="size" value="${offset}"/>
                </c:url>
                <c:choose>
                    <c:when test="${i eq currentPage}">
                        <li class="active">
                            <a href="${link}">${i}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="${link}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:url var="link" value="/category/${category.getName()}">
                <c:param name="page" value="${currentPage + 1}"/>
                <c:param name="size" value="${offset}"/>
            </c:url>
            <c:choose>
                <c:when test="${hasNext}">
                    <li><a href="${link}"><i class="glyphicon glyphicon-chevron-right"></i></a></li>
                </c:when>
                <c:otherwise>
                    <li class="disabled"><a><i class="glyphicon glyphicon-chevron-right"></i></a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>