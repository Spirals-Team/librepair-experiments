# Historia (14hs)

### Descripción

Dada una MAC address y un intervalo de tiempo, devolver un Registro Historico de geolocalizaciones apróximadas en donde estuvo esa MAC Address en un momento determinado

### Criterios de Aceptación

Aclaración: todos los nombres de las MAC Addresses son ficticios. Encontrará sus respectivas referencias en el documento "Capturas de Prueba.txt"

### Input

1) [addra]

2) [addra]

3) [addrc]

4) [addrd]

### Output

### Tareas

| Número | Descripción | Horas |
| ------ | ------ | :------: |
| 0 | Listar los Access Points que registraron dicha MAC Address durante el periodo de tiempo establecido | 1 |
| 1 | Crear por cada unidad de tiempo minima* una entrada en el registro historico donde cada entrada tendra, el momento en el tiempo  y una lista de los Access Points que detectaron a la MAC Address en ese determinado momento. | 4 |
| 2 | Por cada entrada del registro historico utilizar un algoritmo de triangulacion que permita dar una localizacion aproximada de la MAC Address utilizando las areas de cobertura de los Access Points listados en la entrada calculando asi la figura que forma su interseccion y expresandola como una lista de puntos en forma de coordenadas geofraficas| 9 |


\* Se deberá definir una constante que indique al programa cuanto tiempo puede pasar entre recepcion de paquete de los Access Point para considerar que una MAC Address fue identificada al mismo tiempo. Esta aclaración tiene sentido al asumir que no todos los paquetes llegan en el mismo instante a Access Points distintos (cada 10 segundos x ej).
