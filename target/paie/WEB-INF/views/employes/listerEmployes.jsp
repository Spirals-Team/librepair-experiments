<html>
<head>
<meta charset="UTF-8">
<title>PAIE</title>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
	integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
	integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
	integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
	crossorigin="anonymous"></script>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item mr-2"><a href = "<c:url value = "/mvc/employes/lister"/>">Employ�s</a>
						<span class="sr-only">(current)</span>
				</li>
				<li class="nav-item mr-2"><a href = "<c:url value = "/mvc/bulletins/lister"/>">Bulletins</a>
				</li>
			</ul>
		</div>
	</nav>
	<h1 class="text-center">Liste des employ�s</h1>
	<a href="/paie/mvc/employes/creer" class="m-5 float-right"><button>Ajouter un employ�</button></a>
	<table class="table table-striped">
		<thead>
			<tr>
				<th scope="col">Date/Heure de cr�ation</th>
				<th scope="col">Matricule</th>
				<th scope="col">Grade</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${employes}" var="employe">
				<tr>
					<td><c:out value="${employe.dateDeCreation}" /></td>
					<td><c:out value="${employe.matricule}" /></td>
					<td><c:out value="${employe.grade.code}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>