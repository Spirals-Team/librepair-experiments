<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!doctype html>
<html>
<head>
<link rel="stylesheet" href="/webjars/bootstrap/4.1.1/css/bootstrap.css" />
</head>
<body>

	<div class="row justify-content-center">
		<h1 class="mb-3">Ajouter une option de sondage</h1>
		<form:form method="POST" class="col-11" modelAttribute="optionsondage">
		<div>
			<label for="titre">Libellé : </label>
			<form:input type="text" id="titre" class="form-control mb-3"
				path="libelle" />
			<form:errors path="libelle">
				<p class="text-danger">Le libellé doit faire plus de 3 caractères</p>
			</form:errors>
			</div>
			<label for="desc">Description : </label>
			<form:input type="text" id="desc" class="form-control mb-3"
				path="description" />
			<form:errors path="description">
				<p class="text-danger">La descrition doit faire plus de 3 caractères</p>
			</form:errors>

			<div class="row justify-content-center">
				<input type="submit" class="btn btn-primary col-3" />
			</div>
		</form:form>
	</div>
</body>
</html>