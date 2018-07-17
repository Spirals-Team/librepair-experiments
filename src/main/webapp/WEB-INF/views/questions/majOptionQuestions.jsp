<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous" title="Mettre à jour OptionQuestions">
</head>
<body>
	<h1>Mettre à jour une option pour les questions</h1>


	<form:form modelAttribute="option">
	
	<h2>Choisissez l'option à modifier</h2>
	<table class="table table-striped">
		<thead>
			<tr>
				<th scope="col">Option</th>
				<th scope="col">Ok ?</th>
				<th scope="col">À cocher</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ listeOptionQuestions}" var="oq">
				<tr>
					<td>${ oq.libelle }</td>
					<td>${oq.ok}</td>
					<td><input type="radio" name="choix" value="${ oq.id }" required="required"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<h2>Modification à effectuer</h2>
		<table class="table table-striped">
			<tbody>
				
				<tr>
					<td>Libelle :</td>
					<td><form:input path="libelle" required="required" /> 
					<form:errors path="libelle">/!\Veuillez entrer un libelle/!\</form:errors></td>
				</tr>
				<tr>
					<td>Bonne ou mauvaise réponse ? :</td>
					<td><form:select path="ok" required="required">
							<option value="false">Mauvaise réponse</option>
							<option value="true">Bonne réponse</option>
					</form:select></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" value="Valider">
	</form:form>


	<!-- jQuery first, then poper, then Bootstrap JS. -->
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