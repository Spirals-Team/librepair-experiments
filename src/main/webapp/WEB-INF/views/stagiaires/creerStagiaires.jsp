<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="/webjars/bootstrap/4.1.1/css/bootstrap.min.css">
<title>Evalme App</title>
</head>

<body>
	<div class="container-fluid">
		<h1>Créer un stagiaire</h1>
		<form:form modelAttribute="stagiaire">
			<div class="form-group row justify-center">
				<label for="inputName" class="col-md-2 col-form-label">Nom</label>
				<div class="col-sm-8">
					<form:input path="nom" type="text" class="form-control" id="inputName" name="inputName" />
					<form:errors path="nom">
						<font color="red">Le nom est obligatoire</font>
					</form:errors>
				</div>
			</div>
			<div class="form-group row">
				<label for="inputFirstname" class="col-md-2 col-form-label">Prénom</label>
				<div class="col-sm-8">
					<form:input path="prenom" type="text" class="form-control" id="inputFirstName" name="inputName" />
					<form:errors path="prenom">
						<font color="red">Le prénom est obligatoire</font>
					</form:errors>
				</div>
			</div>
			<div class="form-group row">
				<label for="inputEmail" class="col-md-2 col-form-label">Email</label>
				<div class="col-sm-8">
					<form:input path="email" placeholder="exemple@messagerie.fr"  type="email" class="form-control" id="inputEmail" name="inputEmail"/>
					<form:errors path="email">
						<font color="red">L'email est obligatoire</font>
					</form:errors>
				</div>
			</div>
			<div class="form-group row">
				<label for="inputPhoto" class="col-md-2 col-form-label">Photo</label>
				<div class="col-sm-8">
					<form:input path="photoUrl" type="text" class="form-control" id="inputPhoto" name="inputPhoto" />
					<form:errors path="photoUrl">
						<font color="red">La photo est obligatoire</font>
					</form:errors>
				</div>
			</div>
			<div class="row justify-content-end">
				<div class="col-10">
					<button type="submit" class="btn-create btn btn-primary">Créer</button>
				</div>
			</div>
		</form:form>

	</div>


</body>
</html>