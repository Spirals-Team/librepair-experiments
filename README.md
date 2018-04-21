
[![Build Status](https://travis-ci.org/Arquisoft/InciDashboard_e5a.svg?branch=master)](https://travis-ci.org/Arquisoft/InciDashboard_e5a)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/81ed23f28056410c9d542489fba9b901)](https://www.codacy.com/app/jelabra/InciDashboard_e5a?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/InciDashboard_e5a&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/Arquisoft/InciDashboard_e5a/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/InciDashboard_e5a)

# InciDashboard_e5a
InciDashboard e5a

# Autores

Tania Álvarez Díaz (UO244856)

Alejandro Barrera Sánchez (UO251893)

Ismael Cadenas Alonso (UO251025)

Daniel Bermejo Blanco (UO204115)

# Para ejecutar

Desde la carpeta del proyecto:
 	 
Lanzar zookeeper

En Windows: 
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

En Unix:
bin/zookeeper-server-start.sh config/zookeeper.properties

Lanzar kafka

En Windows: 
bin\windows\kafka-server-start.bat config\server.properties

En Unix: 
bin/kafka-server-start.sh config/server.properties

Desde el interior del proyecto en la carpeta donde se encuentre el pom.xml

mvn spring-boot:run

Lanzar Base de Datos de prueba:
Dentro de la carpeta del proyecto: (dirección: \InciDashboard-e5a\hsqldb-2.4.0\hsqldb\bin)
Ejecutar runServer.bat

Para lanzar incidencias:
En windows:
bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic incidencia
> {"id":31, "descripcion":"incidencia 2", "localizacion":"Oviedo"}
