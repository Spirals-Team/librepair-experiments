#language: es

Característica: Inicio de sesión con un usuario válido

	Escenario: Introducimos los datos de un usuario existente en el sistema
	
		Dado el email del agente es 'alvaro@uniovi.es'
			Y su password es 'Contra'
			Y su tipo es 'Persona'
			
		Cuando tratamos de logearnos con esas credenciales
			
		Entonces el agente ingresa en el sistema