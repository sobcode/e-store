<%@ include file="/WEB-INF/jsp/includes/standartVariables.jsp" %>
<%@taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <tf:bootstrap/>
    <title><fmt:message key="manage_orders" bundle="${bundle}"/></title>
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
        <div class="input-group rounded">
            <input type="search" id="search" onkeyup="search()" class="form-control rounded search"
                   placeholder="<fmt:message key="search_by_login" bundle="${bundle}"/>" aria-label="Search"
                   aria-describedby="search-addon"/>
        </div>
        <table class="table table-light table-sortable" id="myTable">
            <thead>
            <tr>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="login" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="product" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="price" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="quantity" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.usersToOrders}" var="order">
                <c:if test="${order.status.toString().toLowerCase() != 'unregistered'}">
                    <tr>
                        <td>
                                ${order.user.login}
                        </td>
                        <td>
                                ${order.product.name}
                        </td>
                        <td class="price">
                                ${order.product.price}
                        </td>
                        <td>
                                ${order.quantity}
                        </td>
                        <form action="${url}/updateOrderStatus?orderId=${order.id}" method="post">
                            <td>
                                <select class="form-select" name="status" required>
                                    <option value="Registered"
                                            <c:if test="${order.status.toString().toLowerCase() == 'registered'}">selected
                                            hidden </c:if>><fmt:message key="registered" bundle="${bundle}"/></option>
                                    <option value="Paid"
                                            <c:if test="${order.status.toString().toLowerCase() == 'paid'}">selected
                                            hidden </c:if>><fmt:message key="paid" bundle="${bundle}"/></option>
                                    <option value="Canceled"
                                            <c:if test="${order.status.toString().toLowerCase() == 'canceled'}">selected
                                            hidden </c:if>><fmt:message key="canceled" bundle="${bundle}"/></option>
                                </select>
                            </td>
                            <td>
                                <button type="submit" class="btn btn-sm btn-primary">
                                    <fmt:message key="update" bundle="${bundle}"/></button>
                            </td>
                        </form>
                        <td>
                            <a href="${url}/deleteOrder?orderId=${order.id}" class="btn btn-sm btn-danger">
                                <fmt:message key="cancel" bundle="${bundle}"/></a>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            <%--<c:forEach items="${requestScope.usersToOrders}" var="userToOrders">
                <c:forEach items="${userToOrders.value}" var="order">
                    <c:if test="${order.status.toString().toLowerCase() != 'unregistered'}">
                        <tr>
                            <td>
                                ${userToOrders.key.login}
                            </td>
                            <td>
                                ${order.product.name}
                            </td>
                            <td class="price">
                                ${order.product.price}
                            </td>
                            <td>
                                ${order.quantity}
                            </td>
                            <form action="${url}/updateOrderStatus?orderId=${order.id}" method="post">
                                <td>
                                    <select class="form-select" name="status" required>
                                        <option value="Registered"
                                                <c:if test="${order.status.toString().toLowerCase() == 'registered'}">selected
                                                hidden </c:if>><fmt:message key="registered" bundle="${bundle}"/></option>
                                        <option value="Paid"
                                                <c:if test="${order.status.toString().toLowerCase() == 'paid'}">selected
                                                hidden </c:if>><fmt:message key="paid" bundle="${bundle}"/></option>
                                        <option value="Canceled"
                                                <c:if test="${order.status.toString().toLowerCase() == 'canceled'}">selected
                                                hidden </c:if>><fmt:message key="canceled" bundle="${bundle}"/></option>
                                    </select>
                                </td>
                                <td>
                                    <button type="submit" class="btn btn-sm btn-primary">
                                        <fmt:message key="update" bundle="${bundle}"/></button>
                                </td>
                            </form>
                            <td>
                                <a href="${url}/deleteOrder?orderId=${order.id}" class="btn btn-sm btn-danger">
                                    <fmt:message key="cancel" bundle="${bundle}"/></a>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:forEach>--%>
            </tbody>
        </table>
    </div>
</main>
</body>
<script>
    <%@include file="/WEB-INF/js/TableScript.js" %>
</script>
</html>
