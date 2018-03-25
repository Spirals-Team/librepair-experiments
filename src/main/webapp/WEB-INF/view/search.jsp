
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="fr">
<head>
<%@ page contentType="text/html; charset=UTF-8"%>
<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

</head>

<body>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="/">Rexam</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/">Index</a></li>


					<li class="active"><a href="/showTeachingUnits">Liste des
							UE</a></li>
				</ul>

				
				<form class="navbar-form navbar-right" action="/search" method="get">
					<div class="input-group">
						<input name="searchTerm" type="text" class="form-control" placeholder="Rechercher...">
						<div class="input-group-btn">
						<button class="btn btn-default" type="submit">
						<i class="glyphicon glyphicon-search"></i>
						</button>
						</div>
					</div>
				</form>


			</div>
		</div>
	</nav>
	<div class="container">

		<div class="starter-template">
			<h1>Rexam</h1>

			<h2>Résultats de la recherche pour ${searchTerm } :</h2>

			<c:forEach items="${searchResults}" var="result">
			
					<h3>
						<c:out value="${result.name}" />
					</h3>

					
				
			</c:forEach>
		</div>
	</div>

	
</body>
</html>