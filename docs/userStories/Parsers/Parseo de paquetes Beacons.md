# Historia 05

### Descripción (17hs)

El sistema podrá parsear informacion “raw” en paquetes de tipo Beacon. 

### Criterios de Aceptación

#### Input

1) Paquete 1 - Beacon Frame de archivo "Prueba de paquete.txt" 

2) Null

#### Output

1) Tipo de Paquete = Beacon
   MAC Address source = 00 23 9c 3b d0 20
   Timestamp = Dec, 31 1999 21:03:53.18999900 -03

2) Lanza excepción el programa

### Tareas

| Número | Descripción | Horas |
| ------ | ------ | :------: |
| 0 | Identificar las posiciones de los bloques de información de la cadena hexadecimal | 8 |
| 1 | Extraer MacAddress de destino y timestamp del paquete | 4 |
| 2 | Formatear a información obtenida de Hexa a UTF-8 | 2 |
| 3 | Extraer el timestamp del paquete ubicado en el global header | 2 |
| 3 | Crear un objeto en memoria que contenga la información recuperada | 1 |
 
