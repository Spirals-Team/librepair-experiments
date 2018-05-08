var categories = ["#drivinVelocity", "#windVelocity", "#preasure", "#humedad", "#temperature"];

function comprobador() {
	if ($("#category option:selected").text() === "Accidente en Carretera") {
		controlador(false, false, true, true, false);
	} else if ($("#category option:selected").text() === "Inundación") {
		controlador(true, true, true, false, false);
	} else if ($("#category option:selected").text() === "Accidente Aéreo") {
		controlador(false, false, false, false, false);
	} else if ($("#category option:selected").text() === "Incendio") {
		controlador(true, false, true, true, false);
	} else if ($("#category option:selected").text() === "Meteorología") {
		controlador(true, false, false, false, false);
	} else if ($("#category option:selected").text() === "Otro") {
		controlador(false, false, false, false, false);
	}
}

function controlador(vCircu, vViento, presion, humedad, temperatura) {
	$("#drivinVelocity").attr('disabled', vCircu);
	$("#windVelocity").attr('disabled', vViento);
	$("#preasure").attr('disabled', presion);
	$("#humedad").attr('disabled', humedad);
	$("#temperature").attr('disabled', temperatura);
	vaciar();
}

function vaciar(){
	var i;
	for (i = 0; i < categories.length; i++){
		if ($(categories[i]).prop('disabled')==true){
			$(categories[i]).val("");
		}
	}
}

function validar(){
	var i;
	var isCorrect = true;
	for (i = 0; i < categories.length; i++){
		if ($(categories[i]).prop('disabled')===false && isNaN($(categories[i]).val()) && isCorrect){
			isCorrect = false;
		}
	}
	if (isCorrect){
		event.returnValue=true;
	} else {
		alert("Solo se admiten números en las propiedades.");
		event.returnValue=false;
	}
	
}