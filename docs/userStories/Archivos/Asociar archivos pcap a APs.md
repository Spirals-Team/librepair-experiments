# Historia 1 (6hs)

### Descripción

Cuando se lea un archivo pcap externo, el sistema deberá asociar esas capturas a un Access Point. (Cabe destacar que, por convención, el nombre del archvio pcap será la MAC Address del AP que reunió todos los paquetes, los 12 dígitos hexadecimales juntos y sin caracteres especiales en el medio)

### Criterios de Aceptación

#### Input

1) Filename: "1-4a-88-7f-41-12-47.pcap" (Acess Point cargado en el programa)

2) Filename: "2-4a-88-7f-41-12-47.pcap" (Access Point cargado en el programa)

3) Filename: "1-c0-12-12-4a-5b-e4.txt"

4) Filename: "NotMatching.pcap"

#### Output

1) El sistema extraerá la MAC Address 4a:88:7f:41:12:47 y asociará los paquetes obtenidos del archivo al correspondiente AP.

2) El sistema extraerá la MAC Address 4a:88:7f:41:12:47 y asociará los paquetes obtenidos del archivo al correspondiente AP.

3) El sistema lanzará una excepción de formato inválido.

4) El sistema lanzará una excepción de MAC Address no encontrada en el nombre de archivo.

### Tareas

| Número | Descripción | Horas | 
| ------ | ------ | :------: |
| 0 | Extraer la cadena que compone el nombre del archivo | 1 |
| 1 | Validar el nombre del archivo y el formato | 2 |
| 2 | Extraer la MAC Address | 2 |
| 3 | Asociar la MAC Address obtenida con alguna de las MAC Addresses de los APs ya cargados en el sistema | 1 | 
