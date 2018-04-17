package model;

import java.time.LocalDateTime;

public class Intervalo {
	
	private LocalDateTime inicioIntervalo;
	private LocalDateTime finIntervalo;
	
	public Intervalo(LocalDateTime inicio, LocalDateTime fin){
		
		this.inicioIntervalo = inicio;
		this.finIntervalo = fin;	
	}
	

	public LocalDateTime getInicio() {
		return inicioIntervalo;
	}

	public LocalDateTime getFin() {
		return finIntervalo;
		
	}
	
	public boolean estaEnIntervalo(LocalDateTime dateTime){
		
		if ( (dateTime.isBefore(finIntervalo) || dateTime.equals(finIntervalo) )&& 
				(dateTime.isAfter(inicioIntervalo) || dateTime.equals(inicioIntervalo))){
			return true;
		}
		
		return false;
	}
}
