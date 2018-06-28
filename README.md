# InciManager_uo251893

[![Build Status](https://travis-ci.org/wason12/InciManager_uo251893.svg?branch=master)](https://travis-ci.org/wason12/InciManager_uo251893)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8d9f352d628646289877196926352d23)](https://www.codacy.com/app/wason12/InciManager_uo251893?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=wason12/InciManager_uo251893&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/wason12/InciManager_uo251893/branch/master/graph/badge.svg)](https://codecov.io/gh/wason12/InciManager_uo251893)
[![Codefresh build status]( https://g.codefresh.io/api/badges/build?repoOwner=wason12&repoName=InciManager_uo251893&branch=master&pipelineName=InciManager_uo251893&accountName=wason12&type=cf-1)]( https://g.codefresh.io/repositories/wason12/InciManager_uo251893/builds?filter=trigger:build;branch:master;service:5b2fbdc4fe1828191a92871e~InciManager_uo251893)

Módulo InciManager del sistema Gestión de incidencias. Este módulo contiene la implementación del módulo InciManager. Para más información mirar la wiki.

# Autor

- Alejandro Barrera Sánchez (UO251893)

# Despliegue

Es necesario maven, kafka y java.

- Maven: https://maven.apache.org/
- Kafka: https://kafka.apache.org/
- Java : http://www.oracle.com

Pasos a seguir:

1. Descargamos maven, kafka y la JVM.
2. Seguir las instrucciones para ejecutar kafka: https://kafka.apache.org/quickstart
3. Clonar este repositorio
4. En la carpeta donde lo hemos clonado ejecutamos el comando mvn spring-boot:run

Nota: Es necesario también una BBDD postgreSQL: https://www.postgresql.org/

# Desplegado en:

Temporalmente está desplegado en: http://wason12.ddns.net:81/ a la espera de que me vuelva a funcional AWS.

# Usuarios de prueba para la aplicación:

- Identificador: prueba1 ; Contraseña: 1234 ; tipo: Person
- Identificador: prueba2 ; Contraseña: 1234 ; tipo: Person
- Identificador: prueba3 ; Contraseña: 1234 ; tipo: Person
- Identificador: prueba4 ; Contraseña: 1234 ; tipo: Person
