$(document).ready(function() {
	var tabla = $("#tablaIncidenciasBody");

	var socket = new SockJS('/stomp');

	var stompClient = Stomp.over(socket);

	stompClient.connect({}, function(frame) {
		stompClient.subscribe("/topic/incidences", function(data) {
			var incidencia = JSON.parse(data.body);
			var encontrado = false;

			$(".nameIncidencia").each(function(i) {
				if (this.innerHTML === incidencia.contents) {
					encontrado = true;
					return false;
				}
			});

			if (!encontrado) {
				
				var textoDangerous;
				if(incidencia.dangerous) textoDangerous = "Sí";
				else textoDangerous = "No";
				
				var htmlstring = "\
			      <tr>\
			        <td class=\"nameIncidencia\">" + incidencia.name + "</td>\
			        <td>" + incidencia.description + "</td>\
			        <td>" + incidencia.status + "</td>\
			        <td >" + incidencia.expiration + "</td>\
			        <td >" + incidencia.operatorComments + "</td>\
			        <td id='dangerous"+ incidencia.id +"'>" + textoDangerous + "</td>\
			        <td > <iframe width=\"300\" height=\"225\" frameborder=\"0\" style=\"border:0\"" +
								"src=\"https://www.google.com/maps/embed/v1/view?key=AIzaSyAnjyWNjAWTI8Cr80Uqv0thhdpLUpm3cNk&center=" + incidencia.location + "&zoom=18&maptype=satellite\" allowfullscreen></iframe></td>" +
								" </tr>\
			      ";
				tabla.append(htmlstring);
				
				// Si la incidencia es peligrosa, incrementamos el numero de incidencias peligrosas
				if(incidencia.dangerous){
					nTrue++;
					$("#dangerous"+incidencia.id).css("background-color", "rgb(255, 97, 90)");
				}
				else{
					nFalse++;
					$("#dangerous"+incidencia.id).css("background-color", "rgb(157, 255, 251)");
				}
				
				//Actualizamos el grafico
				drawChart();
				
			}
		});
	});
});