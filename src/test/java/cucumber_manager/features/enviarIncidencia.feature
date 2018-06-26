#language: es

Característica: Envío de una incidencia por parte de un agente
	
	Escenario: Iniciamos sesión con un usuario y enviamos una incidencia a través de Kafka

		Dado el email del agente que va a enviar la incidencia 'miguel@uniovi.es'
			Y su password 'Contra'
			Y su tipo 'Persona'
			
		Dado la incidencia recogida por un agente loggeado
			Y con nombre 'Averia1'
			Y descripcion 'Perdida de agua de la labadora'
			Y etiquetas 'averia, agua'

		Cuando tratamos de enviar esa incidencia a través de Kafka

		Entonces se envia la incidencia correctamente 