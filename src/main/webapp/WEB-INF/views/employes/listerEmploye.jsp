<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/bootstrap-3.3.7-
dist/css/bootstrap.css">
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="#">LOG</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav">
				<a class="nav-item nav-link" href="<c:url value = "/mvc/employes/creer"/>">Employes</a> <a
					class="nav-item nav-link" href="<c:url value = "/mvc/bulletins/creer"/>">Bulletins</a>
			</div>
		</div>
	</nav>

	
	<div class="container">
		<div class="text-center">
			<h1>Liste des employés</h1>
		</div>

		<a href="creer" class="btn btn-info" role="button"
			style="margin-bottom: 2%; margin-left: 87%;">Ajouter un employé</a>

		<table class="table table-hover table-bordered">
			<thead>
				<tr>
					<th width="40%;">Date / Heure de création</th>
					<th>Matricule</th>
					<th>Grade</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${remunerationEmploye}" var="remunerationEmploye">
					<tr>
						<td>"${remunerationEmploye.dateCreation}"</td>
						<td>"${remunerationEmploye.matricule}"</td>
						<td>"${remunerationEmploye.grade}"</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>
	
	