# Semillero API [![Build Status](https://travis-ci.org/DanielJulian/NahualSpringBoot.svg?branch=master)](https://travis-ci.org/DanielJulian/NahualSpringBoot.svg?branch=master)

## Introducción

API que usamos en Nahual para hacer un seguimiento de los estudiantes que pasan por el taller y el estado en qué se encuentra su búsqueda laboral.

Está desarrollada en Java, más específicamente con Spring Boot.

## Instalación DEV

### Base de datos

La instalación de los containers nos instala una base MariaDB. 
De hecho, hay un docker-compose.yml solo para este proyecto, por si no querés levantar los otros containers y solo te levante la base de datos.

Dentro de la carpeta __sql-migrations__ se encuentran todos los archivos que debemos ejecutar para la creación del esquema.
(Recordatorio: el puerto no es el default, es el __3307__)

### Cómo desarrollamos? 

Mientras estamos desarrollando podemos usar la base dockerizada pero correr la aplicación desde nuestro IDE preferido (lo único que deberíamos cambiar es el datasource y apuntarlo a nuestro local dockerizado, en vez de al host dockerizado)

## Documentación API
    
La documentación de la API está escrita en RAML 0.8. 

El HTML generado se puede ver en el siguiente [link](https://rawgit.com/nahual/semillero2-api/development/apiV2.html)


