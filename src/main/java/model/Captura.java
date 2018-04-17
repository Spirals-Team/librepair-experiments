package model;

import model.device.roles.Sniffer;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Captura {
	
	private Sniffer Sniffer;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	private ArrayList<Package> paquetes;
	
	public Captura(Sniffer Sniffer, LocalDateTime inicio, LocalDateTime fin){
		
		this.setSniffer(Sniffer);
		this.setInicio(inicio);
		this.setFin(fin);
		this.paquetes = new ArrayList<Package>();
	}
	
	private void addPackages(Package paquete){
		paquetes.add(paquete);
		return;
	}
	
	public ArrayList<Package> getPaquetes(){
		return this.paquetes;
	}

	public Sniffer getSniffer() {
		return Sniffer;
	}

	public void setSniffer(Sniffer Sniffer) {
		this.Sniffer = Sniffer;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}

}
