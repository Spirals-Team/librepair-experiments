# language: es

Característica: Inicio de sesión

Escenario: Inicio de sesion con el administrador
	Dada la lista de usuarios:
		| id | nombre | localizacion | email | identificador | password | tipo |
		| | Admin | | admin@gmail.com | admin | 1234 | Operador |
		| | Susana | | susana@gmail.com | susana | 1234 | Operador |
	Cuando el administrador se encuentra en la pagina de login
	Cuando inserta su identificador "admin" y su password "1234"
	Entonces se loguea de manera correcta