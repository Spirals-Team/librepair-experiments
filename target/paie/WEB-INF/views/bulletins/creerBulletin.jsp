<html>
<head>
<meta charset="UTF-8">
<title>PAIE</title>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
	integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
	integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
	integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
	crossorigin="anonymous"></script>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item mr-2"><a href = "<c:url value = "/mvc/employes/lister"/>">Employés</a>
				</li>
				<li class="nav-item mr-2"><a href = "<c:url value = "/mvc/bulletins/lister"/>">Bulletins</a>
				<span class="sr-only">(current)</span>
				</li>
			</ul>
		</div>
	</nav>
	<h1 class="text-center">Ajouter un bulletin de salaire</h1>
	<form:form modelAttribute="bulletin" method="post">
		<div class="container">
			<div class="row m-2">
				<form:label path="periode" class="col-4 mb-2">Période</form:label>
				<form:select path="periode.id" class="col-8 mb-2">
					<form:option value="NONE">&#160;</form:option>
					<form:options items="${periodes}" itemValue="id"
						itemLabel="dateDebut"></form:options>
				</form:select>
				<form:label path="remunerationEmploye.matricule" class="col-4 mb-2">Matricule</form:label>
				<form:select path="remunerationEmploye.id" class="col-8 mb-2">
					<form:option value="NONE">&#160;</form:option>
					<form:options items="${RemunerationEmployes}" itemValue="id"
						itemLabel="matricule"></form:options>
				</form:select>
				<form:label path="primeExceptionnelle" class="col-4 mb-2">Prime exceptionnelle</form:label>
				<form:input path="primeExceptionnelle" class="col-8 mb-2" />
				<div class="col-12 p-0">
					<input type="submit" value="Ajouter" class="col-2 float-right p-0" />
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>
