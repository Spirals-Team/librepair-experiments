package ar.edu.untref.aydoo;

public class Inversor {
	private double gananciaObtenida;
	
	public Inversor() {
		this.gananciaObtenida = 0;
	}
	
	public void realizarInversion(Inversion inversion) {
		this.gananciaObtenida = inversion.calcularGanancia();
	}
	
	public double getGanancias() {
		return this.gananciaObtenida;
	}
}
