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
    <%@include file="/WEB-INF/files/lang.jspf" %>
    <jsp:include page="/WEB-INF/files/bootstrap.html"/>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/mainpageAdmin.css" rel="stylesheet">
  </head>
  <body>
    <%@include file="/WEB-INF/jspf/headerAdmin.jspf" %>
    




    <div class="container">



      <nav>
        <div class="nav nav-tabs" id="nav-tab" role="tablist">
          <a class="nav-item nav-link active" id="nav-patients-tab" data-toggle="tab" href="#nav-patients" role="tab" aria-controls="nav-patients" aria-selected="true"><fmt:message key="h.label.patient"/></a>
          <a class="nav-item nav-link" id="nav-doctor-tab" data-toggle="tab" href="#nav-doctor" role="tab" aria-controls="nav-doctor" aria-selected="false"><fmt:message key="h.label.doctor"/></a>
        </div>

      </nav>
      <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade show active" id="nav-patients" role="tabpanel" aria-labelledby="nav-patients-tab">
          <div class="row justify-content-center">
            <a class="button-neomorph" data-toggle="collapse" href="#form-filter" role="button" aria-expanded="false" aria-controls="collapseExample"><span class="fas fa-filter"></span> <fmt:message key="h.label.filter"/></a>
          </div>
          <div id="form-filter" class="collapse row block-neomorph justify-content-center">
            <form class="form-inline">
              <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref">
                <option selected>By name</option>
                <option value="1">One</option>
                <option value="2">Two</option>
                <option value="3">Three</option>
              </select>
              <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
              <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message key="button.lable.search"/></button>
            </form>

          </div>

          <div class="row justify-content-center">
            <c:forEach var="patient" items="${patients}">
            <div class="card block-neomorph">
              <img src="${patient.getImage()}" alt="patient">
              <div class="card-body">
                <h5>${patient.getFirstName()} ${patient.getLastName()}</h5>
                <p>
                  <fmt:message key="h.label.birthday"/>: ${patient.getBirthday().toString()}
                  <fmt:message key="h.label.lastmeeting"/>:
                </p>
                <a href="#" class="card-link"><fmt:message key="h.label.cardhistory"/></a>
              </div>
            </div>
          </c:forEach>
        </div>



      </div>
      <div class="tab-pane fade" id="nav-doctor" role="tabpanel" aria-labelledby="nav-doctor-tab">



       <div class="row justify-content-center">
        <c:forEach var="doctor" items="${doctors}">
        <div class="card block-neomorph">
          <img src="${doctor.getImage()}" alt="doctor">
          <div class="card-body">
            <h5>${doctor.getFirstName()} ${doctor.getLastName()}</h5>
            <p>
              <fmt:message key="h.label.specialization"/>: ${doctor.getSpecialization().getSpecializationName()}
              <fmt:message key="h.label.patientscount"/>: 0
            </p>
            <a href="#" class="card-link"><fmt:message key="h.label.meetinghistory"/></a>
          </div>
        </div>
      </c:forEach>
    </div>


  </div>
</div>



</div>






<script type="text/javascript">
  // Javascript to enable link to tab
  var url = document.location.toString();
  if (url.match('#')) {
    $('.nav-tabs a[href="#' + url.split('#')[1].replace(/(\?.+)/,'') + '"]').tab('show');
  } 

// Change hash for page-reload
$('.nav-tabs a').on('shown.bs.tab', function (e) {
  window.location.hash = e.target.hash;
})
</script>


<div class="body"></div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>

</body>
</html>