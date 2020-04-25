<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="ie=edge" />
	<title>Log in</title>
	<jsp:include page="/WEB-INF/files/lang.jspf"/>
	<jsp:include page="/WEB-INF/files/bootstrap.html" />
	<style type="text/css">
		body{
			height: 100vh;
			display: flex;
			justify-content: center;
			align-items: center;
		}
	</style>
</head>
<body>
	<img src="${pageContext.request.contextPath}/img/error-404-page.png">
</body>

</html>