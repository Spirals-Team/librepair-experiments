package ar.com.utn.dds.sge.observer.sensors;

/**
 * 
 * Interfaz generica de sensores.
 *
 */
public interface ISensor {
	/**
	 * @return Valor de la medida que obtuvo el sensor. Dependiendo el tipo de sensor
	 * y su fabricante, el valor puede informarse en distintos formatos, como por ejemplo
	 * XML, json, trama de bits, etc.
	 */
	public Object obtenerValor();

}
