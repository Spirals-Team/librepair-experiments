<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous" title="Lister Questions">
</head>
<body>
	<nav></nav>
	<h1>Liste des questions</h1>
	<div class="container">
		<div class="row">
			<div class="col-sm"><a href="<%=request.getContextPath()%>/questions/listerOption">Aller aux options</a></div>
			<div class="col-sm"><a href="<%=request.getContextPath()%>/questions/creer">Créer une question</a></div>
			<div class="col-sm"><a href="<%=request.getContextPath()%>/questions/maj">Éditer une question</a></div>
			<div class="col-sm"><a href="<%=request.getContextPath()%>/questions/supprimer">Supprimer une question</a></div>
		</div>
	</div>

	<table class="table table-striped">
		<thead>
			<tr>
				<th scope="col">Titre</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ listeQuestions}" var="qu">
				<tr>
					<td>${ qu.titre } - Option(s) : <c:forEach
							items="${ qu.options }" var="rep">
							<br>------- ${ rep.libelle }
			</c:forEach>
			</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

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