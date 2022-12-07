<%@ include file="/WEB-INF/jsp/includes/standartVariables.jsp" %>
<%@taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<html>
<head>
  <tf:bootstrap/>
  <title><fmt:message key="sign_in" bundle="${bundle}"/></title>
  <style>
    <%@ include file="/WEB-INF/css/login.css"%>
  </style>
</head>
<body>

<header>
  <header>
    <tf:navbar/>
  </header>
</header>

<main class="form-signin">
  <form autocomplete="off" method="post" action="${url}/login" class="text-center">
    <h1 class="h3 mb-3 fw-normal"><fmt:message key="sign_in" bundle="${bundle}"/></h1>

    <div class="form-floating">
      <input type="text" class="form-control" id="floatingInput" name="login" required
             placeholder="<fmt:message key="login" bundle="${bundle}"/>">
      <label for="floatingInput"><fmt:message key="login" bundle="${bundle}"/></label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" name="password" required
             placeholder="<fmt:message key="password" bundle="${bundle}"/>">
      <label for="floatingPassword"><fmt:message key="password" bundle="${bundle}"/></label>
    </div>

    <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="sign_in" bundle="${bundle}"/></button>
    <a href="${url}/registration"><fmt:message key="sign_up" bundle="${bundle}"/></a>

    <c:if test="${sessionScope.error}">
      ${sessionScope.remove("error")}
      <div class="modal fade" tabindex="-1" id="myModal">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="error" bundle="${bundle}"/></h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <fmt:message key="${sessionScope.errorType}" bundle="${bundle}"/>${sessionScope.remove("errorType")}
            </div>
          </div>
        </div>
      </div>
    </c:if>
  </form>
</main>
<script>
  <%@include file="/WEB-INF/js/LoginRegistration.js" %>
</script>
</body>
</html>