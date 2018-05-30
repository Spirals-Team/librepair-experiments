<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<! doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Creation Bulletin</title>
   <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
	<h1>Créer Bulletin de Salaire</h1>
	<form:form method="post" modelAttribute="bulletinSalaire">
	<div class="form-group row">
		<label for="periode" class="col-sm-2 col-form-label">Période</label>
		<div class = "col-sm-10">
			<form:select path="periode.id" items="${listePeriodes}" itemLabel="datePeriode" itemValue="id"/>
		</div>
		<label for="matricule" class="col-sm-2 col-form-label">Matricule</label>
		<div class = "col-sm-10">
			<form:select path="remunerationEmploye.id" items="${listeMatricules}" itemLabel="matricule" itemValue="id"/>
		</div>
		<label for="primeExceptionnelle" class="col-sm-2 col-form-label">Prime exceptionnelle</label>
		<div class = "col-sm-10">
			<form:input path="primeExceptionnelle" type="number"/>
		</div>
		<div class = "col-sm-2">
		<form:button>Créer</form:button>
		</div>
		
	</div>
	</form:form>
</body>