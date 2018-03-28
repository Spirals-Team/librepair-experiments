<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib
   prefix="form"
   uri="http://www.springframework.org/tags/form"%>
<html lang="fr">
<head>
<%@ page contentType="text/html; charset=UTF-8"%>
<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>

<%@include file="menu_admin.jsp" %>
<c:choose><c:when test="${results == null || results.examResults == null || results.examResults.isEmpty()}"><h1>AUCUN RESULTAT POUR CETTE EPREUVE</h1></c:when> 
<c:otherwise>
	<div class="container">
<c:if test="${!results.examResults.isEmpty()}"><h3><c:out value="${results.examResults.get(0).exam.code} (Type épreuve : ${results.examResults.get(0).exam.typeExam})"></c:out></h3>
<br/>
</c:if>

		<div  id="message">
         <div>${ErrorMessage}</div>
      </div>
		<div class="starter-template">
				<table class="table table-hover">
					<tr>
						<th>Prénom</th>
						<th>Nom</th>
						<th>Note</th>
						<th>Date d'obtention</th>
					</tr>
					  <form:form method="POST" modelAttribute="results" action="editResults?codeExam=${results.examResults.get(0).exam.code}">
						<c:forEach items="${results.examResults}" varStatus="resStat" var ="res">
							<tr>
								<td><c:out value="${res.studentYear.student.firstname}" /></td>
								<td><c:out value="${res.studentYear.student.lastname}" /></td>
								<td><form:input path="examResults[${resStat.index}].score" type="number" min="0" max="20"/></td>
								<td><form:input path="examResults[${resStat.index}].dateObtened" type ="date"/></td>
							</tr>
						</c:forEach>
               <tr><td style="border-top:none;"><input type="submit" value="Confirmer" id="form_button" /></td></tr>
						</form:form>
				</table>
		</div>
	</div>
	</c:otherwise>
	</c:choose>
</body>
<script>setTimeout(function() {
	 $('#message').fadeOut();
}, 1000 );</script>
</html>