package com.economizate.batch;

import com.economizate.servicios.Usuarios;
import com.economizate.servicios.impl.UsuariosImpl;

public class BackupDrive implements IBackup{
	
	Usuarios usuarios = new UsuariosImpl();
	
	@Override
	public void generarBackupMovimientos() {
		//TODO implementar
	}

}
