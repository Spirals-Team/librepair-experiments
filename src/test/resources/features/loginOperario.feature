# language: es
Característica: Ser capaz de iniciar sesion como Operario 

	Como operario registrado en el sistema quiero poder acceder a mis incidencias asignadas.

	Escenario: Login correcto
		Dado un operario de nombre "Id1" y contraseña "123456" registrado en el sistema
		Y situado en la página "/login"
		Cuando hago login con usuario "Id1" y password "123456"  introduciendo los datos en los campos
		Entonces soy redireccionado a la página "/users/operario"
		
	Escenario: Login incorrecto
		Dado un operario de nombre "Id1" contraseña "12345"  no registrado en el sistema
		Y situado en la página "/login"
		Cuando hago login con usuario "Id1" y password "12345"  introduciendo los datos en los campos
		Entonces soy redireccionado a la página "/login?error"