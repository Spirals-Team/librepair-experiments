<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

<style>
input[type=submit] {
    padding:5px 15px; 
    background:#4CAF50; 
    border:1 #f2f2f2;
    cursor:pointer;
    -webkit-border-radius: 5px;
    border-radius: 5px; 
}
</style>

</head>
<body>
<h1>Bienvenido!</h1>
<h1>Menu:</h1>
   <form action="listarDispositivos" method="POST">
   <input type="submit" value="Ver Dispositivos" />
   
   
  </form>
  	<form action="AgregarDispositivos" method="POST">
	<input type="submit" value="Agregar Dispositivos" />
	
	</form>
	<form action="EliminarD" method="POST">
	<input type="submit" value="Eliminar Dispositivos" />
	</form>
	
	<form action="ModificarD" method="POST">
	<input type="submit" value="Modificar Dispositivos" />
	</form>
	
	<form action="PuntosC" method="POST">
	<input type="submit" value="Consultar Puntos" />
	</form>
	
	<input type="button" value="ATRAS" name="Back2" onclick="history.back()" />	

	
</body>
</html>