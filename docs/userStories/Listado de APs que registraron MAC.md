# Historia (4 hs)

### Descripción

Dada una MAC address, devolver un listado de todos los AP que identificaron esa MAC Address.

### Criterios de Aceptación

Aclaración: todos los nombres de las MAC Addresses son ficticios. Encontrará sus respectivas referencias en el documento "Capturas de Prueba.txt"

#### Input

1) [addra](https://github.com/pp2-2018/wirelessanalyzer/blob/development/docs/userStories/Capturas%20de%20Prueba.md#mac-addresses-de-los-paquetes)

2) [addre](https://github.com/pp2-2018/wirelessanalyzer/blob/development/docs/userStories/Capturas%20de%20Prueba.md#mac-addresses-de-los-paquetes)

3) f1:a4:d7:74:4c:09

#### Output

1) [A, B, C, E]

2) [B, E]

3) [] (Lista vacía)

### Tareas

| Número | Descripción | Horas |
| ------ | ------ | :------: |
| 0 | Identificar las capturas* que contienen la MAC Address especificada | 2 |
| 1 | Listar los AP diferentes que capturaron la MAC Address | 2 |

*Se utliza el termino capturas para identificar la relación entre Access Point y los paquetes capturados por este último.