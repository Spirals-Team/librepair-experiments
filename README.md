# InciDashboard_e3a
InciDashboard_e3a

[![Build Status](https://travis-ci.org/Arquisoft/InciDashboard_e3a.svg?branch=master)](https://travis-ci.org/Arquisoft/InciDashboard_e3a)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e89ecf2799b8400580f767eb000d0380)](https://www.codacy.com/app/jelabra/InciDashboard_e3a?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/InciDashboard_e3a&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/Arquisoft/InciDashboard_e3a/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/InciDashboard_e3a)


## Índice

- [Introducción al proyecto](#incidashboard_e3a)
- [Aplicación Web](#aplicación-web)
- [Cómo ejecutar el proyecto](#cómo-ejecutar-el-proyecto)
    - [Detalles](#detalles)	     
    - [Instrucciones](#instrucciones)	  
 - [Autores](#autores)	
 
 
 
 ## Aplicación Web
 
Este módulo contiene una aplicación web con tres partes o roles: Administrador, Analista y Operario.
Cada uno de los usuarios que acceden a dicha aplicación tiene un perfil/rol, y dependiendo de éste, tanto lo que podrá visualializar, como las funcionalidades que podrá usar, cambiarán.
 
Si el usuario que accede a la aplicación tiene el rol de administrador:

- Visualizará un cuadro de mando en el que podrá seleccionar cualquier propiedad y elegir los humbrales "normales" para dicha propiedad. Así como inciacar cuando es crítica la incidencia (cuando está por encima del valor máximo o cuando está por debajo del mínimo).
	
- También verá una tabla con todos los usuarios registrados (sin incluirse a sí mismo).


En el caso de que el usuario tenga rol analista tendrá en una sola vista:

- Gráfica de barras con todas las incidencias registradas en los últimos 10 días.

- Gráfica de sectores con el estado de las incidencas y el número de éstas que hay por cada estado. 

- Gráficas de puntos que monitorizan la llegada de incidencias por kafka.

- Mapa con todas las incidencias localizadas en él.

- Algunas fotos de incidencias recientes.

- Lista con las últimas incidencias que llegan por kafka.

Y por último, si el usuario es un operario visualizará:

- Una tabla con todas sus incidencias, con la posibilidad de cambiar el estado de cada una de ellas.

- Notificaciones con las nuevas incidencias que llegan por kafka.


 
 ## Cómo ejecutar el proyecto

### Detalles

Funciona con una base de datos MongoDB.

Por tanto, para probar hay que tener funcionando:

1. Desplegar [Kafka](https://kafka.apache.org/quickstart). Para lo que hay que arrancar primero Apache Zookeeper y después Apache Kafka, de esta forma ejecutaremos desde el directorio apache-kafka primero:
		
		* Linux/Mac: bin/zookeeper-server-start.sh config/zookeeper.properties
		* Windows: bin\windows\zookeeper-server-start.bat config\zookeeper.properties

	y después:

		* Linux/Mac: bin/kafka-server-start.sh config/server.properties
   		* Windows: bin\windows\kafka-server-start.bat config\server.properties

2. Lanzar este módulo.

### Instrucciones



# Autores

- Álvaro Cabrero Barros (@espiguinho)
- Saúl Castillo Valdés (@saulcasti)
- Pelayo Díaz Soto (@PelayoDiaz)
- Amelia Fernández Braña (@ameliafb)
- Francisco Javier Riedemann Wistuba (@FJss23)

..................................................



