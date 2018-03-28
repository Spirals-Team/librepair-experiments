
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

		<%@include file="menu_student.jsp" %>
	<div class="container">

		<div class="starter-template">
			<div class="page-header">
			<h1>Rexam</h1>

			<h2><c:out value="${teachingUnit.name} :" /></h2>
		</div >
			
				<table class="table table-hover">
					<tr>
						<th>Épreuve</th>
						<th>Type épreuve</th>
						<th>Poids</th>
					</tr>
						<c:forEach items="${teachingUnit.components }" var="component">
							<tr>
								<td><c:out value="${component.exam.code}" /></td>
								<td><c:out value="${component.exam.typeExam}" /></td>
								<td><c:out value="${component.weight}" /></td>
							</tr>

						</c:forEach>
				</table>

		</div>
	</div>
</body>
</html>