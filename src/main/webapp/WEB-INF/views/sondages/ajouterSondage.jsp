<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html>
<head>
<link rel="stylesheet" href="/webjars/bootstrap/4.1.1/css/bootstrap.css" />
</head>
<body>

	<div class="row justify-content-center">
	<h1 class="mb-3">Page d'ajout de sondage</h1>
	<form:form method="post" class="col-11" modelAttribute="sondage">
		<label for="name">Nom : </label>
		<form:input type="text" id="name" class="form-control mb-3" path="titre"/>
		<form:errors path="titre">
				<p class="text-danger">le nom ne doit pas être vide !</p>
			</form:errors>
		<label for="classes">Classe : </label>
		<form:select class="form-control mb-3" id="classes" path="classe.id" required="required">
			<c:forEach items="${listeClasse}" var="classe">
				<option value="${classe.id}">${classe.nom}</option>
			</c:forEach>
		</form:select>
		<label for="optionslabel">Options :</label>
		<select class="form-control mb-3" name="opt" multiple required="required" >
			<c:forEach items="${listeOption}" var="option">
				<option value="${option.id}">${option.libelle}</option>
<%-- 				<form:options items="${listeOption}" itemLabel="libelle" itemValue="id" /> --%>
			</c:forEach>
		</select>
		<div class="row justify-content-center">
			<input type="submit" class="btn btn-primary col-3"/>
		</div>
	</form:form>
	</div>
</body>
</html>