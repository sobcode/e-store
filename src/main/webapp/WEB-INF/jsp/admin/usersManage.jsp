<%@ include file="/WEB-INF/jsp/includes/standartVariables.jsp" %>
<%@taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <tf:bootstrap/>
    <title><fmt:message key="manage_users" bundle="${bundle}"/></title>
    <style>
        <%@include file="/WEB-INF/css/table.css" %>
    </style>
</head>
<body>
<header>
    <tf:navbar/>
</header>
<main>
    <div class="container my-3">
        <table class="table table-light table-sortable" id="myTable">
            <thead>
            <tr>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="login" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="role" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.users}" var="user">
                <c:if test="${user.role.toString() != 'Admin'}">
                    <tr>
                        <td>
                            ${user.login}
                        </td>
                        <td>
                            ${user.role.toString()}
                        </td>
                        <td>
                            ${user.status.toString()}
                        </td>
                        <td class="d-flex justify-content-center">
                            <c:choose>
                                <c:when test="${user.status.toString() == 'Unblocked'}">
                                    <a class="btn btn-danger" href="${url}/updateUserStatus?userId=${user.id}&status=Blocked">
                                        <fmt:message key="block" bundle="${bundle}"/></a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-primary" href="${url}/updateUserStatus?userId=${user.id}&status=Unblocked">
                                    <fmt:message key="unblock" bundle="${bundle}"/></a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
</body>
<script>
    <%@ include file="/WEB-INF/js/TableScript.js"%>
</script>
</html>
