
# Historia (2hs)

### Descripción

Dado un archivo de configuración en formato json que contenga la MAC Address de los Access Point, sus localizaciones y su radio medido en metros, el sistema podrá leer y parsea el archivo con estas configuraciones

### Criterios de Aceptación

#### Input

1) Archivo Json con el siguiente contenido:
{
   "Mac Address":"4a:88:7f:41:12:47",
   "Position":{
      "lat":43.69964,
      "lon":-61.9125
   },
   
   "Radio":5
}

2) Archivo Json con el siguiente contenido:
{
   "Mac Address":"4a:88:7f:41:12:47",
   "Coordinates":{
      "lat":43.69964,
      "lon":-61.9125
   },

   "Radio":5
}

3) Archivo vacío

#### Output

1) Objeto en memoria relacionado a un Access Point con MAC Address 4a:88:7f:41:12:47, ubicado en la posición 43.69964, -61.9125 del mapa y un radio de 5 metros.

2) Excepción. Los campos contenidos en el json no son los esperados por el programa.

3) Excepción. Formato inválido.

### Tareas

| Número | Descripción | Horas | 
| ------ | ------ | :------: |
| 0 | Abrir y leer el archivo provisto | 1 | 
| 1 | Parsear la información del archivo y guardarla en memoria | 1 | 


