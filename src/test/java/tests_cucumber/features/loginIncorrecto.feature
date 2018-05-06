# language: es

Característica: Inicio de sesión

Escenario: Inicio de sesion fallido
	Dada esta lista de usuarios:
		| nombre | localizacion | email | identificador | password | tipo |
		| Admin | | admin@gmail.com | admin | 1234 | Operador |
		| Susana | | susana@gmail.com | susana | 1234 | Operador |
	Cuando el agente va a hacer login
	Entonces introduce identificador "inventado@gmail.com" y password "12345"
	Entonces No puede entrar al sistema y se le muestra un mensaje de error