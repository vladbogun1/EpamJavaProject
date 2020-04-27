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
    <title>Service</title>
    <%@include file="/WEB-INF/jspf/lang.jspf" %>
    <jsp:include page="/WEB-INF/files/bootstrap.html"/>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/service.css" rel="stylesheet">
</head>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>


<div class="container">
    <div class="row justify-content-center">
        <a class="button-neomorph" data-toggle="collapse" href="#form-patient-filter" role="button" aria-expanded="false" aria-controls="collapseExample"><fmt:message key="h.label.addmeeting"/></a>
    </div>
    <div id="form-patient-filter" class="collapse block-neomorph justify-content-center">

        <div class="control-group">
            <label for="user_select"><fmt:message key="h.label.patient"/>:</label>
            <select id="user_select" class="contacts" placeholder="Pick some patient..."></select>
        </div>

        <div class="control-group">
            <label for="doctor_select"><fmt:message key="h.label.doctor"/>:</label>
            <select id="doctor_select" class="contacts" placeholder="Pick some doctor..."></select>
        </div>
        <button class="btn btn-lg btn-outline-success " type="button" onclick="load(this);createMeeting(this);"><span><fmt:message
                key="button.lable.save"/></span><div class="loader" style="display: none;"></div></button>
    </div>
    <div class="row justify-content-center">
        <a class="button-neomorph" data-toggle="collapse" href="#form-new-patient" role="button" aria-expanded="false" aria-controls="collapseExample"><fmt:message key="h.label.addmeeting"/></a>
    </div>
    <div id="form-new-patient" class="collapse block-neomorph justify-content-center">

        <div class="control-group">
            <label for="first_name"><fmt:message key="label.firstname"/>:</label>
            <input type="text" id="first_name">
        </div>

        <div class="control-group">
            <label for="last_name"><fmt:message key="label.lastname"/>:</label>
            <input type="text" id="last_name">
        </div>
        <div class="control-group">
            <label for="birthday"><fmt:message key="label.birthday"/>:</label>
            <input type="date" id="birthday">
        </div>
        <button class="btn btn-lg btn-outline-success " type="button" onclick="load(this);createPatient(this);"><span><fmt:message
                key="button.lable.save"/></span><div class="loader" style="display: none;"></div></button>
    </div>




</div>


<script type="text/javascript">
    function load(a) {
        a.childNodes.forEach((slide) => {
            if (slide.style.display === "none") {
                slide.style.display = "";
            } else {
                slide.style.display = "none";
            }
        });
    }

    function createPatient(button) {
        var stringPath = "/service?";
        stringPath+="command=newPatient&";
        var first_name = $("#first_name")[0].value;
        stringPath+="first_name=" + first_name + "&";
        var birthday = $("#birthday")[0].value;
        stringPath+="date=" + birthday + "&";
        var last_name = $("#last_name")[0].value;
        stringPath+="last_name=" + last_name;



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
        };
        request.send();
    }

    function createMeeting(button) {
        var stringPath = "/service?";
        stringPath+="command=addMeeting&";
        var patient_id = $("#user_select")[0].value;
        stringPath+="patient_id=" + patient_id + "&";
        var doctor_id = $("#doctor_select")[0].value;
        stringPath+="doctor_id=" + doctor_id;



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
        };
        request.send();
    }




    var doctors = [];
    <c:forEach var="doctor" items="${doctors}">
        doctors.push(
            {
                email:'${doctor.getSpecialization().getSpecializationName()}',
                name:'${doctor.getFirstName()} ${doctor.getLastName()}',
                id:${doctor.getId()}
        }
        );
    </c:forEach>
    var patients = [];
    <c:forEach var="patient" items="${patients}">
    patients.push(
        {
            email:'${patient.getBirthday()}',
            name:'${patient.getFirstName()} ${patient.getLastName()}',
            id:${patient.getId()}
        }
    );
    </c:forEach>

    $('#user_select').selectize({
        persist: false,
        maxItems: 1,
        valueField: 'id',
        labelField: 'name',
        searchField: ['name', 'email'],
        options: patients,
        render: {
            item: function (item, escape) {
                return '<div>' +
                    (item.name ? '<span class="name">' + escape(item.name) + '</span>' : '') +
                    (item.email ? '<span class="email">' + escape(item.email) + '</span>' : '') +
                    '</div>';
            },
            option: function (item, escape) {
                var label = item.name || item.email;
                var caption = item.name ? item.email : null;
                return '<div>' +
                    '<span class="label">' + escape(label) + '</span>' +
                    (caption ? '<span class="caption">' + escape(caption) + '</span>' : '') +
                    '</div>';
            }
        },
        createFilter: function (input) {
            var match, regex;

            // email@address.com
            regex = new RegExp('^' + '$', 'i');
            match = input.match(regex);
            if (match) return !this.options.hasOwnProperty(match[0]);

            // name <email@address.com>
            regex = new RegExp('^([^<]*)\<' + '\>$', 'i');
            match = input.match(regex);
            if (match) return !this.options.hasOwnProperty(match[2]);

            return false;
        },
        create: function (input) {
            if ((new RegExp('^' + '$', 'i')).test(input)) {
                return {email: input};
            }
            var match = input.match(new RegExp('^([^<]*)\<' + '\>$', 'i'));
            if (match) {
                return {
                    email: match[2],
                    name: $.trim(match[1])
                };
            }
            alert('Invalid email address.');
            return false;
        }
    });
    $('#doctor_select').selectize({
        persist: false,
        maxItems: 1,
        valueField: 'id',
        labelField: 'name',
        searchField: ['name', 'email'],
        options: doctors,
        render: {
            item: function (item, escape) {
                return '<div>' +
                    (item.name ? '<span class="name">' + escape(item.name) + '</span>' : '') +
                    (item.email ? '<span class="email">' + escape(item.email) + '</span>' : '') +
                    '</div>';
            },
            option: function (item, escape) {
                var label = item.name || item.email;
                var caption = item.name ? item.email : null;
                return '<div>' +
                    '<span class="label">' + escape(label) + '</span>' +
                    (caption ? '<span class="caption">' + escape(caption) + '</span>' : '') +
                    '</div>';
            }
        },
        createFilter: function (input) {
            var match, regex;

            // email@address.com
            regex = new RegExp('^' + '$', 'i');
            match = input.match(regex);
            if (match) return !this.options.hasOwnProperty(match[0]);

            // name <email@address.com>
            regex = new RegExp('^([^<]*)\<' + '\>$', 'i');
            match = input.match(regex);
            if (match) return !this.options.hasOwnProperty(match[2]);

            return false;
        },
        create: function (input) {
            if ((new RegExp('^' + '$', 'i')).test(input)) {
                return {email: input};
            }
            var match = input.match(new RegExp('^([^<]*)\<' + '\>$', 'i'));
            if (match) {
                return {
                    email: match[2],
                    name: $.trim(match[1])
                };
            }
            alert('Invalid email address.');
            return false;
        }
    });

    $('#user_select')[0].selectize.setValue('1');
    $('#doctor_select')[0].selectize.setValue('1');
</script>


<div class="body"></div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>

</body>
</html>