<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<! doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
  <title>PAIE</title>
</head>
<body>
<h1>Liste des employés</h1>

<div class = "col-sm-4">
<a href="creer" class="list-group-item list-group-item-action list-group-item-secondary">Ajouter un employé</a>
</div>

<table class="table table-striped">
	<thead>
		<tr>
			<th scope="col">Date/heure création</th>
			<th scope="col">Matricule</th>
			<th scope="col">Grade</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="employe" items="${employes}">
		<tr>
			<td>${employe.heureCreation}</td>
			<td>${employe.matricule}</td>
			<td>${employe.grade.code}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</body>