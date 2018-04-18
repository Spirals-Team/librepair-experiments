package com.economizate.batch;

import java.io.IOException;

import com.economizate.servicios.Usuarios;
import com.economizate.servicios.impl.Propiedad;
import com.economizate.servicios.impl.TXTWriter;
import com.economizate.servicios.impl.UsuariosImpl;

public class BackupArchivo implements IBackup{

	Usuarios usuarios = new UsuariosImpl();
	
	public void generarBackupMovimientos() {
		try {
			new TXTWriter("backup.txt").write(usuarios
					.buscarUsuarioPorEmail(Propiedad.getInstance().getPropiedad("email"))
					.getSaldo()
					.getMovimientos().toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void aceptarVisitor(IVisitor visitante) {
		visitante.visitar(this);
	}

}
