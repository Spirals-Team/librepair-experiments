package ar.com.utn.dds.sge.commands;

public class Actuador {

	private Comando comando;
	
	public Actuador(Comando comando) {
		setComando(comando);
	}

	public void run() {
		comando.ejecutar();
	}
	
	public Comando getComando() {
		return comando;
	}

	public void setComando(Comando comando) {
		this.comando = comando;
	}
}
