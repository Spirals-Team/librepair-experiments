# Grupo-H-012018-desapp

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c75deea85ba742219a4a61328ce58b15)](https://app.codacy.com/app/marivgil/Grupo-H-012018-desapp?utm_source=github.com&utm_medium=referral&utm_content=RedondaAnalia/Grupo-H-012018-desapp&utm_campaign=badger)

Repositorio Desarrollo de Aplicaciones, semestre 1, 2018. 


Carpnd
(Pick and Drive)

En la actualidad se ha popularizado la idea de que aquellas personas que tienen algún bien (mueble o inmueble) pueden aprovecharlo al máximo, por ejemplo, alquilandolo en momentos en que ellos no hacen uso del mismo. En sintonía con esta tendencia, buscamos construir una aplicación que permita a quien posee un vehículo y no hace uso del mismo en todo momento, pueda obtener un rédito económico mientras no lo usa.

Carpnd quiere acercar a quienes buscan un vehículo para hacer uso del mismo por un tiempo definido, con quienes disponen de ese vehículo y no lo van a utilizar en el mismo lapso de tiempo.

Como es habitual en este tipo de aplicaciones, tanto quienes publiquen su auto para alquiler como aquellos que realicen los alquileres gozarán de una reputación que se construye a partir de la puntuación otorgada por sus contrapartes.

Los vehículos estarán publicados con una descripción para conocer sus prestaciones, fotografías del mismo, zona geográfica en la cual debe ser retirado y devuelto, tiempo por el cual está disponible para su alquiler y el costo del mismo.

Los usuarios del sistema deberán dejar registrado:

CUIL   [Obligatorio,CUIL Valido]

Nombre y Apellido [Obligatorio,4<=X<=50]

Dirección  [Obligatorio]

Email [Obligatorio, Email valido]

Los dueños de un vehículo podrán publicar el mismo ingresando los siguientes datos:

Tipo de vehículo (Motocicleta, Auto, Camioneta) [Obligatorio]

Capacidad de pasajeros [Obligatorio]

Localidad   [Obligatorio]

Dirección de retiro y Ubicación en un mapa (Google Maps preferentemente) [Obligatorio]

Dirección de regreso y Ubicación en un mapa (En este caso puede ser más de una alternativa) [Obligatorio]

Descripción del vehículo [Obligatorio,30<=X<=200]]

Teléfono de contacto [Obligatorio, Teléfono válido, +Característica]

Horario y días disponible para alquilar [Obligatorio]

Costo del alquiler por hora/día [Obligatorio]

Fotografías del vehículo

Con el objetivo de simplificar la gestión de cobros y pagos, cada usuario contará con una cuenta corriente de créditos. Cada alquiler implica descontar créditos a quien utilizará el vehículo y acreditar dichos créditos a quien presta el vehículo.
Los clientes podrán cargar credito a sus cuentas. En el marco de este alcance solo se dispondrá una entrada de credito por sistema . El proveedor tendrá la misma funcionalidad para “retirar” dinero.

Los vehículos pueden ofrecerse en     alquiler por el lapso mínimo de una hora y como máximo 5 días.

Quien desee alquilar un vehículo podrá realizar bús    quedas por categoría o por localidad. Podrá ordenar los resultados por precio (min/max) y por puntuación (min/max). Se deberá proveer una vista en lista y otra ubicada en un mapa. La lista deberá mostrar descripción, precio, reputación del dueño del vehículo, nombre del dueño del vehículo. Se deberá paginar cada 20 resultados.

Quien desee alquilar un vehículo deberá realizar la reserva del mismo, indicando el tiempo por el cual hará uso del mismo y quedará a la espera de la confirmación de la reserva por parte del propietario del vehículo.

Si la reserva se confirma, quien realizó el alquiler retirará el vehículo en el lugar indicado y lo devolverá también en el lugar de retorno pactado. Tanto al momento de retirar el vehículo como al momento de retornarlo, ambos participantes de la transacción deben llevar a cabo acciones específicas sobre la aplicación.
Retiro del vehículo: 
Quien retira el vehículo indicará en la aplicación que ya tiene el bien en su poder. 
Quien colocó en alquiler el vehículo debe confirmar esta notificación.
El tiempo de alquiler corre a partir de que ambos usuarios cumplieron con su acción. 
En caso que el propietario indique que el bien fue retirado y no exista respuesta de la contraparte dentro de los 30 minutos, la misma se da por confirmada.
En caso que quien alquila el vehículo indique que ya lo tiene en su poder y no exista respuesta de su contraparte dentro de los 30 minutos, la misma se da por rechazada.
Devolución del vehículo:
Quien devuelve el vehículo indica que ya no posee el mismo y puntúa al propietario. 
Quien recibe el vehículo notifica su recepción y puntúa a su contraparte.
El tiempo de alquiler se fija a partir de que ambos usuarios cumplieron con su acción. 
Ambos usuarios pueden ingresar comentarios al momento de puntuar a su contraparte.

Todos aquellos usuarios que posean una puntuación promedio por debajo de 4 (en una escala de 1 a 5) deben ser baneados de la aplicación.

Toda acción que un usuario realice sobre la aplicación, vinculada a una reserva, debe ser notificada por mail (ej:https://www.mailgun.com/) a su respectiva contraparte (reserva del vehículo, toma de posesión del vehículo, devolución del vehículo)

El último día de cada mes, se enviará a los usuarios un estado de cuenta con los movimientos que hayan registrado sobre la aplicación. 

Para facilitar la carga, el sistema deberá validar el CUIT de los usuarios (en el alta) en https://soa.afip.gob.ar/sr-padron/v2/persona/XXXXX y completar los datos nombre/apellido/dirección con la información suministrada para que el usuarios confirme o edite.

La aplicación deberá ser completamente responsive ya que si bien ahora no vamos a contar con una aplicación mobile, será importante que pueda visualizarse correctamente en celulares y tablets. 

Es requisito indispensable proveer la capacidad de acceder a la aplicación utilizando una cuenta existente en alguna de las herramientas o redes sociales más utilizadas (gmail, facebook, twitter, etc). 
