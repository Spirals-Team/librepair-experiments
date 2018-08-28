<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Phonebook</title>

    <link rel="stylesheet" href="<c:url  value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>">
    <link rel="stylesheet"
          href="<c:url  value="/webjars/bootstrap-3-datepicker/1.5.0/dist/css/bootstrap-datepicker3.min.css"/>"/>
    <link rel="stylesheet" href="<c:url  value="/css/style.css"/>">

    <script src="<c:url  value="/webjars/jquery/1.12.4/jquery.min.js"/>"></script>
    <script src="<c:url  value="/webjars/bootstrap/3.3.7/js/bootstrap.js"/>"></script>

    <script src="<c:url  value="/webjars/bootstrap-3-datepicker/1.5.0/dist/js/bootstrap-datepicker.min.js"/>"></script>
    <script src="<c:url  value="/webjars/bootstrap-3-datepicker/1.5.0/dist/locales/bootstrap-datepicker.ru.min.js"/>"></script>

    <script src="<c:url  value="/webjars/bootstrap3-typeahead/3.1.1/bootstrap3-typeahead.js"/>"></script>

    <script src="<c:url  value="/webjars/bootbox/4.4.0/bootbox.js"/>"></script>
    <script src="<c:url  value="/js/phonebook.js"/>"></script>
</head>
<body>
<%@include file="/WEB-INF/jsp/include/menu.jsp" %>

<div class="container">
    <div class="row">
        <form action="<c:url  value="/employee/insert"/>" method="post">
            <div class="employee-card" id="firstname"><label for="firstname">Firstname</label>
                <input type="text" name="firstName" value="${employee.firstName}">
            </div>

            <div class="employee-card" id="secondName"><label for="secondName">Secondname</label>
                <input type="text" name="secondName" value="${employee.secondName}">
            </div>

            <div class="employee-card" id="lastName"><label for="lastName">Lastname</label>
                <input type="text" name="lastName" value="${employee.lastName}">
            </div>

            <div class="employee-card" id="birthdate"><label for="birthdate">Birthday</label>
                <input type="text" name="birthdate" id="datepicker"
                       value="<fmt:formatDate pattern="dd.MM.yyyy" value="${employee.birthdate}"/>">
            </div>

            <div class="employee-card" id="email"><label for="email">email</label>
                <input type="email" name="email" value="${employee.email}">
            </div>

            <div class="employee-card" id="icq"><label for="icq">icq</label>
                <input type="text" name="icq" value="${employee.icq}">
            </div>

            <div class="employee-card" id="skype"><label for="skype">skype</label>
                <input type="text" name="skype" value="${employee.skype}">
            </div>

            <div class="employee-card" id="note"><label for="note">note</label>
                <input type="text" name="note" value="${employee.note}">
            </div>

            <div class="employee-card" id="workAddress"><label for="workAddress">Work address</label>
                <input type="text" name="workAddress" value="${employee.workAddress}">
            </div>
            <div class="employee-card" id="homeAddress"><label for="homeAddress">Home address</label>
                <input type="text" name="homeAddress" value="${employee.homeAddress}">
            </div>

            <div class="employee-card" id="phoneContainer"><label for="phoneContainer">Phone</label>
                <button type="button" id="addPhone" class="btn btn-success btn-xs glyphicon glyphicon-plus"
                        name="employeeId" value="Add phone"></button>
            </div>

            <button type="submit" class="btn btn-success" id="save">Save</button>
        </form>
    </div> <!-- /row -->

    <hr>
    <%@include file="/WEB-INF/jsp/include/footer.jsp" %>
</div> <!-- /container -->
</body>
</html>
