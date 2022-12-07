<%@ include file="/WEB-INF/jsp/includes/standartVariables.jsp" %>
<%@taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <tf:bootstrap/>
    <title><fmt:message key="orders" bundle="${bundle}"/></title>
    <style>
        <%@ include file="/WEB-INF/css/table.css"%>
    </style>
</head>
<body>
<header>
    <tf:navbar/>
</header>
<main>
    <div class="container my-3">
        <div class="d-flex py-3">
            <h3 id="totalPrice"><fmt:message key="total_price" bundle="${bundle}"/>:
            <fmt:message key="currency" bundle="${bundle}"/></h3>
        </div>
        <table class="table table-light table-sortable">
            <thead>
            <tr>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="name" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="category" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="price" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.orders}" var="order">
                <tr>
                    <td>
                        ${order.product.name}
                    </td>
                    <td>
                        ${order.product.category.name}
                    </td>
                    <td class="price">
                        ${pageContext.request.getSession(false).getAttribute("lang") == 'ukr' ?
                        41 * order.product.price * order.quantity : order.product.price * order.quantity}
                        <fmt:message key="currency" bundle="${bundle}"/>
                    </td>
                    <td>
                        <fmt:message key="${order.status.toString().toLowerCase()}" bundle="${bundle}"/>
                    </td>
                    <td>
                        <c:if test="${order.status == 'Registered'}">
                            <form method="post" action="${url}/cancelRegisteredOrder?action=remove&orderId=${order.id}">
                                <button type="submit" class="btn btn-sm btn-danger">
                                    <fmt:message key="cancel" bundle="${bundle}"/></button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
</body>
<script>
    <%@include file="/WEB-INF/js/TableScript.js" %>
</script>
</html>
