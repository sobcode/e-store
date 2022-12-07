<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/WEB-INF/customTags/priceTag.tld" %>

<fmt:setLocale value='${pageContext.request.getSession(false).getAttribute("lang")}'/>
<fmt:setBundle basename="translate" var="bundle"/>

<c:url value="" var="EnLang">
    <c:param name="lang" value="en"/>
</c:url>

<c:url value="" var="UkrLang">
       <c:param name="lang" value="ukr"/>
</c:url>

<c:set var="user" value="${sessionScope.user}"/>

<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <c:set var="role" value="${sessionScope.user.role.toString().toLowerCase()}"/>
    </c:when>
    <c:otherwise>
        <c:set var="role" value="guest"/>
    </c:otherwise>
</c:choose>

<c:set var="url" value="${pageContext.request.contextPath}/${role}"/>


