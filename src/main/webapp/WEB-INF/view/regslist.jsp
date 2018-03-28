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

				<h2>Liste des inscriptions :</h2>
			</div>

			<table id="regslist" class="unitsTable table table-hover">
				<thead>
					<tr>
						<th>Année</th>
						<th>Nom de l'UE</th>
						<th>Crédits</th>
					</tr>
				</thead>
				<c:forEach items="${regs}" var="reg">
					<tr>
						<td><c:out value="${reg.studentYear.id.year}" /></td>
						<td><c:out value="${reg.teachingUnit.name}" /></td>
						<td><c:out value="${reg.teachingUnit.creditValue}" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>