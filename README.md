# Loader e3b

[![Build Status](https://travis-ci.org/Arquisoft/Loader_e3b.svg?branch=master)](https://travis-ci.org/Arquisoft/Loader_e3b)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6fad6fe134c1434cb0b9384d851821c8)](https://www.codacy.com/app/jelabra/Loader_e3b?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/Loader_e3b&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/Arquisoft/Loader_e3b/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/Loader_e3b)

Loader e3b

# Authors 2017

+ Daniel Alba Muñiz (UO245188)
+ José Luis Bugallo González (UO244702)
+ Ignacio Escribano Burgos (UO227766)
+ Daniel Duque Barrientos (UO245553)
+ Rubén de la Varga Cabero (UO246977)

# Authors 2018
+ Diego Álvarez Guinarte (UO251682)
+ Manuel Junco Diez (UO252010)
+ Ivan Suarez Castiñeiras (UO244730)
+ Pablo Gonzalez Martinez (UO245699)

### Datos usuarios de prueba

|Nombre                   | Localización           | Email                    | Identificador | Tipo   |
|-------------------------|------------------------|--------------------------|---------------|--------|
|Juan Torres Pardo        |                        | juan@example.com         | 90500084Y     | Person |
|Luis López Fernando      |                        | luis@example.com         | 191160962F    | Person |
|Ana Torres Pardo         |                        | ana@example.com          | 09940449X     | Person |
|Sensor_123 2018          | 43.361368, -5.853591   | admin@sensores.com       | sensor_123   | Sensor |
|Ministerio medioambiente | 43.359486, -5.846986   | ambiente@ministerio.com  | medioambiente | Entity |
|Space X sensor model A   | 33.921209, -118.327940 | musk@spacex.com          | spacex        | Sensor |

# Instrucciones para ejecutar
+ Importar repositorio: 

```git clone https://github.com/Arquisoft/Loader_e3b.git```
+ Navegar a la carpeta del projecto
+ Compilar el projecto : 

```mvn clean install -U```
+ Ejecutar: 

```mvn exec:java -Dexec.args="load src/test/resources/agents.xlsx"```

# Instrucciones para ejecutar desde Eclipse
+ Importar repositorio a Eclipse desde Github
+ Botón derecho - Configure - Convert to Maven project y esperar a que se descarguen las dependencias
+ Ejecutar LoadUsers (en el paquete main) con los argumentos load src/test/resources/test.xlsx. 
 
 Para especificar argumentos en Eclipse, acceder al menú Run Configurations - LoadUsers - Arguments
 
 Adjunto imagen
 
 ![alt text](https://i.imgur.com/4i1CFRR.png)
 
 
