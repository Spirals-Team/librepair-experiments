# language: es

Característica: Poder enviar incidencias con un agente iniciado en sesión

	Escenario: Seleccionar la opcion de "Enviar Incidencias" de la barra de navegación y rellenar el formulario

		Dado un agente iniciado en sesión
		When el agente intenta acceder a enviar incidencias 
		Entonces es redireccionado a la página "create"
		Y el puede rellanar el formulario
		Y ser redirigido a la página "Ver incidencias" donde ver la incidencia creada