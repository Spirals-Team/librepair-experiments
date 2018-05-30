<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE>
<html>
	<head>
		<link rel="stylesheet" href="../bootstrap-3.3.7-dist/css/bootstrap-theme.css" >
	</head>
	<body>
		<div class="container">
			<div class="row"> <h1>Ajouter un employ&eacute</h1></div>
			<div class="row">
				<form:form method="post" modelAttribute="employer">
			
				<div class="col col-md-4">
					<label>Matricule</label><br/>
					<label>Entreprise</label><br/>
					<label>Profil</label><br/>
					<label>Grade</label><br/>
				</div>
				
				<div class="col col-md-8">
					<input type="text" class="form-control" placeholder="matricule"><br/>
					<input type="text">
				</div>
				</form:form>
			</div>
		</div>
				
		
	</body>
</html>