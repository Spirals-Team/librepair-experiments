# language: es

 Característica: El agente debe poder ver sus incidencias
 
 	Escenario: Recibir incidencias
 		
 		Dada Incidencia emitida por el productor
 		Cuando el dashboard la recibe
 		Entonces se crea una nueva incidencia para ese agente