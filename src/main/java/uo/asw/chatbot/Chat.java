package uo.asw.chatbot;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uo.asw.dbManagement.model.Incidencia;
import uo.asw.inciManager.util.DateUtil;

public class Chat {

	private List<Mensaje> mensajes = new ArrayList<Mensaje>();

	private boolean creacionEnProceso = false;
	private int pasos=0;
	private Incidencia inci;
	
	public Chat() {
		cargarMensajesInciales();
	}
	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public void addMensaje(Mensaje m) {
		mensajes.add(m);
	}

	private void cargarMensajesInciales() {
		Mensaje m = new Mensaje(new Date(), "Hola, ¿en qué le podemos ayudar?", "bot");
		Mensaje m2 = new Mensaje(new Date(), "Puede usted escoger crear una incidencia o consultar las que ya ha mandando", "bot");

		addMensaje(m);
		addMensaje(m2);
	}

	public void calcularRespuesta(String mensaje) {
		if(creacionEnProceso) {
			creacionIncidencia(mensaje);
		}
		else {
			boolean loHePillado =false;
			
			// Si quiere crear
			String mensajeMayusculas = mensaje.toUpperCase();	
			String creacion = "CREAR CREACIÓN CREACION CREO";	
			String[] palabras = creacion.split("\\s+");
			for (String palabra : palabras) {
				if (mensajeMayusculas.contains(palabra) && !creacionEnProceso) {
					Mensaje m = new Mensaje(new Date(), "Ha seleccionado crear una nueva incidencia", "bot");
					addMensaje(m);
					 m = new Mensaje(new Date(), "Comenzamos el proceso de creación ...", "bot");
						addMensaje(m);
					m = new Mensaje(new Date(), "Indique el nombre de la incidencia", "bot");
					addMensaje(m);
					creacionEnProceso = true;
					loHePillado=true;
					inci = new Incidencia();
				}
			}
			if(!loHePillado) {
				creacion = "LISTA LISTAR LISTADO";
				palabras = creacion.split("\\s+");
				for (String palabra : palabras) {
					if (mensajeMayusculas.contains(palabra)) {
						Mensaje m = new Mensaje(new Date(), "Para listar tus incidencias debes ir al apartado "
								+ "listado de incidencias que se encuentra en la barra superior", "bot");
						addMensaje(m);
						m = new Mensaje(new Date(), "¿Te podemos ayudar en alguna otra cosa?", "bot");
						addMensaje(m);
						loHePillado=true;
					}
				}
			}
			if(!loHePillado) {
				creacion = "HOLA BUENAS";
				palabras = creacion.split("\\s+");
				for (String palabra : palabras) {
					if (mensajeMayusculas.contains(palabra)) {
						loHePillado=true;
					}
				}
			}
			//Este siempre al final
			if(!loHePillado) {
				Mensaje m = new Mensaje(new Date(), "Lo siento, no le he entendido", "bot");
				addMensaje(m);
			}


		}

	}
	private void creacionIncidencia(String mensaje) {
		switch (pasos) {

		case 0:
			paso0(mensaje);
			break;

		case 1:
			paso1(mensaje);
			break;

		case 2:
			paso2(mensaje);
			break;
		case 3:
			paso3(mensaje);
			break;
		case 4:
			paso4(mensaje);
			break;
		}

	}

