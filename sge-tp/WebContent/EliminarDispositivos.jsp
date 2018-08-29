<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Smart APP Grupo 2</title>
</head>
<body>
	<img src="C:/Users/s.agosta/Desktop/SGE-DDS/sge-tp/WebContent/app/Resources/Images/Banner1.PNG" height="100">
   <form action="EliminarD" method="POST">
   <br>
   <h1>AGREGAR UN DISPOSITIVO</h1>
   <input type="text" name="Especie" value="Especie" />
   <br>
   <h2></h2>
   <input type="text" name="Tipo" value="Tipo" />
   <br>
   <h2></h2>
   <input type="text" name="Nombre" value="Nombre" />
   <br>
   <h2></h2>
   <input type="text" name="Consumo" value="Consumo" />
   <br>
   
   
   <h2></h2>
   <input type="submit" value="Eliminar" />
   <input type="button" value="ATRAS" name="Back2" onclick="history.back()" />
   <h2></h2>
   <img src="C:/Users/s.agosta/Desktop/SGE-DDS/sge-tp/WebContent/app/Resources/Images/Banner1.PNG" height="100">
</form>
</body>
</html>