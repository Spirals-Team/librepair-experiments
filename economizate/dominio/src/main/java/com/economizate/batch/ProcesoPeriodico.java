package com.economizate.batch;

import java.util.List;
import java.util.TimerTask;

public class ProcesoPeriodico extends TimerTask{
	
	List<IBackup> listaBackups;
	
	public ProcesoPeriodico(List<IBackup> backups) {
		this.listaBackups = backups;
	}
	
	@Override
	public void run() {
		listaBackups.forEach(b -> b.generarBackupMovimientos());
	}

}
