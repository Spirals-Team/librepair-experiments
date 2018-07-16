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
	<form:form id="formulaireClasse" name="formulaireClasse" method="put"
		modelAttribute="classe">
		<div class="row">
			<div class="col-12 col-sm-4 offset-sm-4">
				<fieldset
					style="margin-bottom: 15px; padding: 10px; border: 3px dotted #999;">
					<legend style="text-align: center;">Nom de la classe</legend>
					<label id="labelName" name="labelName">Veuillez entrer le
						nom de votre classe : </label>
					<form:input id="inputName" path="nom" placeholder="Nom"
						class="col-12 col-sm-4 offset-sm-4 form-control" />
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
									<td>
										<c:set var="isCheck" value="0"></c:set>
										<c:forEach items="${listeStagiairesClasse}" var="stc">
											<c:if test="${st.id == stc.id}">
												<c:set var="isCheck" value="1"></c:set>
											</c:if>
										</c:forEach>
										<c:choose>
											<c:when test="${isCheck == 1}">
												<form:checkbox path="stagiaires[${status.index}].id" value="${st.id}" checked="checked" />
											</c:when>
											<c:otherwise>
												<form:checkbox path="stagiaires[${status.index}].id" value="${st.id}" />
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</fieldset>
			</div>
		</div>
		<div class="row">
			<div class="col-12 col-sm-2 offset-sm-5">
				<button type="submit" class="btn btn-secondary">Éditer
					classe</button>
			</div>
		</div>
	</form:form>