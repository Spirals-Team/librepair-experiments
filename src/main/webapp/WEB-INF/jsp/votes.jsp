<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/voteDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="page-header">
            <h3><spring:message code="vote.vote"/></h3>
        </div>
        <table class="table table-striped display" id="voteDatatable">
            <thead>
            <tr>
                <th><spring:message code="user.name"/></th>
                <th><spring:message code="vote.restaurant"/></th>
                <th><spring:message code="common.addDate"/></th>
                <th></th>
            </tr>
            </thead>
        </table>
        <br/>
        <a class="btn btn-primary" onclick="redirectToRestaurants()">
            <span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
            <spring:message code="common.backToRestaurants"/>
        </a>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>