<%@include file="/WEB-INF/jsp/includes/standartVariables.jsp"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="${url}/mainPage"><fmt:message key="online_shop" bundle="${bundle}"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <c:if test="${role != 'admin'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${url}/cartPage"><fmt:message key="cart" bundle="${bundle}"/></a>
                    </li>
                </c:if>
                <c:if test="${role == 'admin'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${url}/productsManagePage"><fmt:message key="products" bundle="${bundle}"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${url}/ordersManagePage"><fmt:message key="orders" bundle="${bundle}"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${url}/usersManagePage"><fmt:message key="users" bundle="${bundle}"/></a>
                    </li>
                </c:if>
                <c:if test="${role == 'user'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${url}/ordersPage"><fmt:message key="orders" bundle="${bundle}"/></a>
                    </li>
                </c:if>
                <c:if test="${role != 'guest'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${url}/logout"><fmt:message key="logout" bundle="${bundle}"/></a>
                    </li>
                </c:if>
                <c:if test="${role == 'guest'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${url}/login"><fmt:message key="sign_in" bundle="${bundle}"/></a>
                    </li>
                </c:if>
            </ul>
        </div>
        <div class="localizationBtn">
            <a class="link-primary" href="${EnLang}">EN</a>
            <a class="link-primary" href="${UkrLang}">UKR</a>
        </div>
    </div>
</nav>