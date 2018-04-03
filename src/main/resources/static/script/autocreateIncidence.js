$(document).ready(function() { 
	function generateRequest(){
		var http = new XMLHttpRequest();
    	var url = "/inci";
    	
    	http.open("POST", url, true);
    	http.setRequestHeader("Content-Type", "application/json");
    	return http;
	}
	
	// ----------------- funciones que simulan cada sensor --------------------
	function simularSensorDeZoo(){
    	var myDate = new Date();
    	var fechaActual = myDate.getUTCDate() + "/" + (myDate.getMonth()+1) + "/" + myDate.getFullYear();
    	
    	myDate.setDate(myDate.getDate() + 4);
    	var fechaCaducidad = myDate.getUTCDate() + "/" + (myDate.getMonth()+1) + "/" + myDate.getFullYear();
    	
    	generateRequest().send(JSON.stringify({
  		  login: "S2",
  		  password: "123456",
  		  nombreIncidencia: "Sensor de zoológico",  
  		  descripcion: "Sensor automático que se encuentra en el zoológico",  
  		  kind: "sensor",  
  		  fechaEntrada: fechaActual, 
  		  fechaCaducidad: fechaCaducidad,
  		  categorias: "automatico,ambiente",
  		  propiedades: "temperatura/"+ getRandomArbitrary(-50, 100) +",presion/"+ getRandomArbitrary(0, 2) +",humedad/" + getRandomArbitrary(0, 101)  
  		}));   
    } 
	
    function simularSensorDeServidores(){
    	var myDate = new Date();
    	var fechaActual = myDate.getUTCDate() + "/" + (myDate.getMonth()+1) + "/" + myDate.getFullYear();
    	
    	myDate.setDate(myDate.getDate() + 15);
    	var fechaCaducidad = myDate.getUTCDate() + "/" + (myDate.getMonth()+1) + "/" + myDate.getFullYear();
    	
    	generateRequest().send(JSON.stringify({
  		  login: "S1",
  		  password: "123456",
  		  nombreIncidencia: "Sensor de la sala de servidores del edifio central",  
  		  descripcion: "Sensor automático que se encuentra en la sala de servidores del edificio central",  
  		  kind: "sensor",  
  		  fechaEntrada: fechaActual, 
  		  fechaCaducidad: fechaCaducidad,
  		  categorias: "automatico,ambiente",
  		  propiedades: "temperatura/"+ getRandomArbitrary(-50, 100) +",presion/"+ getRandomArbitrary(0, 2) +",humedad/" + getRandomArbitrary(0, 101)  
  		}));   
    } 
    
    function simularSensorDeContaminación(){
    	var myDate = new Date();
    	var fechaActual = myDate.getUTCDate() + "/" + (myDate.getMonth()+1) + "/" + myDate.getFullYear();
    	
    	myDate.setDate(myDate.getDate() + 30);
    	var fechaCaducidad = myDate.getUTCDate() + "/" + (myDate.getMonth()+1) + "/" + myDate.getFullYear();
    	
    	generateRequest().send(JSON.stringify({
  		  login: "S3",
  		  password: "123456",
  		  nombreIncidencia: "Sensor de contaminación de la ciudad de Oviedo",  
  		  descripcion: "Sensor automático que que mide los niveles de contamincación de la ciudad de Oviedo",  
  		  kind: "sensor",  
  		  fechaEntrada: fechaActual, 
  		  fechaCaducidad: fechaCaducidad,
  		  categorias: "automatico,ambiente,contaminacion",
  		  propiedades: "nivel_polucion/"+ getRandomArbitrary(0, 11) +",calidad_aire/"+ getRandomArbitrary(0, 501)  
  		}));   
    } 
    
    function getRandomArbitrary(min, max) {
    	if((Math.random() * (9 - 1) + 1)%2 == 0)
			return Math.random() * ((((max+min)/2)+4) - (((max+min)/2)-4)) + (((max+min)/2)-4);
		else
    	  return Math.random() * (max - min) + min;
    	}
    
    
    
    // ------------------------  Cada x segundos +- se envian datos de ambos sensores -------------
    setInterval(simularSensorDeServidores, 4995);
    setInterval(simularSensorDeZoo, 4997);
    setInterval(simularSensorDeContaminación, 10000);
});