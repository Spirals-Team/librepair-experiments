# language: es

Característica: Poder ver la lista de incidencias con un agente iniciado en sesión
Escenario: ver la lista de incidencias
	Acceder a la lista de incidencias de un agente en sesión
	
		Dado un agente de usuario "Agente1" contraseña "123456" y kind "person" registrado en el sistema
		Cuando el agente realiza correctamente el login con usuario "Agente1" contraseña "123456" y kind "person"
		Y intenta acceder a la página "/incidencia/list"
		Entonces es redireccionado a la página "/incidencia/list"