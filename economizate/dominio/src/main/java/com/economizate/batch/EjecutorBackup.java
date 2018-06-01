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
		case TRESSEGUNDOS:
			timer.schedule(new ProcesoPeriodico(backups), 0, Long.parseLong("3000"));
			break;
		case CINCOMINUTOS:
			timer.schedule(new ProcesoPeriodico(backups), 0, Long.parseLong("300000"));
			break;
		case DIARIO:
			timer.schedule(new ProcesoPeriodico(backups), 0, 5000l); //86400000l); -> 24hs
			break;
		case SEMANAL:
			timer.schedule(new ProcesoPeriodico(backups), 0, 6000l); //604800000l); -> 7 dias
			break;
		case MENSUAL:
			timer.schedule(new ProcesoPeriodico(backups), 0, 7000l); //2419200000l); ->1 mes
			break;
		case ANUAL:
			timer.schedule(new ProcesoPeriodico(backups), 0, 8000l); //29030400000l);
			break;
		case DEFAULT:
			timer.schedule(new ProcesoPeriodico(backups), 0, 3000l);
			break;
		default:
			timer.schedule(new ProcesoPeriodico(backups), 0, 86400000l);
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
