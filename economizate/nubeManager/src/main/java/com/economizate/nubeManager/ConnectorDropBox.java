package com.economizate.nubeManager;

import com.economizate.servicios.INube;

public class ConnectorDropBox implements INube{

	@Override
	public boolean conectar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean upload() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Enum<?> getTipo() {
		return NubeEnum.DROPBOX;
	}
	
}
