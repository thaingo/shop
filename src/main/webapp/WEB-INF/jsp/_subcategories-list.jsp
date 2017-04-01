<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table style="width:100%">
    <c:if test="${entries.size() > 0}">
        <c:forEach var="i" begin="0" end="${entries.size() - 1}" step="${step}">
            <tr>
                    <%--<c:set var="stop" value="${entities.size()}"/>
                    <c:if test="${i + step - 1 < entities.size()}">
                        <c:set var="stop" value="${i + step - 1}"/>
                    </c:if>
                    <c:forEach var="j" begin="${i}" end="${stop}" step="1">
                        <td>${entries.get(j).getKey().getName()} (${entries.get(j).getValue()})</td>
                    </c:forEach>--%>
                <c:forEach var="j" begin="${i}" end="${i + step - 1}" step="1">
                    <c:if test="${j < entries.size()}">
                        <td>
                            <c:set var="entry" value="${entries.get(j)}"/>
                            <a href="${pageContext.request.contextPath}/category/${entry.getKey().getName()}">
                                    ${entry.getKey().getName()} (${entry.getValue()})
                            </a>
                        </td>
                    </c:if>
                </c:forEach>
            </tr>
        </c:forEach>
    </c:if>
</table>