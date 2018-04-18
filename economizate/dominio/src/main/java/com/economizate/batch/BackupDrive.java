package com.economizate.batch;

import com.economizate.servicios.Usuarios;
import com.economizate.servicios.impl.UsuariosImpl;

public class BackupDrive implements IBackup{
	
	Usuarios usuarios = new UsuariosImpl();
	
	public void generarBackupMovimientos() {
		//TODO implementar
	}

	@Override
	public void aceptarVisitor(IVisitor visitante) {
		visitante.visitar(this);
	}

}
