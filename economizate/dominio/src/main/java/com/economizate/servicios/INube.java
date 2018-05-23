package com.economizate.servicios;

public interface INube {
	
	/**
	 * Establece conexion con la "nube"
	 * @return
	 */
	public boolean conectar();
	
	/**
	 * Sube archivos a la nube, historial de movimientos en ese caso 
	 * @return
	 */
	public boolean upload();
	
	
	/**
	 * Devuelve que tipo de servicio en la nube brinda
	 * @return
	 */
	
	public Enum<?> getTipo();
	
	

}
