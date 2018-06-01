package com.economizate.nubeManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.economizate.servicios.INube;

public class ListaNubes {
	
	static Map<String, INube> connectors = new HashMap<String, INube>();
	
	public static List<String> getListaNubes() {
		return Arrays.asList(
				"GOOGLEDRIVE",
				"DROPBOX",
				"ONEDRIVE");
	}
	
	public static Map<String, INube> loadNubes(){
		List<String> nubes = ListaNubes.getListaNubes();
		for (String s: nubes) {
			if("GOOGLEDRIVE".equals(s))
				connectors.put(s,new ConnectorDrive());
		}
		return connectors;
	}

}
