<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="ie=edge" />
	<title>404</title>
	<%@include file="/WEB-INF/jspf/lang.jspf" %>
	<jsp:include page="/WEB-INF/files/bootstrap.html"/>
	<link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
	<style type="text/css">
		.error{
			position: fixed;
			top:0;
			bottom: 0;
			left: 0;
			right: 0;
			display: flex;
			justify-content: center;
			align-items: center;
		}
	</style>
</head>
<body>
	<%@include file="/WEB-INF/jspf/header.jspf" %>
	<div class="error">
	<img src="${pageContext.request.contextPath}/img/error-404-page.png">
	</div>
	<div class="body"></div>
	<%@include file="/WEB-INF/jspf/footer.jspf" %>
</body>

</html>