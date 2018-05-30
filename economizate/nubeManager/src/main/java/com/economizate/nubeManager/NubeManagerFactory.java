package com.economizate.nubeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.economizate.servicios.INube;

public class NubeManagerFactory {
	
	static Map<String, INube> connectors = ListaNubes.loadNubes();
	
	public static INube getNube(String nube, String urlHistorial) {
		return connectors.get(nube);
	}
	
	public static List<INube> getNube(List<String> clouds, String urlHistorial) {
		List<INube> nubes = new ArrayList<INube>();
		for(String c : clouds) {
			nubes.add(connectors.get(c));
			}
		return nubes;
	}
}
