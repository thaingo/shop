<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table-responsive table">
    <c:if test="${entries.size() > 0}">
        <tbody>
        <c:forEach var="i" begin="0" end="${entries.size() - 1}" step="${step}">
            <tr>
                <c:forEach var="j" begin="${i}" end="${i + step - 1}" step="1">
                    <c:if test="${j < entries.size()}">
                        <td>
                            <c:set var="entry" value="${entries.get(j)}"/>
                            <c:url var="link" value="/category/${entry.getKey().getName()}"/>
                            <a href="${link}">${entry.getKey().getName()} (${entry.getValue()})</a>
                        </td>
                    </c:if>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </c:if>
</table>