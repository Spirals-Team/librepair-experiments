<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Smart APP Grupo 2</title>
</head>
<body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<div>
	<img src="C:/Users/s.agosta/Desktop/SGE-DDS/sge-tp/WebContent/app/Resources/Images/Banner1.PNG" height="100">
   <form action="AgregarD" method="POST">
   <br>
   <h1>AGREGAR UN DISPOSITIVO</h1>
  	 <select name='id_categoria' id='id_categoria'>
        <option value="1" selected>Estandar</option>
        <option value="2">Inteligente</option>
        
    </select>
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
   <input id="id_input" type="text" name="Estado" value="Estado" disabled />
   <br>
   <h2></h2>
   <input id="id_input1" type="text" name="Humedad" value="Humedad" disabled />
   <br>
   <h2></h2>
   <input id="id_input2" type="text" name="Movimiento" value="Movimiento" disabled />
   <br>
   <h2></h2>
   <input id="id_input3" type="text" name="Temperatura" value="Temperatura" disabled />
   <br>
   
   
   
   
   <h2></h2>
   <input type="submit" value="Agregar" />
   <input type="button" value="ATRAS" name="Back2" onclick="history.back()" />
   <h2></h2>
   <img src="C:/Users/s.agosta/Desktop/SGE-DDS/sge-tp/WebContent/app/Resources/Images/Banner1.PNG" height="100">
</form>
</div>
<script type="text/javascript">
        $( function() {
    $("#id_categoria").change( function() {
        if ($(this).val() === "1") {
            $("#id_input").prop("disabled", true);
            $("#id_input1").prop("disabled", true);
            $("#id_input2").prop("disabled", true);
            $("#id_input3").prop("disabled", true);
        } else {
            $("#id_input").prop("disabled", false);
            $("#id_input1").prop("disabled", false);
            $("#id_input2").prop("disabled", false);
            $("#id_input3").prop("disabled", false);

        }
    });
});
    </script>

</body>
</html>


