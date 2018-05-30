<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
</head>
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<div class="alert alert-primary" role="alert">
			<div class="row">
				<h1>Creer Bulletin de Salaire</h1>
			</div>
		</div>

		<form:form method="post" modelAttribute="bulletin">
			<div class="row">
				<div class="col col-md-4">
					<label>Periode</label>
				</div>
				<div class="col col-md-6">
					<form:select path="periode.id" items="${listePeriode }" itemLabel="libelle" itemValue="id" ></form:select>
				</div>
			</div>
			<div class="row">
				<div class="col col-md-4">
					<label>Matricule</label>
				</div>
				<div class="col col-md-6">
					<form:select path="remunerationEmploye.id" items="${listeMatri}"
						itemLabel="remunerationEmploye.matricule" itemValue="id" cssClass="form-control"></form:select>

				</div>
			</div>
			<div class="row">
				<div class="col col-md-4">
					<label>Prime Exceptionnelle</label><br />
				</div>
				<div class="col col-md-6">
					<form:input path="primeExceptionnelle" cssClass="form-contro" type="number"/>

				</div>
			</div>

			<form:button class="btn btn-primary">Creer</form:button>
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