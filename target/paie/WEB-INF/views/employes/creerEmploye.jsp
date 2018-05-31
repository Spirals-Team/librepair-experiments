<%@ page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap-4.1.1-dist/css/bootstrap.css">
	<title>Création d'un employé</title>
</head>

<body>
	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a href="../employes/lister" class="nav-link">Employés</a></li>
				<li class="nav-item active"><a href="../bulletins/lister" class="nav-link">Bulletins</a></li>
			</ul>
		</div>
	</nav>
	
	<div class="container">
		<div class="row">
			<div class="col-9">
				<h2>Ajouter un employé</h2>
			</div>
		</div>
		
		<form:form method="post" modelAttribute="employe">
			<div class="form-group row">
				<label for="matricule" class="col-3 col-form-label">Matricule</label>
				<form:select class="col-9" items="${collegues}" path="matricule" itemValue="matricule" itemLabel="matricule"></form:select>
							</div>
			<div class="form-group row">
				<label for="entreprise" class="col-3 col-form-label">Entreprise</label>
				<form:select class="col-9" items="${entreprises}" path="entreprise.id" itemValue="id" itemLabel="denomination"></form:select>
			</div>
			<div class="form-group row">
				<label for="profil" class="col-3 col-form-label">Profil</label>
				<form:select class="col-9" items="${profil_remunerations}" path="profilRemuneration.id" itemValue="id" itemLabel="code"></form:select>
			</div>
			<div class="form-group row">
				<label for="grade" class="col-3 col-form-label">Grade</label>
				<form:select class="col-9" items="${grades}" path="grade.id" itemValue="id" itemLabel="code"></form:select>
			</div>
			<form:button type="submit" class="btn btn-dark">Créer</form:button>
		</form:form>
	</div>
	
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