function comprobador() {
	var seleccionado= $("#propiedadUmbral option:selected").text();
	
	if (seleccionado === "Temperatura") {
		controlador("ºC", seleccionado);
	} else if (seleccionado=== "Presión") {
		controlador("atm", seleccionado);
	} else if (seleccionado === "Humedad") {
		controlador("%", seleccionado);
	} else if (seleccionado === "Velocidad del Viento") {
		controlador("km/h", seleccionado);
	} else if (seleccionado === "Velocidad de Circulación") {
		controlador("km/h", seleccionado);
	} else if (seleccionado === "Nivel de Polución") {
		controlador("mg/m3", seleccionado);
	}else if(seleccionado === "Calidad del Aire"){
		controlador("índ.", seleccionado)
	}
}

function controlador(unidadMedida, seleccionado) {
	$("#valormaxUnd").text(unidadMedida);
	$("#valorminUnd").text(unidadMedida);
	$('#valorMinimo').val(10);
	$('#valorMaximo').val(10);
	$('#criticoMin').prop('checked', true);
	$('#criticoMax').prop('checked', true);
}


