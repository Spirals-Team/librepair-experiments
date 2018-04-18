# Historia 04 (3hs)

### Descripción

El sistema proverá de un diccionario de {AP MAC Address, numero de apariciones} de todos los primeros N Access Points ordenados por más concurrencias de Mac Addresses (Cantidad de paquetes recibidos, en terminos prácticos). Las concurrencias de las MAC Addresses serán contadas siempre que un paquete Probe Request o Beacon sea detectado. Si dos APs contienen el mismo número de apariciones, el sistema no garantiza ningún orden de presentación en la lista.

### Criterios de Aceptación

Aclaración: todos los nombres de las MAC Addresses son ficticios. Encontrará sus respectivas referencias en el documento "Capturas de Prueba.txt"

#### Input 

1) 3

2) 100

3) 0

#### Output

1) [{B, 5}, {E, 4}, {A, 2}]

2) [{B, 5}, {E, 4}, {A, 2}, {C, 2}, {D,2}]

3) []

### Tareas

| Número | Descripción | Horas | 
| ------ | ------ | :------: |
| 0 | Contar por cada aparición de MAC Address un punto de concurrencia por ese Access Point | 2 |
| 1 | Ordenar las listas de los AP según los más concurridos | 1 |

