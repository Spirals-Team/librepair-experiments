package com.economizate.nubeManager;

import com.economizate.servicios.INube;

public class ConnectorOneDrive implements INube{

	@Override
	public boolean conectar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean upload(String pathFile) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Enum<?> getTipo() {
		return NubeEnum.ONEDRIVE;
	}

}
