<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
<head>
<link rel="stylesheet" href="/webjars/bootstrap/4.1.1/css/bootstrap.css" />
</head>
<body>
	<h1>Liste des sondages</h1>

	<table class="table">
		<thead class="thead-dark">
			<tr>
				<th scope="col">Titre</th>
				<th scope="col">Classe</th>
				<th scope="col">Option</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listeSondages}" var="sondage">
				<tr>
					<td scope="col">${sondage.titre}
					<td scope="col">${sondage.classe.nom}
					<td scope="col">
					<c:forEach items="${sondage.options}" var="option">
					
						${option.libelle} <br> ${option.description} <br>

					</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>