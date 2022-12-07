<%@ include file="includes/standartVariables.jsp" %>
<%@taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <tf:bootstrap/>
    <title><fmt:message key="error" bundle="${bundle}"/></title>
</head>
<body>
<div class="container">
    <div class="align-items-center m-5">
        <div class="alert alert-danger" role="alert">
            <h2 class="alert-heading text-center"><fmt:message key="block_error" bundle="${bundle}"/>!</h2>
            <hr>
            <p class="mb-0 h5 text-center"><a href="${url}/mainPage"><fmt:message key="main_page" bundle="${bundle}"/></a></p>
        </div>
    </div>
</div>
</body>
</html>