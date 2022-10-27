<%@ include file="includes/standartVariables.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <title><fmt:message key="error" bundle="${bundle}"/></title>
</head>
<body>
<div class="container">
    <div class="align-items-center m-5">
        <div class="alert alert-danger" role="alert">
            <h2 class="alert-heading text-center"><fmt:message key="rights_error" bundle="${bundle}"/>!</h2>
            <hr>
            <p class="mb-0 h5 text-center"><a href="${url}/mainPage"><fmt:message key="main_page" bundle="${bundle}"/></a></p>
        </div>
    </div>
</div>
</body>
</html>
