<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>SGP app</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="#">Employés</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="#">Bulletins</a>
				</li>
			</ul>
		</div>
	</nav>
	<div class="container mt-5">
		<h2>Ajouter un employé</h2>
		<form:form method="post" modelAttribute="employe">
		<div class="row mt-2">
			<div class="col">
				<span class="input-group-text" id="basic-addon3">Matricule :</span>
			</div>
			<div class="col">
				<form:input type="text" path="matricule" name="matricule" class="form-control" id="basic-url"
					aria-describedby="basic-addon3" require="true"/>
			</div>
		</div>
		<div class="row mt-2">
			<div class="col">
						<span class="input-group-text" id="basic-addon3">Entreprise :</span>
					</div>
					<div class="col">
						<form:select path="entreprise.id">
								<form:option value="NONE" label="--- Select ---"/>
								<form:options items="${lesEntre}" itemLabel="denomination" itemValue="id"/>
							</form:select>
					</div>
				</div>
				<div class="row mt-2">
					<div class="col">
						<span class="input-group-text" id="basic-addon3">Profil :</span>
					</div>
					<div class="col">
						<form:select path="profilRemuneration.id">
								<form:option value="NONE" label="--- Select ---"/>
								<form:options items="${profils}" itemLabel="code" itemValue="id"/>
							</form:select>
					</div>
				</div>
				<div class="row mt-2">
					<div class="col">
						<span class="input-group-text" id="basic-addon3">Grade :</span>
					</div>
					<div class="col">
						<form:select path="grade.id">
								<form:option value="NONE" label="--- Select ---"/>
								<form:options items="${grades}" itemLabel="code" itemValue="id"/>
							</form:select>
					</div>
				</div>
				<div class="row mt-2">
					<div class="col"></div>
					<div class="col">
						<input type="submit" value="Ajouter"
							class="btn btn-outline-dark float-right mt-2">
					</div>
				</div>
				</form:form>
			</div>
</body>
</html>