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
		<h1>Supprimer un profil de stagiaire</h1>
		<form:form modelAttribute="stagiaire">
			<p>Vous êtes sur le point de supprimer le profil suivant :</p>
			<p><c:out value="${stagiaire.nom} "/> <c:out value="${stagiaire.prenom}"/></p>
			
			<div class="row justify-content-end">
				<div class="col-10">
					<button type="submit" class="btn-delete btn btn-danger">Supprimer</button>
				</div>
			</div>		
		</form:form>

	</div>
	
	
	
</body>

</html>