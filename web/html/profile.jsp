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
    <title>Profile</title>
    <%@include file="/WEB-INF/jspf/lang.jspf" %>
    <jsp:include page="/WEB-INF/files/bootstrap.html"/>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet">
  </head>
  <body>
    <%@include file="/WEB-INF/jspf/header.jspf" %>

    <c:if test="${profiler.getClass().name == 'main.java.ua.nure.bogun.epammed.entities.Patient'}">
    <c:set var="role" value="patient"/>
  </c:if>
  <c:if test="${profiler.getClass().name == 'main.java.ua.nure.bogun.epammed.entities.User'}">
  <c:set var="role" value="worker"/>
</c:if>

<div class="container bootstrap snippet">
  <div class="row">
    <div class="col-sm-10"><h1>${profiler.getFirstName()} ${profiler.getLastName()}</h1></div>

  </div>
  <div class="row">
    <div class="col-sm-3"><!--left col-->

      <div class="text-center">
        <img src="${profiler.getImage()}" class="avatar img-circle img-thumbnail" alt="avatar">
      </div>
      <br>

      <ul class="list-group">
        <li class="list-group-item block-neomorph text-right"><span class="pull-left"><strong><fmt:message
          key="h.label.meetingscount"/></strong></span> ${meetings.size()}</li>
        </ul>

      </div><!--/col-3-->


      <div class="col-sm-9">

        <div class="nav nav-tabs block-neomorph justify-content-center" id="nav-tab" role="tablist">
          <a class="nav-item nav-link active" id="nav-profile-tab" data-toggle="tab" href="#nav-profile"
          role="tab" aria-controls="nav-profile" aria-selected="true"><fmt:message
          key="header.label.profile"/></a>
          <a class="nav-item nav-link" id="nav-meetings-tab" data-toggle="tab" href="#nav-meetings" role="tab"
          aria-controls="nav-meetings" aria-selected="false"><fmt:message key="h.label.meetinghistory"/></a>
          <a class="nav-item nav-link" id="nav-card-tab" data-toggle="tab" href="#nav-card" role="tab"
          aria-controls="nav-card" aria-selected="false"><fmt:message key="h.label.cardhistory"/></a>
        </div>


        <div class="tab-content">
          <div class="tab-pane active" id="nav-profile">

            <form onsubmit="return false;" class="form block-neomorph" action="" method="post" id="registrationForm">
              
              <input type="hidden" id="role" name="role" value="${role}">
              <input type="hidden" id="uid" name="uid" value="${profiler.getId()}">

              <div class="form-group">
                <div class="col-xs-6">
                  <label for="first_name"><h4><fmt:message key="label.firstname"/></h4></label>
                  <input type="text" value="${profiler.getFirstName()}" class="form-control"
                  name="first_name" id="first_name" placeholder="first name"
                  title="enter your first name if any.">
                </div>
              </div>

              <div class="form-group">
                <div class="col-xs-6">
                  <label for="last_name"><h4><fmt:message key="label.lastname"/></h4></label>
                  <input type="text" value="${profiler.getLastName()}" class="form-control"
                  name="last_name" id="last_name" placeholder="last name"
                  title="enter your last name if any.">
                </div>
              </div>

              <c:if test="${role == 'worker'}">
              <div class="form-group">
                <div class="col-xs-6">
                  <label for="specialization"><h4><fmt:message key="h.label.specialization"/></h4>
                  </label>
                  <input type="text" value="${profiler.getSpecialization().getSpecializationName()}"
                  class="form-control" name="specialization" id="specialization"
                  placeholder="enter specialization" title="enter your specialization if any.">
                </div>
              </div>
            </c:if>
            <c:if test="${role == 'patient'}">
            <div class="form-group">
              <div class="col-xs-6">
                <label for="birthday"><h4><fmt:message key="h.label.birthday"/></h4></label>
                <input type="text" value="${profiler.getBirthday().toString()}" class="form-control"
                name="birthday" id="birthday" placeholder="enter specialization"
                title="enter your specialization if any.">
              </div>
            </div>
          </c:if>

          <c:if test="${editable}">
          <div class="form-group">
            <div class="col-xs-12 edit-buttons">
              <button class="btn btn-lg btn-outline-success" style="display: none;" type="button" onclick="load(this);getUpdate(this);"><span><fmt:message
                key="button.lable.save"/></span><div style="display: none;" id="loader"></div></button>
                <button class="btn btn-lg btn-outline-danger" onclick="edit()" type="button"><fmt:message
                  key="button.lable.reset"/></button>

                </div>
              </div>
            </c:if>
          </form>


        </div><!--/tab-pane-->
        <div class="tab-pane" id="nav-meetings">

          <table class="table table-striped">
            <tr>
              <th><fmt:message key="h.label.patient" /></th>
              <th><fmt:message key="h.label.doctor" /></th>
              <th><fmt:message key="h.label.specialization" /></th>
              <th><fmt:message key="h.label.day" /></th>
            </tr>
            <c:forEach var="meeting" items="${meetings}">
            <tr id="${meeting.getId()}">
              <td>${meeting.getPatient().getFirstName()} ${meeting.getPatient().getLastName()}</td>
              <td>${meeting.getUser().getFirstName()} ${meeting.getUser().getLastName()}</td>
              <td>${meeting.getUser().getSpecialization().getSpecializationName()}</td>
              <td>${meeting.getDate().toString()}</td>
            </tr>
          </c:forEach>
        </table>


      </div><!--/tab-pane-->
      <div class="tab-pane" id="nav-card">


        <hr>

      </div>

    </div><!--/tab-pane-->
  </div><!--/tab-content-->

</div><!--/col-9-->
</div>


<script>

  function load(a) {
    a.childNodes.forEach((slide) => {
      if (slide.style.display == "none") {
        slide.style.display = "";
      } else {
        slide.style.display = "none";
      }
    });
  }
  function getUpdate(button){
    var stringPath = "/profile?";
    var role = $("#role")[0].value;
    stringPath+="role=" + role + "&";
    var id = $("#uid")[0].value;
    stringPath+="id=" + id + "&";
    var fname = $("#first_name")[0].value;
    stringPath+="first_name=" + fname + "&";
    var lname = $("#last_name")[0].value;
    stringPath+="last_name=" + lname + "&";
    if($("#specialization").length > 0){
      var specialization = $("#specialization")[0].value;
      stringPath+="specialization=" + specialization + "&";
    }
    if($("#birthday").length > 0){
      var birthday = $("#birthday")[0].value;
      stringPath+="birthday=" + birthday + "&";
    }


    let request = new XMLHttpRequest();
    request.open("POST", stringPath, true);

    request.onload = () => {
      load(button);
      switch (request.status) {
        case 200:
          alert("All data was saved");
          break;
        case 400:
        alert("Incorrect data");
        break;
        default:
        alert("Server error");
        break;

      }
      edit();

    };

    request.send();
  }

  function text() {
    $('input').each(function () {
      var b = $(this);
      if (b.hasClass("text")) {
        b.removeClass("text")
        b.prop("disabled", false);
      } else {
        b.addClass("text")
        b.prop("disabled", true);
      }
    });
  }

  text();

  function edit() {
    text();
    $(".edit-buttons").children().each(function () {
      var b = $(this)[0];
      if (b.style.display === "none") {
        b.style.display = "inline-block";
      } else {
        b.style.display = "none";
      }
    });
  }
</script>

<div class="body"></div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>

</body>
</html>