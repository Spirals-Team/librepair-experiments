
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
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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

			<h2>Liste des UE par discipline :</h2>
			<div id="accordion">
				<c:forEach items="${disciplines}" var="discipline" varStatus="i">
					<h3>
						<c:out value="${discipline}" />
					</h3>
					<div>
						<table id="tu${i.index}" class="unitsTable table table-hover">
							<thead>
								<tr>
									<th>Nom</th>
									<th>Nb crédit</th>
									<th>Épreuves</th>
									<th>Actions</th>
								</tr>
							</thead>
							<c:forEach items="${teachingUnits}" var="tu">
								<c:if test="${discipline==tu.discipline }">
									<tr>
										<td><c:out value="${tu.name}" /></td>
										<td><c:out value="${tu.creditValue}" /></td>
										<td><a href="/rexam/showExams?code=${tu.code }">Détail des
												épreuves</a></td>
										<td><a href="/rexam/registration?code=${tu.code }"><button>S'inscrire</button></a></td>
									</tr>

								</c:if>
							</c:forEach>
						</table>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<script>
		$(function() {
			$("#accordion").accordion({
				collapsible : true,
				active : false
			});
		});
	</script>
</body>
</html>