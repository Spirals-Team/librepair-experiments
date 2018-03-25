
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
<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
<script>
	$("h2").click(function() {
		$("#tu_list").slideToggle();
	});
</script>
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

				<form class="navbar-form navbar-right" action="/action_page.php">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search...">
					</div>
				</form>


			</div>
		</div>
	</nav>
	<div class="container">

		<div class="starter-template">
			<h1>Rexam</h1>

			<h2><c:out value="${teachingUnit.name} :" /></h2>
		
			
				<table class="table table-hover">
					<tr>
						<th>Épreuve</th>
						<th>Type épreuve</th>
						<th>Poids</th>
					</tr>
						<c:forEach items="${teachingUnit.components }" var="component">
							<tr>
								<td><c:out value="${component.exam.code}" /></td>
								<td><c:out value="${component.exam.typeExam}" /></td>
								<td><c:out value="${component.weight}" /></td>
							</tr>

						</c:forEach>
				</table>

		</div>
	</div>
</body>
</html>