<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="/webjars/bootstrap/4.1.1/css/bootstrap.min.css">
</head>

<body>

	<h1 style="text-align: center;">Bienvenue sur la page d'ajout
		d'une classe</h1>
	<br />
	<br />
	<form:form id="formulaireClasse" name="formulaireClasse" method="post"
		modelAttribute="classe">
		<div class="row">
			<div class="col-12 col-sm-4 offset-sm-4">
				<fieldset
					style="margin-bottom: 15px; padding: 10px; border: 3px dotted #999;">
					<legend style="text-align: center;">Nom de la classe</legend>
					<label id="labelName" name="labelName">Veuillez entrer le
						nom de votre classe : </label>
					<form:input id="inputName" path="nom"
						placeholder="Nom" class="col-12 col-sm-4 offset-sm-4 form-control" />
					<form:errors path="nom">
						<font color="red">Le nom est obligatoire</font>
					</form:errors>
				</fieldset>
			</div>

		</div>
		<div class="row">
			<div class="col-12 col-sm-8 offset-sm-2">
				<fieldset
					style="margin-bottom: 15px; padding: 10px; border: 3px dotted #999;">
					<legend style="text-align: center;">Liste des stagiaires</legend>
					<label id="labelStagiaire" name="labelStagiaire">Veuillez
						sélectionner les stagiaires qui constituent cette classe : </label>
					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col">Nom</th>
								<th scope="col">Prénom</th>
								<th scope="col">Photo</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${listeStagiaires}" var="st" varStatus="status">
								<tr>
									<td>${st.nom}</td>
									<td>${st.prenom}</td>
									<td>${st.photoUrl}</td>
									<td><form:checkbox path="stagiaires[${status.index}].id"
											value="${st.id}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</fieldset>
			</div>
		</div>
		<div class="row">
			<div class="col-12 col-sm-2 offset-sm-5">
				<button type="submit" class="btn btn-secondary">Créer
					classe</button>
			</div>
		</div>
	</form:form>


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