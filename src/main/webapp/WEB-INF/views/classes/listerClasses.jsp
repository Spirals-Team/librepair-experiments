
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html>
<head>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">

</head>
<body>

	<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
		<a class="navbar-brand" href="#"><img alt="Logo" src=""></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarsExample03" aria-controls="navbarsExample03"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarsExample03">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link"
					href="../../index.html">Accueil</a></li>
				<li class="nav-item"><a class="nav-link" href="../Gestion.html">Gestion</a>
				</li>
			</ul>
		</div>
	</nav>

	<br />

	<div class="row">
		<div class="col-12 col-sm-4 offset-sm-4">
			<fieldset class="fieldsetEntite">
				<legend class="legendEntite">Entité Classe</legend>
				<p class="pEntite">
					Une Classe est composé de : <br /> -Un id (Hérité de BaseEntité,
					auto-incrémenté)<br /> -Un nom <br /> -Une liste de Stagiaire<br />
					<br /> Vous pouvez :<br /> -Ajouter une classe<br /> -Éditer une
					classe<br /> -Supprimer une classe<br />
				</p>
			</fieldset>
		</div>
	</div>
	<br />
	<div class="row">
		<div class="col-12 col-sm-4 offset-sm-4">
			<label>Ajouter une nouvelle Classe</label> <a href="/classes/ajouter" type="button"
				class="btn btn-secondary">Ajouter</a>
		</div>
	</div>
	<br />
	<div class="row">
		<div class="col-12 col-sm-6 offset-sm-3">
			<div class=tableau>
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">Id</th>
							<th scope="col">Nom</th>
							<th scope="col">Liste des Stagiaires</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${listeClasses}" var="c">
							<tr>
								<td>${c.getId()}</td>
								<td>${c.nom}</td>
								<td>[ <c:forEach items="${c.stagiaires}" var="st">
							[${st.nom} , ${st.prenom}]
						</c:forEach> ]
								</td>
								<td><a href="/classes/maj?id=${c.id}">Éditer</a> / <a href="/classes/supprimer?id=${c.id}"> Supprimer</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- jQuery first, then poper, then Bootstrap JS. -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
		integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
		crossorigin="anonymous"></script>
</body>
</html>