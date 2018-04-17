# Historia

### Descripción

El sistema podrá identificar si un paquete escrito en hexadecimal fue escrito en un determinado intervalo de tiempo provisto. (11hs)

### Criterios de Aceptación

#### Input 

1) Paquete 1 - Beacon Frame con Timestamp, intervalo: [30 Dic 1999 - 1 Ene 2000]

2) Paquete 2 - Probe Request con Timestamp, intervalo: [20 Dic 1999 - 25 Dic 1999]

3) Paquete null

#### Output
1. El sistema detecta que el paquete fue detectado en el intervalo dado.

2. El sistema determina que el paquete **no** fue detectado en el intervalo dado.



### Tareas

| Número | Descripción | Horas | 
| ------ | ------ | :------: |
| 0 | Crear el objeto Intervalo con los datos suministrados | 3 |
| 1 | Identificar el [Global Header](https://wiki.wireshark.org/Development/LibpcapFileFormat#Global_Header) del paquete | 2 |
| 2 | Verificar si timestamp del paquete está contenido en el Intervalo | 2 |
| 3 | Parsear el Global Header y extraer el timestamp de cuando el paquete fue recibido | 4 |

