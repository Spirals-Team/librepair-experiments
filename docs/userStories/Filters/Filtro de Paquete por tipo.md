# Historia (15hs)

### Descripción

El sistema podrá identificar si un paquete escrito en hexadecimal es de algún tipo específico de paquete. Para esto, se deberá indicar el offset y el dígito hexadecimal que indique el tipo de paquete. 

### Criterios de Aceptación

#### Input 

1) Beacon Frame 1 recuperado de archivo "Paquetes de prueba.txt"
Offset: 0x0090
Dígito: 0x0008

2) Probe Request Frame 1 recuperado de archivo "Paquetes de prueba.txt"
Offset: 0x0090
Dígito: 0x0004

3) Paquete null

4) Http Packet 1 recuperado de archivo "Paquetes de prueba.txt"

#### Output

1) El paquete es de tipo Beacon

2) El paquete es de tipo Probe Request

3) El paquete no es de algunos de los tipos específicados

4) El paquete no es de algunos de los tipos específicados

### Tareas

| Número | Descripción | Horas |
| ------ | ------ | :------: |
| 0 | Configurar el filtro según las configuraciones provistas| 2 |
| 1 | Implementar patron *evaluator* para leer las configuraciones| 6 |
| 2 | Leer el byte de correspondiente al offset y decidir que tipo de paquete es| 4 |
| 3 | Comparar el byte obtenido con el dígito indicado y crear la clase *Paquete* correspondiente| 3 |

