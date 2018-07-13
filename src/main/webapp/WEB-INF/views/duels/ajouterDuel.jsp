<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty erreurListeIds}">
	<c:set var="isInvalidClass" value="is-invalid" />
</c:if>
<!doctype html>
<html>
<head>
<title>Duels</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
</head>
<body>
	<div class="container-fluid">
		<h1>Ajouter un duel</h1>
		<form:form method="post" modelAttribute="duel">
			<div class="form-group">
				<label for="stagiairesSelect">Stagiaires</label> <select multiple
					name="listeIds" class="multiselect form-control ${isInvalidClass}"
					id="stagiairesSelect" required>
					<c:forEach items="${listeStagiaires}" var="stagiaire">
						<option value="${stagiaire.id}">${stagiaire.prenom}
							${stagiaire.nom}</option>
					</c:forEach>
				</select> <small id="stagiairesSelectHelpBlock" class="form-text text-muted">
					Sélectionnez les deux stagiaires participant au duel. </small>
				<c:if test="${not empty erreurListeIds}">
					<div class="invalid-feedback">Veuillez choisir deux stagiaires
						différents.</div>
				</c:if>
			</div>
			<div class="form-group">
				<label for="quizzSelect">Quizz</label>
				<form:select class="form-control" id="quizzSelect" path="quizz.id">
					<c:forEach items="${listeQuizzes}" var="quizz">
						<form:option value="${quizz.id}">${quizz.titre}</form:option>
					</c:forEach>
				</form:select>
				<form:errors path="quizz">
					<div class="invalid-feedback">Veuillez choisir un quizz.</div>
				</form:errors>
			</div>
			<button type="submit" class="btn btn-primary">Ajouter</button>
			<a href='<c:url value="/duels/lister" />' class="btn btn-secondary">Retour
				à la liste</a>
		</form:form>
	</div>
</body>
</html>