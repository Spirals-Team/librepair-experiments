# language: es

Característica: Poder ver los detalles de las incidencias con un agente iniciado en sesión

	Escenario: Poder acceder a los detalles de cada incidencia desde la pagina "List"

		Dado un agente iniciado en sesión
		When el agente intenta ver los detalles de una incidencia
		Entonces es redireccionado a la página "details"
		Y el puede ver los detalles de la incidencia