package ar.com.utn.dds.sge.observer.sensors;

import java.util.List;

/**
 * 
 * Modelo del sensor que se utiliza en SGE.
 *
 */
public abstract class SensorSGE {
	/**
	 * Valor obtenido por el sensor. Puede ser Float en caso de obtener numeros
	 * decimales(Temperatura), Boolean en caso de indicar uno de 2 estados posibles(ej: Puerta abierta o cerrada), 
	 * o incluso un Enum en caso de obtenerse un valor entre ciertos valores posibles predefinidos. 
	 */
	private Object valor;
	
	//Magnitud en la que esta expresada el valor. Ej: C° en caso de temperatura.
	private String magnitud;
	
	/**
	 * Perdiodo en que se consulta el valor obtenido por el sensor.
	 */
	private int periodoEnMilis;
	
	/**
	 * Lista de observadores que estan pendientes de algun cambio en el valor
	 * del sensor.
	 */
	private List<SensorObserver> observadores;

	public SensorSGE(String magnitud, int periodoEnMilis, List<SensorObserver> observadores) {
		this.magnitud = magnitud;
		this.periodoEnMilis = periodoEnMilis;
		this.observadores = observadores;
	}

	/**
	 * @return the magnitud
	 */
	public String getMagnitud() {
		return magnitud;
	}

	/**
	 * @param magnitud the magnitud to set
	 */
	public void setMagnitud(String magnitud) {
		this.magnitud = magnitud;
	}

	/**
	 * @return the periodoEnMilis
	 */
	public int getPeriodoEnMilis() {
		return periodoEnMilis;
	}

	/**
	 * @param periodoEnMilis the periodoEnMilis to set
	 */
	public void setPeriodoEnMilis(int periodoEnMilis) {
		this.periodoEnMilis = periodoEnMilis;
	}

	/**
	 * @return the observadores
	 */
	public List<SensorObserver> getObservadores() {
		return this.observadores;
	}

	/**
	 * @param observadores the observadores to set
	 */
	public void setObservadores(List<SensorObserver> observadores) {
		this.observadores = observadores;
	}
	
	public void agregarObservador(SensorObserver observador){
		this.observadores.add(observador);
	}
	
	public Boolean EliminarObservador(SensorObserver observador){
		return this.observadores.remove(observador);
	}
	
	private void notificar() {
		this.observadores.stream().forEach(observador -> observador.actualizar(this));
	}

	/**
	 * 
	 * Metodo que se utiliza para que se realice una medicion
	 * 
	 */
	public void realizarMedicion() {
		Object ultimoValorMedido = this.valor;
		
		//se llama al metodo que obtiene el valor del sensor. Nota: Este metodo debe ser implementado.
		this.valor = this.getValor();
		
		//Cambio el valor de la medicion anterior, se debe notificar a los observadores
		if(!ultimoValorMedido.equals(this.valor)) {
			this.notificar();
		}
			
	}
	
	/**
	 * @return El valor de la medida obtenida por el sensor
	 */
	public abstract Comparable getValor();
}
