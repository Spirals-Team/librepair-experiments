package com.economizate.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class EjecutorBackup {	
	
	private List<IBackup> backups;
	private BackupTimer frecuencia;
	private Timer timer;
	
	public EjecutorBackup(BackupTimer frecuencia, IBackup... backups) {
		this.frecuencia = frecuencia;
		this.backups = aLista(backups);
		this.timer = new Timer();
	}
	
	public EjecutorBackup(BackupTimer frecuencia, List<IBackup> backups) {
		this.frecuencia = frecuencia;
		this.backups = backups;
		this.timer = new Timer();
	}
	
	public void ejecutar() {
		switch(this.frecuencia) {
		case DIARIO:
			timer.schedule(new ProcesoPeriodico(backups), 1000, Long.parseLong("5184000000"));
			break;
		case DEFAULT:
			timer.schedule(new ProcesoPeriodico(backups), 1000, 300000l);
			break;
		default:
			timer.schedule(new ProcesoPeriodico(backups), 3000, Long.parseLong("5184000000"));
			break;
		}
		
	}
	
	private List<IBackup> aLista(IBackup... backups){
		List<IBackup> lista = new ArrayList<>();
		for(IBackup b : backups) {
			lista.add(b);
		}
		return lista;
	}
}
