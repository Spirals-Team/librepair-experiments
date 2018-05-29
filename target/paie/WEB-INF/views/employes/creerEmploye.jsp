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
				<a class="nav-item nav-link" href="<c:url value = "mvc/employes/creer"/>">Employes</a> <a
					class="nav-item nav-link" href="<c:url value = "mvc/bulletins/creer"/>">Bulletins</a>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="row align-items-start">
			<div class="col-12">
				<h1>Ajouter un employé</h1>
			</div>
		</div>
		<form:form method="POST" modelAttribute="remunerationEmploye">
			<div class="form-group">
				<label for="matricule">Matricule</label>
				<div class="col-4 offset-8">
					<form:input type="text" class="form-control" path="matricule"
						placeholder="Matricule" required=""/>

				</div>
			</div>

			<div class="form-group">
				<label for="entreprise">Entreprise</label>
				<form:select path="entreprise.id"
					items="${listeEntr}" itemValue="id"/>	
				</div>
	
				<div class="form-group">
						<label for="profil">Profil</label>
						<form:select  path="profilRemuneration.id" items="${listeProfils}" itemValue="id"/>	
					</div>

				<div class="form-group">
						<label for="grade">Grade</label>
						<form:select  path="grade.id" items="${listeGrades}" itemValue="id"/>	
					</div>

				<div class="row justify-content-end">
					<div class="col-7 offset-5">
						<button type="submit" class="btn btn-info" id="creer">Ajouter</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>



</body>
</html>