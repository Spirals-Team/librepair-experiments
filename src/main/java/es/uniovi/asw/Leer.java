package es.uniovi.asw;

import java.util.ArrayList;

import dao.Agente;

public class Leer {

	public static ArrayList<Agente> leerAgentesdelExcel(ArrayList<Agente> agentes, String ruta) {
		ArrayList<Agente> aux = new ArrayList<Agente>();
		
		try {			
			aux = Xlsx.leerAgentes(agentes, ruta);
		} catch (Exception e) {
			System.out.println("Extensión no soportada");
		} catch (Error e) {
			System.out.println("Extensión no soportada");
		}
		return aux;
	}

}
