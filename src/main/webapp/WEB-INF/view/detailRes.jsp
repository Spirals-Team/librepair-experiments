<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="fr">
<head>
<%@ page contentType="text/html; charset=UTF-8"%>
<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

</head>

<body>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="/">Rexam</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="/">Index</a></li>


					<li><a href="/rexam/showTeachingUnits">Liste des UE</a></li>

					<li><a href="/rexam/regs">Liste des
							inscriptions</a></li>

					<li><a href="/rexam/results">Liste des résultats</a></li>

				</ul>

				<p class="navbar-text">
					<c:out value="Année ${currentYear}-${currentYear+1}" />
				</p>

				<p class="navbar-text">
					<c:out
						value="Connecté en tant que ${student.firstName} ${student.lastName}" />
				</p>
				
				<form class="navbar-form navbar-right" action="/search" method="get">
					<div class="input-group">
						<input name="searchTerm" type="text" class="form-control"
							placeholder="Rechercher...">
						<div class="input-group-btn">
							<button class="btn btn-default" type="submit">
								<i class="glyphicon glyphicon-search"></i>
							</button>
						</div>
					</div>
				</form>

			</div>
		</div>
	</nav>
	<div class="container">

		<div class="starter-template">
			<h1>Rexam</h1>

			<h2>
				<c:out value="Détail des notes -- ${tu.name}" />
			</h2>

			<div class="avg_details">

				<c:if test="${empty reg.averageScore}">
					<c:out value="Moyenne : Pas encore déterminée" />
				</c:if>

				<c:if test="${not empty reg.averageScore}">
					<c:out value="Moyenne : ${reg.averageScore}" />
				</c:if>

				<c:out value="Status :${reg.status}" />

			</div>

			<div class="exam_details">

				<table id="exam_table" class="unitsTable table table-hover">
					<thead>
						<tr>
							<th>Epreuves</th>
							<th>Notes</th>
							<th>Poids</th>
							<th>Date d'obtention</th>
						</tr>
					</thead>

					<c:forEach items="${tu.components}" var="compo">

						<tr>
							<td><c:out value="${compo.exam.typeExam}" /></td>
							<td><c:out
									value="${compo.exam.getResultByStudentYear(studyear).score}" /></td>
							<td><c:out value="${compo.weight}" /></td>
							<td><c:out
									value="${compo.exam.getResultByStudentYear(studyear).dateObtened}" /></td>
						</tr>

					</c:forEach>

				</table>
			</div>

		</div>
	</div>
</body>
</html>