	private void paso0(String mensaje) {
		inci.setNombreIncidencia(mensaje);
		Mensaje m = new Mensaje(new Date(), "¿Cuál es la descripción de esta incidencia?", "bot");
		addMensaje(m);
		pasos++;
	}
	private void paso1(String mensaje) {
		inci.setDescripcion(mensaje);
		Mensaje m2 = new Mensaje(new Date(), "Perfecto, introduce ahora la fecha de caducidad con el siguiente formato: dd/MM/yyyy", "bot");
		addMensaje(m2);
		m2 = new Mensaje(new Date(), "Siendo 'dd' el día del mes, 'MM' el mes del año y 'yyyy' el año", "bot");
		addMensaje(m2);
		pasos++;
	}
	private void paso2(String mensaje) {
		Date fecha = DateUtil.stringToDate(mensaje);
		if(fecha != null) {
			inci.setFechaEntrada(new Date());
			inci.setFechaCaducidad(fecha);
			Mensaje m3 = new Mensaje(new Date(), "Ya queda poco, escoja ahora una o varias de estas categorias:"
					+ "ACCIDENTE_CARRETERA, " + 
					"FUEGO, " + 
					"INUNDACION, " + 
					"ACCIDENTE_AEREO, " + 
					"METEOROLOGICA, " + 
					"VALOR_NO_ASIGNADO", "bot");
			addMensaje(m3);
			Mensaje m4 = new Mensaje(new Date(), "Escriba sus nombres separado por comas", "bot");
			addMensaje(m4);
			pasos++;
		}else {
			Mensaje m4 = new Mensaje(new Date(), "Ups... parece que ha introducido la fecha con un formato no válido", "bot");
			addMensaje(m4);
			m4 = new Mensaje(new Date(), "Vuelva a introducir la fecha de caducidad que ha escogido para su incidencia", "bot");
			addMensaje(m4);
		}
	}
	private void paso3(String mensaje) {
		String mensajeMayusculas = mensaje.toUpperCase();
		String[] palabras = mensajeMayusculas.split(",");
		String categorias = "ACCIDENTE_CARRETERA  " + 
				"FUEGO  " + 
				"INUNDACION  " + 
				"ACCIDENTE_AEREO  " + 
				"METEOROLOGICA  " + 
				"VALOR_NO_ASIGNADO";
		boolean algoMal = false;
		String palabrasMal = "";
		for (String palabra : palabras) {
			if (!categorias.contains(palabra)) {
				algoMal=true;
				palabrasMal += " "+ palabra;
			}}
		if(!algoMal) {
			inci.addListaCategorias(mensaje);
			Mensaje m5 = new Mensaje(new Date(), "Por último, escriba las propiedades separadas por comas con el siguiente formato:", "bot");
			addMensaje(m5);
			m5 = new Mensaje(new Date(), "nombre_propiedad/valor_propiedad , ..... , nombre_propiedadN/valor:propiedadN ", "bot");
			addMensaje(m5);
			pasos++;
		}else {
			Mensaje m5 = new Mensaje(new Date(), "Ups... no se han podido reconocer. Las categorias"+palabrasMal+ " no están permitidas", "bot");
			addMensaje(m5);
			m5 = new Mensaje(new Date(), "Vuelva a introducir todas categorias que quiera", "bot");
			addMensaje(m5);
		}
	}

	private void paso4(String mensaje) {
		String mensajeMayusculas = mensaje.toUpperCase();
		String[] palabras = mensajeMayusculas.split(",");
		String propiedades ="TEMPERATURA "
				+ "PRESION "
				+ "HUMEDAD "
				+ "VELOCIDAD_VIENTO "
				+ "VELOCIDAD_CIRCULACION "
				+ "VALOR_NO_ASIGNADO "
				+ "NIVEL_POLUCION "
				+ "CALIDAD_AIRE";

		boolean algoMal = false;
		String palabrasMal = "";
		for (String palabra : palabras) {
			String p = palabra.split("/")[0];
			if (!propiedades.contains(p)) {
				algoMal=true;
				palabrasMal += " "+ palabra;
			}
			try {
				Integer.parseInt(palabra.split("/")[1]);
			}catch (Exception e) {
				algoMal=true;
			}

		}

		if(!algoMal) {
			inci.addListaPropiedades(mensaje);
			Mensaje m5 = new Mensaje(new Date(), "Genial, ya hemos recabado todos los datos que necesitabamos... ", "bot");
			addMensaje(m5);
			m5 = new Mensaje(new Date(), "Su incidencia ha sido creada con éxito", "bot");
			addMensaje(m5);
			creacionEnProceso = false;
			pasos=0;
		}else {
			Mensaje m5 = null;
			if(palabrasMal=="") {
				m5 = new Mensaje(new Date(), "Ups... no se han podido reconocer todas las propiedades. El valor de la propiedad debe ser un valor numético", "bot");
				addMensaje(m5);
			}else {
				m5 = new Mensaje(new Date(), "Ups... no se han podido reconocer todas las propiedades. Las propiedades"+palabrasMal+ " no están permitidas", "bot");
				addMensaje(m5);
			}
			m5 = new Mensaje(new Date(), "Vuelva a introducir todas propiedades que quiera", "bot");
			addMensaje(m5);
		}
	}
	public Incidencia getInci() {
		return inci;
	}
	public void setInci(Incidencia inci) {
		this.inci = inci;
	}
	
	
	
}
