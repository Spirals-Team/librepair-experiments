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

					<li class="active"><a href="/rexam/regs">Liste des
							inscriptions</a></li>

					<li><a href="/rexam/results">Liste des résultats</a></li>

				</ul>

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

				<p class="navbar-text">
					<c:out value="Année ${currentYear}-${currentYear+1}" />
				</p>

				<p class="navbar-text">
					<c:out
						value="Connecté en tant que ${student.firstName} ${student.lastName}" />
				</p>

			</div>
		</div>
	</nav>

	<div class="container">

		<div class="starter-template">
			<h1>Rexam</h1>

			<h2>Liste des inscriptions :</h2>


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