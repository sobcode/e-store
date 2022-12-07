<%@ include file="/WEB-INF/jsp/includes/standartVariables.jsp" %>
<%@taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <tf:bootstrap/>
    <title><fmt:message key="cart" bundle="${bundle}"/></title>
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
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${cart}" var="order">
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
                        <ct:priceTag/>
                    </td>
                    <td>
                        <input type="hidden" name="id" value="${order.product.id}" class="form-input">
                        <div class="form-group d-flex justify-content-between">
                            <form method="post" action="${url}/changeProductQuantity?action=increment&productId=${order.product.id}">
                                <button type="submit" class="quantity-left-minus btn btn-success btn-number">+</button>
                            </form>
                            <input type="text" name="quantity" class="form-control" value="${order.quantity}" readonly>
                            <form method="post" action="${url}/changeProductQuantity?action=decrement&productId=${order.product.id}">
                                <button type="submit" class="quantity-left-minus btn btn-danger btn-number">-</button>
                            </form>
                        </div>
                    </td>
                    <td>
                        <form method="post" action="${url}/changeProductQuantity?action=remove&productId=${order.product.id}">
                            <button type="submit" class="btn btn-sm btn-danger"><fmt:message key="delete" bundle="${bundle}"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${not empty requestScope.cart}">
            <div class="d-flex justify-content-end">
                <a href="${url}/buyFromCart" class="btn btn-primary"><fmt:message key="buy" bundle="${bundle}"/></a>
            </div>
        </c:if>
    </div>
</main>
</body>
<script>
    <%@ include file="/WEB-INF/js/TableScript.js"%>
</script>
</html>
