<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-dark" style="background-color: #e3f2fd;">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#navbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
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
				<c:out value="${user.firstname} ${user.lastname}" />
			</p>

			<form class="navbar-form navbar-left" action="/rexam/showTeachingUnits" method="get">
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
			<form id="logoutForm" method="POST" action="${contextPath}/logout">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#" onclick="document.forms['logoutForm'].submit()"><span
						class="glyphicon glyphicon-log-out"></span> Logout</a></li>
			</ul>
		</div>
	</div>
</nav>