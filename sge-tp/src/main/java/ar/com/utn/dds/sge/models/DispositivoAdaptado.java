package ar.com.utn.dds.sge.models;

public class DispositivoAdaptado extends DispositivoInteligente{

	private DispositivoEstandar dispositivoEstandar;

	public DispositivoAdaptado(DispositivoEstandar dispositivoEstandar) {
		this.setDispositivoEstandar(dispositivoEstandar);
	}
	
	

	public DispositivoEstandar getDispositivoEstandar() {
		return dispositivoEstandar;
	}

	public void setDispositivoEstandar(DispositivoEstandar dispositivoEstandar) {
		this.dispositivoEstandar = dispositivoEstandar;
	}
	
	// Getters
	
	@Override
	public String getTipo() {
		return dispositivoEstandar.getTipo();
	}
	
	@Override
	public String getNombre() {
		return dispositivoEstandar.getNombre();
	}
	
	@Override
	public Float getConsumo() {
		return getEstado().consumo(dispositivoEstandar.getConsumo());
	}
	
}
