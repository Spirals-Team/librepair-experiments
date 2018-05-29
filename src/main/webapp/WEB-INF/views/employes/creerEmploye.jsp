<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<! doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <title>PAIE</title>
</head>
<body>

<h1>Ajouter un employé</h1>
  <form:form method="post" modelAttribute="employe">
    <table>
        <tr>
            <td>Matricule</td>
            <td><form:input path="matricule" /></td>
        </tr>
        <tr>
            <td>Entreprise</td>
            <td><form:select path="entreprise.id" items="${listeEntreprises}" itemLabel="denomination" itemValue="id"/></td>
        </tr>
        <tr>
            <td>Profil</td>
            <td><form:select path="profilRemuneration.id" items="${listeProfils}" itemLabel="code" itemValue="id"/></td>
        </tr>
        <tr>
            <td>Grade</td>
           	<td><form:select path="grade.id" items="${listeGrades}" itemLabel="code" itemValue="id"/></td>
        </tr>
    </table>
    <form:button>Valider</form:button>
</form:form>

</body>
</html>