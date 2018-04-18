package com.economizate.batch;

import java.util.List;

public class DescargaBackup implements IVisitor{

	@Override
	public void visitar(BackupArchivo backup) {
		backup.generarBackupMovimientos();
	}

	@Override
	public void visitar(BackupDrive backup) {
		backup.generarBackupMovimientos();
	}

	@Override
	public void visitar(List<IBackup> lista) {
		lista.forEach(b -> b.aceptarVisitor(this));
	}

}
