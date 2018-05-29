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
		<div class="row align-items-start">
			<div class="col-12">
				<h1>Créer Bulletin de salaire</h1>
			</div>
		</div>

		<form:form method="POST" modelAttribute="bulletin">
			<div class="form-group">
				<label for="periode">Periode</label>
				<form:select path="periode.id" items="${listePeriodes}" itemValue="id" />
			</div>

			<div class="form-group">
				<label for="matricule">Matricule</label>
				<form:select path="remunerationEmploye.id"
					items="${listeMatricules}" itemValue="id" itemLabel="matricule" />
			</div>


			<div class="form-group">
				<label for="primeExceptionnelle">Prime Exceptionnelle</label>
				<div class="col-4 offset-8">
					<form:input type="text" class="form-control"
						path="primeExceptionnelle" placeholder="Prime Exceptionnelle"
						required="" />

				</div>
			</div>

			<div class="row justify-content-end">
				<div class="col-7 offset-5">
					<button type="submit" class="btn btn-info" id="creer">Créer</button>
				</div>
			</div>
	</form:form>
	</div>
</body>
</html>