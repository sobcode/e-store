<%@ include file="/WEB-INF/jsp/includes/standartVariables.jsp" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
          crossorigin="anonymous"></script>
  <title><fmt:message key="sign_up" bundle="${bundle}"/></title>
  <style>
    <%@ include file="/WEB-INF/css/login.css"%>
  </style>
</head>
<body class="text-center">

<header>
  <header>
    <%@ include file="/WEB-INF/jsp/includes/navbar.jsp"%>
  </header>
</header>

<main class="form-signin">
  <form class="was-validated" method="post" autocomplete="off"
        action="${url}/registration">
    <h1 class="h3 mb-3 fw-normal"><fmt:message key="sign_up" bundle="${bundle}"/></h1>

    <div class="form-floating">
      <input type="text" class="form-control" id="floatingInput" name="login"
              pattern="(?=^)[a-zA-Z1-9_!#$%*]*(?=$)"
              onfocus="showWarning('loginWarning')"
              placeholder="<fmt:message key="login" bundle="${bundle}"/>" required>
        <label for="floatingInput"><fmt:message key="login" bundle="${bundle}"/></label>
        <div class="is-invalid invalid-feedback" id="loginWarning" hidden>
        <fmt:message key="invalid_login" bundle="${bundle}"/>
        </div>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" name="password"
              onfocus="showWarning('passwordWarning')"
              onchange="checkIfPasswordEquals()"
              pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}"
              placeholder="<fmt:message key="password" bundle="${bundle}"/>" required>
      <label for="floatingPassword"><fmt:message key="password" bundle="${bundle}"/></label>
      <div class="is-invalid invalid-feedback" id="passwordWarning" hidden>
          <fmt:message key="invalid_password" bundle="${bundle}"/>
      </div>
    </div>

    <div class="form-floating">
      <input type="password" class="form-control" id="floatingConfirmPassword" name="confirm_password"
              onfocus="showWarning('confirmPasswordWarning')"
              placeholder="<fmt:message key="confirm_password" bundle="${bundle}"/>" required>
      <label for="floatingConfirmPassword"><fmt:message key="confirm_password" bundle="${bundle}"/></label>
      <div class="is-invalid invalid-feedback" id="confirmPasswordWarning" hidden>
        <fmt:message key="password_not_equal" bundle="${bundle}"/>
      </div>
    </div>

    <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="sign_up" bundle="${bundle}"/></button>

    <c:if test="${sessionScope.loginAlreadyExist}">
      ${sessionScope.remove("loginAlreadyExist")}
      <div class="modal fade" tabindex="-1" id="myModal">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="error" bundle="${bundle}"/></h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <fmt:message key="login_exist" bundle="${bundle}"/>
            </div>
          </div>
        </div>
      </div>
    </c:if>
  </form>
</main>
<script>
  <%@ include file="/WEB-INF/js/LoginRegistration.js"%>
</script>
</body>
</html>
