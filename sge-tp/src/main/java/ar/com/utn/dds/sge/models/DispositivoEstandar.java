package ar.com.utn.dds.sge.models;

public class DispositivoEstandar extends Dispositivo {

		private Float hsDeUsoXDia;
		
		public DispositivoEstandar(String tipo, String nombre, Float consumo) {
			this.setTipo(tipo);
			this.setNombre(nombre);
			this.setConsumo(consumo);
		}
		
		public Float gethsDeUsoXDia() {
			return hsDeUsoXDia;
		}

		/**
		 * @param estado the estado to set
		 */
		public void sethsDeUsoXDia(Float hsDeUsoXDia) {
			this.hsDeUsoXDia = hsDeUsoXDia;
		}
}
