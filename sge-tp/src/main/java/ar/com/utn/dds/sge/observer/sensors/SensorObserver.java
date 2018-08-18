package ar.com.utn.dds.sge.observer.sensors;

/**
 * 
 * Interfaz que deberan implementar aquellas clases que esten pendientes
 * de los cambios en el valor que informa un sensor.
 *
 */
public interface SensorObserver {
	public void actualizar(SensorSGE sensor);
}
