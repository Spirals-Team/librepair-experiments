<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="fr">
<head>
<%@ page contentType="text/html; charset=UTF-8"%>
<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
	<%@include file="menu_student.jsp"%>

	<div class="container">
		<div class="starter-template">
			<div class="page-header">
				<h1>Rexam</h1>
				<h2>Résultats de la recherche pour ${searchTerm } :</h2>
			</div>
			<ul class="list-group">
				<c:if test="${not empty searchResults}">
					<c:forEach items="${searchResults}" var="result">
						<li class="list-group-item"><c:out
								value="${result.name} (${result.discipline})" /></li>
					</c:forEach>
				</c:if>
				<c:if test="${empty searchResults}">
					<div class="alert alert-danger">
						<strong>Erreur:</strong> Aucun résultat trouvé !
					</div>
				</c:if>

			</ul>
		</div>
	</div>

</body>
</html>