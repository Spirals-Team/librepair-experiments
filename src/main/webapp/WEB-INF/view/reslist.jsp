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

					<li class="active"><a href="/rexam/results">Liste des résultats</a></li>

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
				<c:out value="Liste des résultats" />
			</h2>

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
							</c:choose>
						</td>
						
						<td><a class="btn btn-info"
							href="/rexam/results/${res.teachingUnit.code}"> Détail des
								notes </a></td>

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