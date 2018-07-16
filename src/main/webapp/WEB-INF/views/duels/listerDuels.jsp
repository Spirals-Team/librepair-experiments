<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<h1>Liste des duels</h1>
		<div class="list-group">
			<c:forEach items="${listeDuels}" var="duel">
				<a href="editer?id=${duel.id}"
					class="list-group-item list-group-item-action"> ${duel.id} -
					${duel.stagiaireA.nom} vs. ${duel.stagiaireB.nom} dans
					"${duel.quizz.titre}" </a>
			</c:forEach>
		</div>
		<a href='<c:url value="/duels/ajouter" />' class="btn btn-primary mt-2">Ajouter
			un duel</a>
	</div>
</body>
</html>