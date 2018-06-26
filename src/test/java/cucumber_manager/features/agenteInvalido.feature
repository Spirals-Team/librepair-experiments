#language: es

Característica: Inicio de sesión con un usuario inválido

	Escenario: Introducimos los datos de un usuario no existente en el sistema
	
		Dado el email inventado 'facundo@uniovi.es'
			Y su password inventada es 'Contra'
			Y su tipo inventado es 'Persona'
			
		Cuando tratamos de logearnos con esas credenciales erroneas
			
		Entonces el agente no ingresa en el sistema al no estar registrado