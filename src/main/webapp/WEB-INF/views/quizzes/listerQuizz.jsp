<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<link rel="stylesheet"
	href="/webjars/bootstrap/4.1.1/css/bootstrap.min.css">
</head>
<body>
	<h2>Quizzes</h2>
	<table class="table table-striped">
		<thead>
			<tr>
				<th scope="col">Titre</th>
				<th scope="col">Liste des questions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listeQuizz}" var="quizz">
				<tr>
					<td><c:out value="${quizz.titre}"></c:out></td>
					<td>-----------------------------------------------</td>
					<td><a href="/quizzes/editer?id=${quizz.id}"
						class="btn btn-primary" role="button"> Editer </a></td>

					<td>
						<form action="/quizzes/supprimer" method="post"
							id="formSuppr${quizz.id}">
							<input type="hidden" value="${quizz.id}" name="id"> <input
								type="submit" value="Supprimer" class="btn btn-danger">
						</form>
					</td>

					<c:forEach items="${quizz.questions}" var="questions">
						<tr>
							<td></td>
							<td><c:out value="${questions.titre}"></c:out></td>
							<td></td>
							<td></td>
						</tr>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- jQuery first, then poper, then Bootstrap JS. -->
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
		

	<c:forEach items="${listeQuizz}" var="quizz">
		<script type="text/javascript">
		$("#formSuppr${quizz.id}").submit(function(e) {
		    if(!confirm("Voulez-vous vraiment supprimer ce quizz?")){
		         e.preventDefault();
		    }
		});
		</script>
	</c:forEach>
</body>
</html>