<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="fr">
<head>
<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Rexam</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Index</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<div class="container">

		<form method="POST" action="${contextPath}/login" class="form-signin" style="max-width: 330px;">
			<h2 class="form-heading">Log in</h2>

			<div class="form-group ${error != null ? 'has-error' : ''}">
				<span>${message}</span> 
				<input name="username" type="text"
					class="form-control" placeholder="Username" autofocus="true" /> 
				<input
					name="password" type="password" class="form-control"
					placeholder="Password" /> 
				<span>${error}</span> 
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

				<button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
			</div>

		</form>
	</div>
	
</body>
</html>
