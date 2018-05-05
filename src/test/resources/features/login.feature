# language: es

Característica: Poder hacer login con un agente

	Escenario: Hacer petición post de login con un agente

		Dado un agente registrado en la base de datos
		Cuando el agente intenta hacer login con el usuario "Agente1", la clave "123456" y kind "person"
		Entonces consigue hacer login 
		Y el agente es redireccionado a la página "Home"