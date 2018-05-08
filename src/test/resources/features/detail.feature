# language: es

Característica: Poder ver los detalles de las incidencias con un agente iniciado en sesión
Escenario: detalles incidencias
	Acceder a los detalles de una incidencia

		Dado un agente iniciado en sesión
		Cuando el agente intenta ver los detalles de una incidencia
		Entonces es redireccionado a la página "details"