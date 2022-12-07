<%@include file="includes/standartVariables.jsp"%>
<%@taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <tf:bootstrap/>
    <title><fmt:message key="main_page" bundle="${bundle}"/></title>
    <style>
        <%@include file="/WEB-INF/css/main.css"%>
    </style>
</head>
<body>

<header>
    <tf:navbar/>
</header>
<main>
    <img class="card-img-top" src="<c:url value="trainers.png"/>">
    <img src="trainers.png">
    <div class="container">
        <div class="d-flex justify-content-start card-header my-3">
            <div><fmt:message key="all_products" bundle="${bundle}"/></div>
        </div>
            <form class="d-flex justify-content-between" action="${url}/mainPage&page=${param.getOrDefault("page", "1")}" method="get">
                <div class="category">
                    <h4><fmt:message key="category" bundle="${bundle}"/></h4>
                    <c:forEach items="${requestScope.categories}" var="category">
                        <input type="checkbox" id="${category.name}" name="filterParam" value="category_ID=${category.id}" checked>
                        <label for="${category.name}">${category.name}</label><br/>
                    </c:forEach>
                </div>
                <div class="color">
                    <h4><fmt:message key="color" bundle="${bundle}"/></h4>
                    <c:forEach items="${requestScope.colors}" var="color">
                        <input type="checkbox" id="${color.color}" name="filterParam" value="color_ID=${color.id}" checked>
                        <label for="${color.color}">${color.color}</label><br/>
                    </c:forEach>
                </div>
                <div class="size">
                    <h4><fmt:message key="size" bundle="${bundle}"/></h4>
                    <c:forEach items="${requestScope.sizes}" var="size">
                        <input type="checkbox" id="${size.size}" name="filterParam" value="size_ID=${size.id}" checked>
                        <label for="${size.size}">${size.size}</label><br/>
                    </c:forEach>
                </div>
                <div class="sex">
                    <h4><fmt:message key="sex" bundle="${bundle}"/></h4>
                    <input type="checkbox" id="male" name="filterParam" value="sex='male'" checked>
                    <label for="male"><fmt:message key="male" bundle="${bundle}"/></label><br/>

                    <input type="checkbox" id="female" name="filterParam" value="sex='female'" checked>
                    <label for="female"><fmt:message key="female" bundle="${bundle}"/></label><br/>

                    <input type="checkbox" id="unisex" name="filterParam" value="sex='unisex'" checked>
                    <label for="unisex"><fmt:message key="unisex" bundle="${bundle}"/></label><br/>
                </div>
                <div>
                    <button type="submit" class="btn btn-sm btn-primary"><fmt:message key="filter" bundle="${bundle}"/></button>
                </div>
            </form>

        <div class="btn-group">
            <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                <fmt:message key="sort_by" bundle="${bundle}"/>
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item sortCards asc"
                       href="${url}/mainPage?sortBy=name&order=asc&page=${param.getOrDefault("page", "1")}">
                    <fmt:message key="by_name_high" bundle="${bundle}"/></a></li>
                <li><a class="dropdown-item sortCards asc"
                       href="${url}/mainPage?sortBy=name&order=desc&page=${param.getOrDefault("page", "1")}">
                    <fmt:message key="by_name_low" bundle="${bundle}"/></a></li>
                <li><a class="dropdown-item sortCards asc"
                       href="${url}/mainPage?sortBy=price&order=desc&page=${param.getOrDefault("page", "1")}">
                    <fmt:message key="by_price_high" bundle="${bundle}"/></a></li>
                <li><a class="dropdown-item sortCards asc"
                       href="${url}/mainPage?sortBy=price&order=asc&page=${param.getOrDefault("page", "1")}">
                    <fmt:message key="by_price_low" bundle="${bundle}"/></a></li>
                <li><a class="dropdown-item sortCards asc"
                       href="${url}/mainPage?sortBy=date&order=asc&page=${param.getOrDefault("page", "1")}">
                    <fmt:message key="by_date_high" bundle="${bundle}"/></a></li>
                <li><a class="dropdown-item sortCards asc"
                       href="${url}/mainPage?sortBy=date&order=desc&page=${param.getOrDefault("page", "1")}">
                    <fmt:message key="by_date_low" bundle="${bundle}"/></a></li>
            </ul>
        </div><br/>
        <div class="row">
            <c:forEach items="${products}" var="product">
                <dic class="col-md-3 my-3">
                    <div class="card w-100">
                        <img class="card-img-top" src="<c:url value="/product-image/${product.img}"/>"
                            alt="Product image" style="max-height: 300px;
                                                        min-height: 300px;
                                                        height: auto;
                                                        width: auto;">
                        <div class="card-body">
                            <h1 class="name" hidden>${product.name}</h1>
                            <h1 class="id" hidden>${product.id}</h1>
                            <h5 class="card-title">${product.name}</h5>
                            <h6><fmt:message key="price" bundle="${bundle}"/>:
                                <span class="price">${pageContext.request.getSession(false).getAttribute("lang") == 'ukr'
                                ? 41 * product.price : product.price}</span>
                                <fmt:message key="currency" bundle="${bundle}"/></h6>
                            <div class="mt-3 d-flex justify-content-between">
                                <form method="post" action="${url}/addToCart?productId=${product.id}">
                                    <button type="submit"
                                            class="btn btn-dark">
                                            <fmt:message key="add_to_cart" bundle="${bundle}"/>
                                    </button>
                                </form>
                            </div>
                        </div>

                    </div>
                </dic>
            </c:forEach>
        </div>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <c:forEach items="${requestScope.pages}" var="page">
                    <li class="page-item"><a class="page-link" href="${url}/mainPage?page=${page}">${page}</a></li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</main>
</body>
</html>
