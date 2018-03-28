<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="fr">
<head>
<!-- Access the bootstrap Css like this,
		Spring boot will handle the resource mapping automcatically -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<body>

	<nav class="navbar navbar-dark" style="background-color: #e3f2fd;">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#navbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Rexam</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Login</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container">
			<form method="POST" action="${contextPath}/login" class="form-signin" style ="max-width:330px;margin: 0 auto;">
				<h2 class="form-signin-heading">Login</h2>

				<div class="form-group ${error != null ? 'has-error' : ''}">
					<span>${message}</span> <input name="username" type="text"
						class="form-control" placeholder="Mail" autofocus /> <input
						name="password" type="password" class="form-control"
						placeholder="Mot de passe" /> <span>${error}</span> <input
						type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

					<button class="btn btn-lg btn-primary btn-block btn-signin"
						type="submit">Log In</button>
				</div>

			</form>
		</div>
</body>
</html>
