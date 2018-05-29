<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employes</title>
<script src="<c:url value='bootstrap/js/bootstrap.js'  />" ></script>
<link href="<c:url value='/bootstrap/css/bootstrap.css' />"   rel="stylesheet" media="screen">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">

		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav">
				<li class="nav-item active "><a class="nav-link" href="<c:url value='mvc/employes/lister'/>">Employés
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Bulletins</a>
				</li>

			</ul>
		</div>
	</nav>
	<div class="text-right">
     	 <a href=creer class=" btn btn-info">Ajouter un nouveau Employe</a>
    </div>
	<h1>List des employés</h1>

	<table class="table">
		<tr>
			<th>Date/heure création</th>
			<th>Matricule </th>
			<th>Grade</th>
		</tr>
		<c:forEach items="${employes}" var="col" >
			<td> <c:out value="${col.dateCreation}"></c:out> </td>
			<td> <c:out value="${col.matricule}" ></c:out> </td>
			<td> <c:out value="${col.grade}"></c:out>	
		</c:forEach>
	
	</table>

	
</body>
</html>