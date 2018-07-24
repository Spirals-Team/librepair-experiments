<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="/webjars/bootstrap/4.1.1/css/bootstrap.min.css">
<title>Evalme App</title>
</head>

<body>
	<div class="container-fluid">
		<h1>Nouveau Quizz</h1>
		<form:form modelAttribute="quizz">
			<div class="form-group row justify-center">
				<label for="inputTitle" class="col-md-1 col-form-label">Titre</label>
				<div class="col-sm-8">
					<form:input path="titre"/>
					<form:errors path="titre">
						<font color="red">Titre obligatoire (minimum 5 caractères)</font>
					</form:errors>
				</div>
			</div>
			<div class="form-group row justify-center">
				<div class="col-sm-8">
					<form:select path="questions">
						<form:options items="${questions}" itemValue="titre"
							itemLabel="titre" />
					</form:select>
					<form:errors path="questions">
						<font color="red">Choisissez au moins une question de la
							liste</font>
					</form:errors>
				</div>
			</div>
			<div class="row justify-content">
				<div class="col-2">
					<button type="submit" class="btn-create btn btn-primary">Créer</button>
				</div>
			</div>
		</form:form>
	</div>


</body>
</html>