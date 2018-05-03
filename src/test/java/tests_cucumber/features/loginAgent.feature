# language: es

Característica: Inicio de sesión

Escenario: Inicio de sesion con un agente
	Dada la siguiente lista de usuarios:
		| nombre | localizacion | email | identificador | password | tipo |
		| Admin | | admin@gmail.com | admin | 1234 | Operador |
		| Susana | | susana@gmail.com | susana | 1234 | Operador |
	Cuando el agente se encuentra en la pagina de login
	Cuando introduzco el identificador "susana" y la password "1234"
	Entonces puedo entrar al sistema y se muestra la vista Home