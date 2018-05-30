
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="alert alert-primary" role="alert">
			<div class="row">
				<h1>Ajouter un employ&eacute</h1>
			</div>
		</div>

		<form:form method="post" modelAttribute="employer">
			<div class="row">
				<div class="col col-md-4">
					<label>Matricule</label>
				</div>
				<div class="col col-md-6">
					<form:input path="matricule" type="text" class="form-control"
						placeholder="matricule" />
				</div>
			</div>
			<div class="row">
				<div class="col col-md-4">
					<label>Entreprise</label>
				</div>
				<div class="col col-md-6">
					<form:select path="entreprise.id" items="${listeEntreprise}"
						itemLabel="denomination" itemValue="id" cssClass="form-control"></form:select>

				</div>
			</div>
			<div class="row">
				<div class="col col-md-4">
					<label>Profil</label><br />
				</div>
				<div class="col col-md-6">
					<form:select path="profilRemuneration.id" items="${listeProfil}"
						itemLabel="code" itemValue="id" cssClass="form-control"></form:select>

				</div>
			</div>
			<div class="row">
				<div class="col col-md-4">
					<label>Grade</label><br />
				</div>
				<div class="col col-md-6">
					<form:select path="grade.id" items="${listeGrade}" itemLabel="code"
						itemValue="id" cssClass="form-control"></form:select>
				</div>
			</div>
			<form:button class="btn btn-primary">Valider</form:button>
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