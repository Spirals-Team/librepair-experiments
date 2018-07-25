<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="/webjars/bootstrap/4.1.1/css/bootstrap.min.css">
<title>Evalme App</title>
</head>
<body>
	<div class="container-fluid">
		<form:form modelAttribute="classe" method="post">
			<p>Vous êtes sur le point de supprimer la classe suivante :</p>
			<p>${classe.nom}</p>
			<br/>
			<p>Cette classe est constituée des stagiaires suivants :<br/>
			<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col">Nom</th>
							<th scope="col">Prenom</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${listeStagiairesClasse}" var="stc">
							<tr>
								<td>${stc.nom}</td>
								<td>${stc.prenom}</td>
						</c:forEach>
					</tbody>
				</table>
			
			<div class="row justify-content-end">
				<div class="col-10">
					<button type="submit" class="btn-delete btn btn-danger">Supprimer</button>
				</div>
			</div>		
		</form:form>

	</div>
	
	
	
</body>

</html>