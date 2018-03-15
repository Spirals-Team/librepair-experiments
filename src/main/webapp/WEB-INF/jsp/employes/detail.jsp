<%@ page import="com.ipiecoles.java.java320.model.Employe" %>
<%@ page import="com.ipiecoles.java.java320.model.Commercial" %>
<%@ page import="com.ipiecoles.java.java320.model.Technicien" %>
<%@ page import="com.ipiecoles.java.java320.model.Manager" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../tags/header.jsp" %>

<% Employe employe = (Employe) pageContext.findAttribute("model");%>
<div class="container">
    <h2>Détail du ${model.className} ${model.matricule}</h2>

    <div class="row">
        <form id="saveForm" action="/<%= employe.getClassName().toLowerCase() + "s"%>/<%= employe.getId() == null ? "save" : employe.getId() %>" method="post">
        <div class="col-lg-6">
            <div class="form-group">
                <label class="form-control-label" for="nom">Nom</label>
                <input type="text" value="${model.nom}" class="form-control" name="nom" id="nom">

                <label class="form-control-label" for="prenom">Prénom</label>
                <input type="text" value="${model.prenom}" class="form-control" name="prenom" id="prenom">

                <label class="form-control-label" for="matricule">Matricule</label>
                <input type="text" value="${model.matricule}" class="form-control" name="matricule" id="matricule">
            </div>
        </div>
        <div class="col-lg-6">
            <div class="form-group">
                <label class="form-control-label" for="nom">Salaire</label>
                <div class="input-group">
                    <input type="number" value="${model.salaire}" class="form-control" name="salaire" id="salaire">
                    <span class="input-group-addon">€</span>
                </div>

                <c:if test="!isNew">

                <label class="form-control-label" for="nom">Prime Annuelle</label>
                <div class="input-group">
                    <input type="text" value="${model.primeAnnuelle}" class="form-control" name="primeAnnuelle" id="primeAnnuelle">
                    <span class="input-group-addon">€</span>
                </div>

                </c:if>

                <label class="form-control-label" for="nom">Date d'embauche</label>
                <input type="text" value="${model.dateEmbauche.toString("dd/MM/YYYY")}" class="form-control" name="dateEmbauche" id="dateEmbauche">

                <% if (employe instanceof Commercial) { %>
                <label class="form-control-label" for="performance">Performance</label>
                <input type="number" value="${model.performance}" class="form-control" name="performance" id="performance">

                <label class="form-control-label" for="caAnnuel">CA Annuel</label>
                <div class="input-group">
                    <input type="number" value="${model.caAnnuel}" class="form-control" name="caAnnuel" id="caAnnuel">
                    <span class="input-group-addon">€</span>
                </div>
                <% } %>

                <% if (employe instanceof Technicien) { %>
                <label class="form-control-label" for="grade">Grade</label>
                <input type="number" value="${model.grade}" class="form-control" name="grade" id="grade">
                <% } %>



                <% if (employe instanceof Manager && employe.getId() != null) { %>
                <label class="form-control-label" for="performance">Equipe</label>
                <div class="row">
                    <div class="col-lg-10">
                        <ul class="list-group">
                            <c:forEach items="${model.equipe}" var="tech">
                                <li class="list-group-item"><a href="employes/${tech.id}">${tech.prenom} ${tech.nom} <span class="badge pull-right">${tech.matricule}</span></a></li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="col-lg-2 text-center">
                        <div class="list-group text-center">
                            <c:forEach items="${model.equipe}" var="tech">
                                <li class="list-group-item"><a href="/managers/${model.id}/techniciens/${tech.id}/delete"><span class="glyphicon glyphicon-remove"></span></a></li>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
        </form>
        <div class="col-lg-6">
            <input form="saveForm" class="btn btn-primary" type="submit" value="Enregistrer"></input>
            <c:if test="${!empty model.id}">
                <a href="/employes/${model.id}/delete" class="btn btn-danger">Supprimer</a>
            </c:if>
        </div>
        <div class="col-lg-6">
            <% if (employe instanceof Manager && employe.getId() != null) { %>
            <form action="/managers/${model.id}/techniciens/add" method="get">
                <div class="col-lg-10">
                    <input type="text" name="matricule" value="" placeholder="Ajouter un technicien avec le matricule..." class="form-control">
                </div>
                <div class="col-lg-2 text-center">
                    <button type="submit" class="btn-success list-group-item list-group-item-action"><span class="glyphicon glyphicon-plus"></span></button>
                </div>
            </form>
            <% } %>
            <% if (employe instanceof Technicien && employe.getId() != null) { %>
                <div class="row">
                    <% if(((Technicien) employe).getManager() != null) { %>
                    <div class="col-lg-12">
                        <label class="form-control-label">Manager</label>
                    </div>
                    <div class="col-lg-10">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="/employes/${model.manager.id}">${model.manager.prenom} ${model.manager.nom}
                                    <span class="badge pull-right">${model.manager.matricule}</span></a>
                            </li>
                        </ul>
                    </div>
                    <div class="col-lg-2">
                        <li class="list-group-item"><a href="/techniciens/${model.id}/manager/${model.manager.id}/delete"><span class="glyphicon glyphicon-remove"></span></a></li>
                    </div>
                    <% } else { %>
                    <form action="/techniciens/${model.id}/manager/add" method="get">
                    <div class="col-lg-10">
                        <input type="text" name="matricule" value="" placeholder="Ajouter un manager avec le matricule..." class="form-control">
                    </div>
                    <div class="col-lg-2 text-center">
                        <button type="submit" class="btn-success list-group-item list-group-item-action"><span class="glyphicon glyphicon-plus"></span></button>
                    </div>
                    </form>
                    <% } %>
                </div>
            <% } %>
        </div>
    </div>
</div>

<%@ include file="../tags/footer.jsp" %>
