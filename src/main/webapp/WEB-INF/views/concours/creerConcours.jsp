<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="/webjars/bootstrap/4.1.1/css/bootstrap.min.css">
		<title>Evalme App</title>
		</head>
		<body>
			<div class="container-fluid">
				
				<div class="col-sm-5">
		            <h4>Concours:</h4>
		            <div class="panel panel-default">
		                <div class="panel-body form-horizontal payment-form">
		                <form:form modelAttribute="concours">
		                    <div class="form-group">
		                        <label for="Titre" class="col-sm-3 control-label">Titre</label>
		                        <div class="col-sm-9">
		                        	<form:input path="titre" type="text" class="form-control" />
		                        	<form:errors path="titre">Le titre est obligatoire</form:errors>
		                        </div>
		                    </div>
		                    <div class="form-group row">
		                        <label for="participants" class="col-sm-3 control-label">Liste des participants</label>
		                        <div class="col-sm-9">
		                        	<form:select path="participants" items="${participants}" itemLabel="prenom" itemValue="id" class="selectpicker form-control" multiple="true" >                              
		                            </form:select>
		                        </div>
		                    </div> 
		                     <div class="form-group row">
		                        <label for="quizzes" class="col-sm-3 control-label">Liste des quizz</label>
		                        <div class="col-sm-9">
		                        	<form:select path="quizzes" items="${quizzes}"  itemLabel="titre" itemValue="id"  class="selectpicker form-control" multiple="multiple" >           
		                              
		                            </form:select>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-12 text-right">
		                            <button type="submit" class="btn btn-default preview-add-button">
		                                <span class="glyphicon glyphicon-plus" ></span> Add
		                            </button>
		                        </div>
		                    </div>
		                </form:form>    
		                </div>
		            </div>            
		        </div>
			</div>
		</body>
	</html>