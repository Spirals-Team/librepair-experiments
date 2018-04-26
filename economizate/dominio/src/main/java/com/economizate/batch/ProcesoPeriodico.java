package com.economizate.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class ProcesoPeriodico extends TimerTask{
	
	List<IBackup> listaBackups;
	
	
	public ProcesoPeriodico(IBackup... backups) {
		this.listaBackups = aLista(backups);
	}
	
	public ProcesoPeriodico(List<IBackup> backups) {
		this.listaBackups = backups;
	}
	
	@Override
	public void run() {
		listaBackups.forEach(b -> b.generarBackupMovimientos());
	}
	
	private List<IBackup> aLista(IBackup... backups){
		List<IBackup> lista = new ArrayList<>();
		for(IBackup b : backups) {
			lista.add(b);
		}
		return lista;
	}

}
