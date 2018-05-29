<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employes</title>
<script src="<c:url value='bootstrap/js/bootstrap.js'  />" ></script>
<link href="<c:url value='/bootstrap/css/bootstrap.css' />"   rel="stylesheet" media="screen">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav">
				<li class="nav-item active "><a class="nav-link" href="">Employés
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Bulletins</a>
				</li>

			</ul>
		</div>
	</nav>

	<h1>Ajouter un employé</h1>



	<div class="container">
		<form:form modelAttribute="employe"  class="needs-validation" method="post">


			<div class="form-group row">
				<label for="validationTooltip01" class="col-sm-2 col-form-label">Matricule</label>
				<div class="col-sm-4">
					<form:input path="matricule" class="form-control" ></form:input>
				</div>
			</div>
			<div class="form-group row">
				<label for="validationTooltip02" class="col-sm-2 col-form-label">Entreprise</label>
				<div class="col-sm-4">
					<form:select path="entreprise.id" class="form-control" items="${entreprise}" itemValue="id"></form:select>
				</div>
			</div>


			<div class="form-group row">
				<label for="validationTooltip03" class="col-sm-2 col-form-label">Profil</label>
				<div class="col-sm-4">
					<form:select class="form-control" path="profilRemuneration.id" items="${profilRemuneration}" itemValue="id"></form:select>
				</div>
			</div>
			<div class="form-group row">
				<label for="validationTooltip04" class="col-sm-2 col-form-label">Grade</label>
				<div class="col-sm-4">
					<form:select class="form-control" path="grade.id" items="${grade}" itemValue="id" > </form:select>
				</div>
			</div>

			<input class="btn btn-primary" value="Ajouter " type="submit"> </input>
		</form:form>
</body>
</html>