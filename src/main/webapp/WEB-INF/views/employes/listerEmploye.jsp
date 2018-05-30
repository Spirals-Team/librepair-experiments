
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
   
    <title>Page Title</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">	</head>

</head>
<body>
    <div>
    	<div class="row">
    		<h1>Lister les employes</h1>
    	</div>
		<div class="row">
			<div class="col-md-8">
			</div>
			<div>
			  <a href="creer"class="list-group-item list-group-item-primary">Ajouter employe</a>
			
			</div>
		</div>
		<div class="row">
			    <table class="table table-striped">
			        <thead>
				          <tr>
				            <th scope="col">Date/Heure de création</th>
				            <th scope="col">Matricule</th>
				            <th scope="col">Grade</th>
				          </tr>
			        </thead>
			        <tbody>
				         <c:forEach var="item" items="${listeRemu}">
				        	 <tr>
				            <td>${item.dateCreation}</td>
				            <td>${item.matricule}</td>
				            <td>${item.grade.code}</td>
				          </tr>
				        
				        </c:forEach>
				
			        </tbody>
			      </table>
		</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
	</div>	
</body>
</html>