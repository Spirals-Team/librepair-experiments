# language: es

Característica: Poder ver incidencias con un agente iniciado en sesión

	Escenario: Seleccionar la opcion de "Ver Incidencias" de la barra de navegación

		Dado un agente iniciado en sesión
		Cuando el agente intenta acceder a las incidencias que envia
		Entonces es redireccionado a la página "list"
		Y consigue ver sus incidencias