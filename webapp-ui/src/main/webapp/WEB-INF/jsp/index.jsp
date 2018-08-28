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
        <div class="form-group">
            <label for="sel1">Выберите отдел: </label>
            <select class="form-control" id="sel1">
                <option>Все</option>
                <option>Руководство</option>
                <option>Бухгалтерия</option>
                <option>Разработка</option>
            </select>
        </div> <!-- /form-group -->

        <div class="col-lg-6" style="margin-top: 10px; padding-left: 0; padding-right: 0;">
        </div><!-- /.col-lg-6 -->

        <div class="col-lg-6" style="margin-top: 10px; padding-left: 0px; padding-right: 0px;">
            <form action="<c:url value="/employee/add"/>" method="get" style="display: inline;">
                <button type="submit" class="btn btn-primary">
                    <i class="icon-user icon-white"></i> Добавить
                </button>
            </form>

            <!-- Single button -->
            <div class="btn-group">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                        aria-haspopup="true"
                        aria-expanded="false">
                    Action <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="#">Separated link</a></li>
                </ul>
            </div> <!-- /btn-group -->
        </div><!-- /.col-lg-6 -->

        <table class="table table-hover">
            <thead>
            <tr>
                <th></th>
                <th>Firstname</th>
                <th>Secondname</th>
                <th>Lastname</th>
                <th>Birthday</th>
                <th>Email</th>
                <th>ICQ</th>
                <th>Skype</th>
                <th>Note</th>
                <th>Home address</th>
                <th>Work address</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${employeeList}" var="employee">
                <tr>
                    <td>
                        <form action="<c:url  value="/employee/edit/${employee.id}"/>" method="get"
                              style="display: inline;">
                            <button type="submit"
                                    class="btn btn-success btn-xs glyphicon glyphicon-pencil"></button>
                        </form>
                        <form action="<c:url  value="/employee/delete/${employee.id}"/>" method="post"
                              style="display: inline;">
                            <button type="submit" class="btn btn-danger btn-xs glyphicon glyphicon-remove"></button>
                        </form>
                    </td>
                    <td>${employee.firstName}</td>
                    <td>${employee.secondName}</td>
                    <td>${employee.lastName}</td>
                    <td>
                            ${employee.birthdate}
                                <fmt:formatDate pattern="dd.MM.yyyy" value="${employee.birthdate}"/>
                    </td>
                    <td>${employee.email}</td>
                    <td>${employee.icq}</td>
                    <td>${employee.skype}</td>
                    <td>${employee.note}</td>
                    <td>${employee.workAddress}</td>
                    <td>${employee.homeAddress}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div> <!-- /row -->

    <hr>
    <%@include file="/WEB-INF/jsp/include/footer.jsp" %>
</div> <!-- /container -->
</body>
</html>
