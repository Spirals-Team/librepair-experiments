
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html>
<head>
<link rel="stylesheet"
    href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
    integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
    crossorigin="anonymous">
</head>
<body>
    <table class="table table-striped">
        <thead>
            <tr>
                <th scope="col">Concours</th>
                <th scope="col">Liste des Participant</th>
                <th scope="col">Liste des Quizz</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${listeConcours}" var="cc">
                <tr>
                    <td>${cc.titre}</td>
                    <td><c:forEach items="${cc.participants}" var="st">
                           [ ${st.prenom} ${st.nom}]
                        </c:forEach></td>
                   <td> <c:forEach items="${cc.quizzes}" var="st">
                            [${st.titre}]
                        </c:forEach> </td>
                   <td>
		           	<a href="/concours/editer?id=${cc.id}" class="btn btn-secondary active" role="button" aria-pressed="true">Editer</a>
		           	<a href="/concours/supprimer?id=${cc.id}" class="btn btn-secondary active" role="button" aria-pressed="true">Supprimer</a>
                   </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="/concours/creer" class="btn btn-primary active" role="button" aria-pressed="true">Ajouter un nouveau concours</a>
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