package com.economizate.nubeManager;

import java.util.List;

import com.economizate.servicios.INube;

public class NubeManagerFactory {
	
	public static INube getNube(List<String> clouds, String urlHistorial) {
		for(String c : clouds) {
			if("GOOGLEDRIVE".equals(c))
					return new ConnectorDrive(urlHistorial);
			else
					return new ConnectorDrive(urlHistorial);
			}
		
		return null;
	}

}
