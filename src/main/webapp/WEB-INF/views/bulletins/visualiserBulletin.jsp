<%@ page import="java.util.List" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/bootstrap-4.1.1-dist/css/bootstrap.css">
<title>Bulletin de salaire</title>
</head>

<body>
	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a href="../employes/lister"
					class="nav-link">Employés</a></li>
				<li class="nav-item active"><a href="../bulletins/lister"
					class="nav-link">Bulletins</a></li>
			</ul>
		</div>
	</nav>

	<div class="container">
		<h2>Bulletin de salaire</h2>

		<div class="row">
			<div class="col-4">
				<p>
					<b>Entreprise</b>
				</p>
				<p>${employe.entreprise.denomination }</p>
				<p>SIRET: ${employe.entreprise.siret}</p>
			</div>
			<div class="offset-4 col-4">
				<p>
					<b>Période</b>
				</p>
				<p>Du ${bulletin.periode.dateDebut } au ${bulletin.periode.dateFin}</p>
				<br />
				<p>
					<b>Matricule: ${employe.matricule}</b>
				</p>
			</div>
		</div>

		<h5>Salaire</h5>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Rubriques</th>
					<th>Base</th>
					<th>Taux salarial</th>
					<th>Montant salarial</th>
					<th>Taux patronal</th>
					<th>Cot. patronales</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Salaire de base</td>
					<td>XXX <!-- ${calcul.salaireDeBase }--></td>
					<td>XXX</td>
					<td>XXX</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td>Prime Except.</td>
					<td></td>
					<td></td>
					<td>${bulletin.primeExceptionnelle }</td>
					<td></td>
					<td></td>
				</tr>
				<tr></tr>
				<tr>
					<td>Salaire brut</td>
					<td></td>
					<td></td>
					<td>XXX<!-- ${calcul.salaireBrut }--></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>

		<h5>Cotisations</h5>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Rubriques</th>
					<th>Base</th>
					<th>Taux salarial</th>
					<th>Montant salarial</th>
					<th>Taux patronal</th>
					<th>Cot. patronales</th>
				</tr>
			</thead>
			<tbody>
				<!--<c:forEach></c:forEach>-->
				<tr>
					<td>Total retenue</td>
					<td></td>
					<td></td>
					<td>XXX <!-- ${calcul.totalRetenueSalarial }--></td>
					<td></td>
					<td>XXX <!-- ${calcul.totalCotisationsPatronnals }--></td>
				</tr>
			</tbody>
		</table>

		<h5>NET Imposable: XXX <!-- ${calcul.netImposable}--></h5>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Rubriques</th>
					<th>Base</th>
					<th>Taux salarial</th>
					<th>Montant salarial</th>
					<th>Taux patronal</th>
					<th>Cot. patronales</th>
				</tr>
			</thead>
			<tbody>
				<!--<c:forEach></c:forEach>-->
			</tbody>
		</table>
	</div>
	
	<div class="row">
		<div class="offset-9 col-3">
			<h5>NET A PAYER: XXX <!-- ${calcul.netAPayer}--></h5>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
		integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
		crossorigin="anonymous"></script>
</body>
</html>