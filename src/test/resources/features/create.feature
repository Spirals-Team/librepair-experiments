# language: es

Característica: Poder enviar incidencias con un agente iniciado en sesión

	Escenario: ver la vista de enviar incidencias

		Dado un agente de usuario "Agente1" contraseña "123456" y kind "person" registrado en el sistema
		Cuando el agente realiza correctamente el login con usuario "Agente1" contraseña "123456" y kind "person"
		Y intenta acceder a la página "/incidencia/create"
		Entonces es redireccionado a la página "/incidencia/create"