# language: es
Característica: Ser capaz de iniciar sesion como Administrador del cuadro de mandos 

	Como administrador registrado en el sistema quiero poder acceder al cuadro de mandos.

	Escenario: Login correcto
		Dado un administrador de nombre "Id4" y contraseña "123456" registrado en el sistema
		Y situado en la página "/login"
		Cuando hago login con usuario "Id4" y password "123456"  introduciendo los datos en los campos
		Entonces soy redireccionado a la página "/users/admin"
		
	Escenario: Login incorrecto
		Dado un operario de nombre "Id4" contraseña "12345"  no registrado en el sistema
		Y situado en la página "/login"
		Cuando hago login con usuario "Id4" y password "12345"  introduciendo los datos en los campos
		Entonces soy redireccionado a la página "/login?error"