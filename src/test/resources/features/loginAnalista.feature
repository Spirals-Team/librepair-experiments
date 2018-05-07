# language: es
Característica: Ser capaz de iniciar sesion como Analista del cuadro de mandos 

	Como analista registrado en el sistema quiero poder acceder a las graficas.

	Escenario: Login correcto
		Dado un analista de nombre "Id5" y contraseña "123456" registrado en el sistema
		Y situado en la página "/login"
		Cuando hago login con usuario "Id5" y password "123456"  introduciendo los datos en los campos
		Entonces soy redireccionado a la página "/users/analisis"
		
	Escenario: Login incorrecto
		Dado un analista de nombre "Id5" contraseña "12345"  no registrado en el sistema
		Y situado en la página "/login"
		Cuando hago login con usuario "Id5" y password "12345"  introduciendo los datos en los campos
		Entonces soy redireccionado a la página "/login?error"