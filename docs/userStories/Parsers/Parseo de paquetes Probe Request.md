# Historia 05

### Descripción

El sistema podrá parsear informacion “raw” en paquetes de tipo Probe Request. (8hs)

### Criterios de Aceptación

#### Input 

1) Paquete 2 - Probe Request Frame extraído del archivo "Paquetes de prueba.txt"

2) Null

#### Output

1) Tipo de Paquete = Probe Request
   MAC Address source = 9c b7 0d 7e 6c 12
   Timestamp = Dec, 31 1999 21:03:59.4119990000 -03

2) Lanza excepción

### Tareas

| Número | Descripción | Horas | 
| ------ | ------ | :------: |
| 0 | Identificar las posiciones de los bloques de información de la cadena hexadecimal | 4 |
| 1 | Extraer MacAddress de destino y timestamp  del paquete | 2 |
| 2 | Convertir a información obtenida de Hexa a UTF-8 | 1 |
| 3 | Crear un objeto en memoria que contenga la información recuperada | 1 | 
 
