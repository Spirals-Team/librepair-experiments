<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="/rexam/showTeachingUnits">Rexam</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">

				<li><a href="/rexam/showTeachingUnits">Liste des UE</a></li>

				<li><a href="/rexam/regs">Liste des inscriptions</a></li>

				<li><a href="/rexam/results">Liste des résultats</a></li>

			</ul>

			<p class="navbar-text">
				<c:out value="Année ${currentYear-1}-${currentYear}" />
			</p>

			<p class="navbar-text">
<%-- 				<c:out --%>
<%-- 					value="Connecté en tant que ${student.firstName} ${student.lastName}" /> --%>
			</p>

			<form class="navbar-form navbar-left" action="/rexam/search" method="get">
				<div class="input-group">
					<input name="searchTerm" type="text" class="form-control"
						placeholder="Rechercher...">
					<div class="input-group-btn">
						<button class="btn btn-default" type="submit">
							<i class="glyphicon glyphicon-search"></i>
						</button>
					</div>
				</div>
			</form>
			<form class="navbar-form" id="logoutForm" method="POST"
				action="${contextPath}/logout">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			<a class="nav navbar-nav"
				onclick="document.forms['logoutForm'].submit()"><button
					class="btn btn-danger">Logout</button></a>

		</div>
	</div>
</nav>