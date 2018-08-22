package ar.com.utn.dds.sge.models;

import java.util.ArrayList;
import java.util.List;

import ar.com.utn.dds.sge.commands.Actuador;
import ar.com.utn.dds.sge.exceptions.FieldValidationException;
import ar.com.utn.dds.sge.observer.sensors.ISensor;
import ar.com.utn.dds.sge.observer.SujetoObservable;
import ar.com.utn.dds.sge.state.Apagado;
import ar.com.utn.dds.sge.state.Encendido;
import ar.com.utn.dds.sge.state.Estado;
import ar.com.utn.dds.sge.state.ModoAhorro;

public class DispositivoInteligente extends Dispositivo implements SujetoObservable{
	private List<ISensor> sensores = new ArrayList<ISensor>();
	private Float[] consumos = new Float[6];
	private Float temperatura;
	private Integer intesidadLuminica;
	private Float humedad;
	private boolean movimiento;
	private Estado estado;

	
	public DispositivoInteligente(String tipo, String nombre, Float consumo) {
		super();
		this.setTipo(tipo);
		this.setNombre(nombre);
		this.setConsumo(consumo);
	}
	
	public void validarestadoEncendido() {
		if(estado instanceof Encendido) {
			throw new FieldValidationException("El dispositivo ya se encuentra encendido");
		}
	}

	public void validarestadoApagado() {
		if(estado instanceof Apagado) {
			throw new FieldValidationException("El dispositivo ya se encuentra apagado");
		}
	}
	
	public void validarestadoAhorro() {
		if(estado instanceof ModoAhorro) {
			throw new FieldValidationException("El dispositivo ya se encuentra en modo ahorro de energia");
		}
	}
	
	public void prender() {
		validarestadoEncendido();
		estado= new Encendido();
	}
	
	public void apagar() {
		validarestadoApagado();
		estado = new Apagado();
	}
	
	public void activarModoAhorro() {
		validarestadoAhorro();
		estado = new ModoAhorro();
	}
	
	public void calcularConsumo() {
		this.setConsumo(this.getConsumo());
		this.actualizarHistorialDeConsumo();
	}
	
	public void actualizarHistorialDeConsumo() {
		for(int i = 0; i < consumos.length - 1; i++) {
			consumos[i+1] = consumos[i];
		}
	}
	
	public Float[] obtenerPeriodoDeHistorialConsumo(Integer cantDeConsumosAObtener) {
		if(cantDeConsumosAObtener == consumos.length) {
			return consumos;
		}
		Float[] vector = new Float[cantDeConsumosAObtener];
		for(int i =0; i < cantDeConsumosAObtener; i++) {
			vector[i] = consumos[i];
		}
		return vector;
	}
	
	//getters y setters
	public List<ISensor> getObservadores() {
		return sensores;
	}

	public void agregarObservadores(ISensor sensor) {
		(this.sensores).add(sensor);
		
	}
	
	public void quitarObservadores(ISensor sensor) {
		(this.sensores).remove(sensor);
	}
	
	public Float getHumedad() {
		return humedad;
	}

	public void setHumedad(Float humedad) {
		this.humedad = humedad;
	}

		
	public float getTemperatura() {
		return temperatura;
	}

	
	public void setTemperatura(Float temperatura) {
		this.temperatura = temperatura;
	}

	public Integer getIntesidadLuminica() {
		return intesidadLuminica;
	}

	public void setIntesidadLuminica(Integer intesidadLuminica) {
		this.intesidadLuminica = intesidadLuminica;
	}

	public boolean isMovimiento() {
		return movimiento;
	}

	public void setMovimiento(boolean movimiento) {
		this.movimiento = movimiento;
	}
	
	public void notificar() {
		
	}

	@Override
	public void agregarActuador(Actuador actuador) {
		
	}

	@Override
	public void quitarActuador(Actuador actuador) {
		
	}

	@Override
	public void notificarActuadores() {
		
	}
		
}
