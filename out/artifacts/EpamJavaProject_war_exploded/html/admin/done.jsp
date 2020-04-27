<%--
  Created by IntelliJ IDEA.
  User: vladbogun
  Date: 24.04.2020
  Time: 15:09
  To change this template use File | Settings | File Templates.
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>MainPage</title>
    <%@include file="/WEB-INF/jspf/lang.jspf" %>
    <jsp:include page="/WEB-INF/files/bootstrap.html"/>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/mainpageAdmin.css" rel="stylesheet">
</head>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>





<div class="container">

    <table class="table table-striped">
        <tr>
            <th><fmt:message key="h.label.doctor" /></th>
            <th><fmt:message key="h.label.specialization" /></th>
            <th>COUNT DONE</th>
        </tr>
        <c:forEach var="done" items="${doneTable}">
            <tr id="${done.key.getId()}">
                <td>${done.key.getFirstName()} ${done.key.getLastName()}</td>
                <td>${done.key.getSpecialization().getSpecializationName()}</td>
                <td>${done.value}</td>
            </tr>
        </c:forEach>
    </table>
</div>







<div class="body"></div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>

</body>
</html>