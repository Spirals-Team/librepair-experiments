# Historia (6hs)

### Descripción

Dada una MAC address, devolver un listado de todos los AP que identificaron esa MAC Address en un tiempo específico provisto.

### Criterios de Aceptación

Aclaración: todos los nombres de las MAC Addresses son ficticios. Encontrará sus respectivas referencias en el documento "Capturas de Prueba.txt"

### Input

1) Intervalo de tiempo: Desde 2018-04-04T20:00:00.000 hasta 2018-04-04T23:00:00.00 
MAC Address: [addre](https://github.com/pp2-2018/wirelessanalyzer/blob/development/docs/userStories/Capturas%20de%20Prueba.md#mac-addresses-de-los-paquetes) 
	
2) Intervalo de tiempo: Desde 2018-04-02T19:27.500 hasta 2018-04-02T23:59:59.999 
MAC Address: [addra](https://github.com/pp2-2018/wirelessanalyzer/blob/development/docs/userStories/Capturas%20de%20Prueba.md#mac-addresses-de-los-paquetes)

3) Intervalo de tiempo Desde 2018-04-03T15:12:12.123 hasta 2017-03-11T12:52:25.12
MAC Address: f0:12:85:5b:4c:9c  

### Output

1) [B, E] 

2) [C]

3) [] (Lista vacía)

### Tareas

| Número | Descripción | Horas | 
| ------ | ------ | :------: |
| 0 | Parsear el intervalo de tiempo para obtener un objeto Intervalo | 2 |
| 1 | Filtrar las capturas relizadas en el período de tiempo indicado | 2 |
| 2 | Buscar en cuáles capturas la MAC Address especificada aparece | 2 |

