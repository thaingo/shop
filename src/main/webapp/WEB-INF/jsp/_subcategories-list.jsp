<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
                            <sec:authorize access="hasRole('ADMIN')">
                                <c:url var="edit" value="/admin/edit/category/${entry.getKey().getId()}"/>
                                <a href="${edit}">Редактировать</a>
                            </sec:authorize>
                        </td>
                    </c:if>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </c:if>
</table>