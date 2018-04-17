# Historia (10hs)

### Descripción

Dado un registro historico de una MAC Address con sus ubicaciones en el tiempo y un área de exclusion determinada por una figura* informar si dicha MAC estuvo o no en esa área en algún momento.

### Criterios de Aceptación

Aclaración: todos los nombres de las MAC Addresses son ficticios. Encontrará sus respectivas referencias en el documento "Capturas de Prueba.txt"

### Input

1) [addra, historico_MAC, coordenadas_Area]

### Output
true o false

### Tareas


| Número | Descripción | Horas |
| ------ | ------ | :------: |
| 0 | Convertir por cada una de las Coordenadas del historico de LAT LONG a coordenadas X, Y. | 6 |
| 1 | Convertir las coordenadas del area de exclusion a coordenadas X, Y    | 0 |
| 2 | Utilizar un [algoritmo](https://en.wikipedia.org/wiki/Collision_detection) que determine si una coordenada está dentro de la figura que forman las coordenadas del area de exclusion es decir si colisiona con estas | 4 |

\* La figura puede estar determinada por un circulo de la forma (Coordenada_Centro, Coordenada_exterior) o un rectangulo (Coordenada A,Coordenada B, Coordenada C, Coordenada D).
