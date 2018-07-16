<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html>
<head>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
</head>
<body>

	<h2>List des différents examens :</h2>

	<table class="table table-striped">
		<tr>
			<th>Titre</th>
			<th>Quizz</th>
			<th>Classe</th>
			<th>Moy</th>
			<th>Edition</th>
			<th>Suppression</th>
		</tr>
		<c:forEach items="${ examList}" var="exam">
			<tr>
				<form action="/examens/supprimer" method="post" id="formSuppr${exam.id}">
					<td>${exam.titre}</td>
					<td>${exam.quizz.titre}</td>
					<td>${exam.classe.nom}</td>
					<td>${exam.getAvg()}</td>
					<td><a href="/examens/editer?id=${exam.id}"
						class="btn btn-secondary" role="button"> Editer </a></td>
					<td><input type="hidden" value="${exam.id}" name="id">
						<input type="submit" value="Supprimer" class="btn btn-danger">
					</td>
				</form>
			</tr>
		</c:forEach>
	</table>

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
	
	
	<c:forEach items="${ examList}" var="exam">
		<script type="text/javascript">
		$("#formSuppr${exam.id}").submit(function(e) {
		    if(!confirm("Confirmez vous la supression de la donnée ?")){
		         e.preventDefault();
		    }
		});
		</script>
	</c:forEach>
	
</body>
</html>