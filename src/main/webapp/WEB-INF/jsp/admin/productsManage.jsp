<%@ include file="/WEB-INF/jsp/includes/standartVariables.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <title><fmt:message key="manage_products" bundle="${bundle}"/></title>
    <style>
        <%@include file="/WEB-INF/css/table.css" %>
    </style>
</head>
<body>
<header>
    <%@ include file="/WEB-INF/jsp/includes/navbar.jsp"%>
</header>
<main>
    <div class="container my-3">
        <div class="d-flex justify-content-between">
            <input type="search" id="search" onkeyup="search()" class="form-control rounded search"
                   placeholder="<fmt:message key="search_by_name" bundle="${bundle}"/>"
                   aria-label="Search" aria-describedby="search-addon"/>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal"
                    style="margin-bottom: 10px">
                <fmt:message key="add" bundle="${bundle}"/>
            </button>
        </div>

        <table class="table table-light table-sortable" id="myTable">
            <thead>
            <tr>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="name" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="category" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="price" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="size" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="color" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="sex" bundle="${bundle}"/></th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.products}" var="product">
                <tr>
                    <td>
                        ${product.name}
                    </td>
                    <td>
                        ${product.category.name}
                    </td>
                    <td class="price">
                        ${pageContext.request.getSession(false).getAttribute("lang") == 'ukr' ? 41 * product.price : product.price}
                        <fmt:message key="currency" bundle="${bundle}"/>
                    </td>
                    <td>
                        ${product.size.size}
                    </td>
                    <td>
                        ${product.color.color}
                    </td>
                    <td>
                        ${product.sex.toString()}
                    </td>
                    <td>
                        <a class="updateUrl"><fmt:message key="update" bundle="${bundle}"/></a>
                    </td>
                    <td>
                        <a href="${url}/deleteProduct?productId=${product.id}" class="btn btn-sm btn-danger">
                            <fmt:message key="delete" bundle="${bundle}"/>
                        </a>
                    </td>
                </tr>
                <tr class="update" hidden>
                    <form action="${url}/updateProduct?productId=${product.id}" method="post" autocomplete="off">
                        <td>
                            <div class="input-group">
                                <input class="form-control" type="text" name="name" value="${product.name}" required
                                placeholder="<fmt:message key="name" bundle="${bundle}"/>">
                            </div>
                        </td>
                        <td>
                            <select class="form-select" name="category" required>
                                <c:forEach items="${requestScope.categories}" var="category">
                                    <option <c:if test="${product.category.name == category.name}">selected </c:if>
                                    value="${category.id}">${category.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <div class="input-group">
                                <input class="form-control" type="text" name="price" pattern="\d+"
                                value="${product.price}" required placeholder="<fmt:message key="price" bundle="${bundle}"/>">
                            </div>
                        </td>
                        <td>
                            <select class="form-select" name="size" required>
                                <c:forEach items="${requestScope.sizes}" var="size">
                                    <option <c:if test="${product.size.size == size.size}">selected </c:if>
                                    value="${size.id}">${size.size}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select class="form-select" name="color" required>
                                <c:forEach items="${requestScope.colors}" var="color">
                                    <option <c:if test="${product.color.color == color.color}">selected </c:if>
                                    value="${color.id}">${color.color}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select class="form-select" name="sex" required>
                                <option <c:if test="${product.sex.toString() == 'Male'}">selected </c:if>
                                value="Male">Male
                                </option>
                                <option <c:if test="${product.sex.toString() == 'Female'}">selected </c:if>
                                        value="Female">Female
                                </option>
                                <option <c:if test="${product.sex.toString() == 'Unisex'}">selected </c:if>
                                        value="Unisex">Unisex
                                </option>
                            </select>
                        </td>
                        <td>
                            <button class="w-100 btn btn-sm btn-primary" type="submit">
                                <fmt:message key="update" bundle="${bundle}"/></button>
                        </td>
                    </form>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="modal fade" tabindex="-1" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="add" bundle="${bundle}"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="${url}/addProduct" method="post" enctype="multipart/form-data" autocomplete="off">
                        <select class="form-select" name="category" required>
                            <option disabled selected hidden><fmt:message key="category" bundle="${bundle}"/></option>
                            <c:forEach items="${requestScope.categories}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select><br/>
                        <select class="form-select" name="color" required>
                            <option disabled selected hidden><fmt:message key="color" bundle="${bundle}"/></option>
                            <c:forEach items="${requestScope.colors}" var="color">
                                <option value="${color.id}">${color.color}</option>
                            </c:forEach>
                        </select><br/>
                        <select class="form-select" name="size" required>
                            <option disabled selected hidden><fmt:message key="size" bundle="${bundle}"/></option>
                            <c:forEach items="${requestScope.sizes}" var="size">
                                <option value="${size.id}">${size.size}</option>
                            </c:forEach>
                        </select><br/>
                        <select class="form-select" name="sex" required>
                            <option disabled selected hidden><fmt:message key="sex" bundle="${bundle}"/></option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Unisex">Unisex</option>
                        </select><br/>
                        <div class="input-group">
                            <input class="form-control" type="text" name="price" pattern="\d+" required
                                   placeholder="<fmt:message key="price" bundle="${bundle}"/>">
                        </div><br/>
                        <div class="input-group">
                            <input class="form-control" type="text" name="name" required
                                   placeholder="<fmt:message key="name" bundle="${bundle}"/>">
                        </div><br/>
                        <div class="input-group">
                            <input class="form-control" type="file" name="image"
                                    accept="image/png, image/gif, image/jpeg" required>
                        </div><br/>
                        <button class="w-100 btn btn-lg btn-primary" type="submit">
                            <fmt:message key="accept" bundle="${bundle}"/></button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
<script>
    <%@include file="/WEB-INF/js/TableScript.js" %>
    <%@include file="/WEB-INF/js/ProductsManage.js" %>
</script>
</html>
