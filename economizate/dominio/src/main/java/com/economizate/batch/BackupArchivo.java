package com.economizate.batch;

import java.io.IOException;

import com.economizate.servicios.Cuenta;
import com.economizate.servicios.impl.CuentaImpl;
import com.economizate.servicios.impl.TXTWriter;


public class BackupArchivo implements IBackup{

	Cuenta cuenta = new CuentaImpl();
	private String path;
	
	public BackupArchivo(String path) {
		this.path = path;
	}
	
	@Override
	public void generarBackupMovimientos() {
		try {
			new TXTWriter(path + "backup-" + System.currentTimeMillis() + ".txt", cuenta.obtenerHistorialMovimientos().toString())
			.write();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
