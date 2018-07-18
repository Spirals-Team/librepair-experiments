<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!doctype html>
<html>
<head>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet" href="ajouterExamen.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row centered-form">
			<div class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Ajout d'un nouveau quizz</h3>
					</div>
					<div class="panel-body">
						<form:form method="post" modelAttribute="examen" role="form" action="/examens/editer">
							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6">
									<div class="form-group">
										<form:select path="classe.id" class="form-control input-sm">
											<option value="NONE" label="--- Select ---" />
											<form:options items="${classeList}" itemValue="id"
												itemLabel="nom" />
										</form:select>
									</div>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6">
									<div class="form-group">
										<form:select path="quizz.id" class="form-control input-sm">
											<option value="NONE" label="--- Select ---" />
											<form:options items="${quizzList}" itemValue="id" itemLabel="titre" />
										</form:select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12 col-sm-12 col-md-12">
									<div class="form-group">
										<form:input path="titre" class="form-control input-sm"
											placeholder="Titre du quizz" />
											<form:errors path="titre"><font color="red">Le titre ne peut être vide</font></form:errors>
									</div>
								</div>
							</div>
							<form:hidden path = "id" value = "${examen.id}"/>
							<input type="submit" value="Valider" class="btn btn-info btn-block">
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
		<table class="table table-striped col-12">
			<tr>
				<th>Stagiaire</th>
				<th>Note</th>
			</tr>
			<c:forEach items="${ noteList}" var="note">
				<tr>
					<td>${note.stagiaire.prenom}</td>
					<td>${note.noteSur20}</td>
				</tr>
			</c:forEach>
		</table>
	
	<h4>Ajout d'une note pour un stagiaire :</h4>
	<div class="row">
		<form:form method="post" modelAttribute="note" role="form" action="/examens/editer/note">
			<div class=" col-12 col-sm-4">
			<form:select path="stagiaire.id" class="form-control input-sm ">
				<option value="NONE" label="--- Select ---" />
				<form:options items="${listStagiaire}" itemValue="id" itemLabel="prenom" />
			</form:select>
			</div>
			<div class="col-12 col-sm-4">
				<form:input path="noteSur20"/>
				<form:errors path="noteSur20"><font color="red">Note doit être entre 0 et 20</font></form:errors>
			</div>
			<div class="col-12 col-sm-4">
				<input type="submit" value="Valider" class="btn btn-info">
			</div>
			<form:hidden path = "examen.id" value = "${examen.id}"/>
		</form:form>
	</div>
</div>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
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