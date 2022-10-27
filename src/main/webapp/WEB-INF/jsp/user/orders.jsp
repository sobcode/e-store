<%@ include file="/WEB-INF/jsp/includes/standartVariables.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <title><fmt:message key="orders" bundle="${bundle}"/></title>
    <style>
        <%@ include file="/WEB-INF/css/table.css"%>
    </style>
</head>
<body>
<header>
    <%@ include file="/WEB-INF/jsp/includes/navbar.jsp"%>
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
