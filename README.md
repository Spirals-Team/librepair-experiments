# Dashboard_e2a

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5a963e9cc71c4f0c951250172abd6d15)](https://www.codacy.com/app/PablooD9/InciDashboard_e2a?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/InciDashboard_e2a&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/Arquisoft/InciDashboard_e2a.svg?branch=master)](https://travis-ci.org/Arquisoft/InciDashboard_e2a)
[![codecov](https://codecov.io/gh/Arquisoft/InciDashboard_e2a/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/InciDashboard_e2a)

Dashboard - e2a

# Autores

* César Camblor García (@UO251281)

* Pablo Díaz Rancaño (@UO251017)

* Fernando De la Torre Cueva (@UO245182)

* Pablo Álvarez Álvarez (@UO251561)

## Introducción al repositorio

Este repositorio pertenece a la parte *Dashboard* del grupo de trabajo **E2a**,
encargada de mostrar, en un panel de control, las incidencias enviadas por agentes y asignadas a los operarios.

## Cómo ejecutar el proyecto

### Base de datos HSQLDB (versión 2.4.0)
La base de datos a utilizar es HSQLDB (**Se puede descargar [aquí](https://sourceforge.net/projects/hsqldb/files/latest/download?source=files)**). A continuación, simplemente se ejecuta el fichero bin/startup.bat.

### Apache Zookeeper y Apache Kafka
Para poder ejecutar Apache Zookeeper y Apache Kafka, puedes descargar la carpeta desde [aquí](https://unioviedo-my.sharepoint.com/:u:/g/personal/uo251017_uniovi_es/EQPNYDwHknpCtZI1U1wK7QUBIEoZVywWTvmwFfO3upoA-A?e=kh1lYE).

Una vez descargado el zip del paso anterior, abre la línea de comandos en la carpeta raíz de la carpeta *kafka_2.11-0.10.2.0*.

Para Windows, ejecuta estos dos comandos en orden (el primero abre Zookeeper. El segundo, Apache Kafka):
```bash
C:\...\kafka_2.11-0.10.2.0>bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```
```bash
C:\...\kafka_2.11-0.10.2.0>bin\windows\kafka-server-start.bat config\server.properties
```

Para Linux:
```bash
tu_prompt#kafka_2.11-0.10.2.0>bin/zookeeper-server-start.sh config/zookeeper.properties
```
```bash
tu_prompt#kafka_2.11-0.10.2.0>bin/kafka-server-start.sh config/server.properties
```

A continuación, sitúate en la carpeta raíz del proyecto del repositorio, y ejecuta el siguiente comando:
```bash
C:\...\proyecto_dashboard>mvn spring-boot:run
```

### Ya tengo todo listo. ¿Cómo puedo enviar una incidencia?
Abre una nueva terminal desde la carpeta *kafka_2.11-0.10.2.0*, y ejecuta el siguiente comando:

**NOTAS ANTES DE EJECUTAR** 
* La latitud y la longitud corresponden a la localización de la incidencia, separados por el símbolo del dollar ($).
* Las etiquetas se separan por el símbolo del dollar ($). Ejemplo -> Fuego$Alud$Terremoto ...
* La lista de campos clave valor funciona de la siguiente manera: Fuego:Extremo$Altitud:Normal$Temperatura:Normal ...
* Para convertir la fecha de la incidencia a milisegundos, [pulse aquí](https://espanol.epochconverter.com/)

WINDOWS
```bash
C:\...\kafka_2.11-0.10.2.0>bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic newIncidence
Nombre agente@Nombre incidencia@Descripcion de la incidencia@Latitud$Longitud@Etiquetas@Lista de campos clave valor@Fecha en milisegundos
```

LINUX
```bash
tu_prompt#kafka_2.11-0.10.2.0>bin/kafka-console-producer.sh --broker-list localhost:9092 --topic newIncidence
Nombre agente@Nombre incidencia@Descripcion de la incidencia@Latitud$Longitud@Etiquetas@Lista de campos clave valor@Fecha en milisegundos
```

Un ejemplo para copiar y pegar:
```bash
C:\...\kafka_2.11-0.10.2.0>bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic newIncidence
Juan@Fuego en Oviedo@El parque San Francisco esta quemandose a causa de un cigarrillo mal apagado@43.3616142$-5.8506767@Fuego$Parque@Temperatura:Alta$Fuego:Extremo@1521893518784
```

### Como ejecutar las pruebas con Selenium:
Primero descargaremos el Firefox46 portable que nos ejecutará las pruebas. [Enlace aquí](https://unioviedo-my.sharepoint.com/:u:/g/personal/uo251561_uniovi_es/EUm3V6zrxuFFuvwX1aAxZzMBEzYnI7TKGxvIpe1zZLq7mw?e=0q0Piz)

Despues en la clase DashBoardTests.java, llegaremos a ella siguiendo la siguiente jerarquía  /dashboard0/src/test/java/asw/selenium/DashBoardTests.java hay que modificar la variable PathFirefox y sustituirla
por el path donde se haya descargado el Firefox. 

Ya en la propia clase podremos ejecutar las pruebas.
