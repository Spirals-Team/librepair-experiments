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

	<%@include file="menu_student.jsp"%>

	<div class="container">

		<div class="starter-template">
			<div class="page-header">
				<h1>Rexam</h1>

				<h2>
					<c:out value="Liste des résultats" />
				</h2>
			</div>

			<table id="res_table" class="unitsTable table table-hover">
				<thead>
					<tr>
						<th>UE</th>
						<th>Etat</th>
						<th>Moyenne</th>
						<th>Notes</th>
						<th>Nb Credits</th>
						<th>Status</th>
					</tr>
				</thead>
				<c:forEach items="${results}" var="res">
					<tr>
						<td><c:out value="${res.teachingUnit.name}" /></td>
						<td><c:out value="${res.status}" /></td>

						<td><c:choose>
								<c:when test="${empty res.averageScore}">
									<c:out value="n/a" />
								</c:when>
								<c:otherwise>
									<c:out value="${res.averageScore}" />
								</c:otherwise>
							</c:choose></td>

						<td><a class="btn btn-info"
							href="/rexam/results/${res.teachingUnit.code}"> Détail des
								notes </a></td>

						<td><c:out value="${res.teachingUnit.creditValue}" /></td>

						<td><c:choose>
								<c:when test="${empty res.averageScore}">
									<c:out value="???" />
								</c:when>
								<c:when test="${res.averageScore < 10}">
									<c:out value="AJ" />
								</c:when>
								<c:otherwise>
									<c:out value="ADM" />
								</c:otherwise>

							</c:choose></td>
					</tr>
				</c:forEach>
			</table>


		</div>
	</div>
</body>
</html>