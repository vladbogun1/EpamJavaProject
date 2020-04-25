<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Log in</title>
    <jsp:include page="/WEB-INF/files/bootstrap.html"/>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
</head>
<body>
    <%@include file="/WEB-INF/files/lang.jspf" %>
    <div class="container">
        <% if (request.getAttribute("disconnected") != null) {%>
        <div class="alert alert-danger">
            <strong><fmt:message key="h.label.accessdenied"/></strong>
            <fmt:message key="h.label.sessionisended"/>
        </div>
        <%}%>
        <div class="row justify-content-center text-center padding-3">
            <img class="logo" src="${pageContext.request.contextPath}/img/epam_logo.png" alt="">
        </div>
        <div class="col-lg-12 slides padding-3">


            <div class="col-lg-12 slide block-neomorph padding-3" style="display: none;">
                <div class="row justify-content-center">
                    <button onclick="display();" class="button-close button-neomorph"><span>×</span></button>
                    <form action="loginUser" onsubmit="return false;"
                    class="col-lg-4 col-lg-push-4 bs-example bs-example-form text-center" method="post">

                    <div class="form-group">
                        <h2><fmt:message key="h.label.loginas"/> <span id="roleText"><fmt:message key="h.label.doctor"/></span></h2>
                        <input class="form-control" id="exampleInputRole" type="hidden" name="role">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail"><fmt:message key="h.label.login"/></label>
                        <input class="form-control" id="exampleInputEmail" placeholder="login" value="admin"
                        type="text" name="login">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword"><fmt:message key="h.label.password"/></label>
                        <input type="password" class="form-control" name="password" id="exampleInputPassword"
                        placeholder="Password" value="vladbogun">
                    </div>

                    <button type="submit" class="button button-neomorph padding-3 mw-70" onclick="load(this);authorize(this);"><span><fmt:message key="button.lable.login"/></span><div style="display: none;" id="loader"></div></button>
                </form>
            </div>
        </div>


        <div class="row justify-content-center slide cards text-center">

            <button onclick="display(); role(2,this);" class="col-lg-3 col-lg-offset-1 button button-neomorph">
                <img src="/img/nurse.png" alt="nurse">
                <h2><fmt:message key="h.label.nurse"/></h2>
            </button>

            <button onclick="display(); role(1,this);" class="col-lg-3 col-lg-offset-1 button button-neomorph">
                <img src="/img/doctor.png" alt="doctor">
                <h2><fmt:message key="h.label.doctor"/></h2>
            </button>

            <button onclick="display(); role(0,this);" class="col-lg-3 col-lg-offset-1 button button-neomorph">
                <img src="/img/admin.png" alt="admin">
                <h2><fmt:message key="h.label.admin"/></h2>
            </button>

        </div>


    </div>
</div>


<div class="body"></div>

<div class="lang-buttons">
    <a class="button button-neomorph" href="?language=ru">Ru-ru</a>
    <a class="button button-neomorph" href="?language=en">En-en</a>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>
<script>
    function role(arg1, arg2) {
        document.querySelector("#exampleInputRole").value = arg1;
        document.querySelector("#roleText").innerText = arg2.innerText.replace(/\s{2,}/g, ' ');
    }

    function display() {
        document.querySelectorAll(".slide").forEach((slide) => {
            if (slide.style.display == "none") {
                slide.style.display = "";
            } else {
                slide.style.display = "none";
            }
        });
    }

    function load(a) {
        a.childNodes.forEach((slide) => {
            if (slide.style.display == "none") {
                slide.style.display = "";
            } else {
                slide.style.display = "none";
            }
        });
    }

    function authorize(button) {
        let role = document.getElementsByName("role")[0].value;
        let login = document.getElementsByName("login")[0].value;
        let password = document.getElementsByName("password")[0].value;
        let request = new XMLHttpRequest();
        request.open("POST", "/logUser?role=" + role + "&login=" + login + "&password=" + password, true);

        request.onload = () => {
            load(button);
            switch (request.status) {
                case 200:
                location.href = "/";
                break;
                case 204:
                alert("Incorrect password");
                break;
                case 400:
                alert("Incorrect login");
                break;
                default:
                alert("Server error");

            }

        };

        request.send();
    }



</script>
</body>

</html>