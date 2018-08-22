package ar.com.utn.dds.sge.models;

public class DispositivoAdaptado extends DispositivoInteligente{

	private DispositivoEstandar dispositivoEstandar;

	public DispositivoAdaptado(DispositivoEstandar dispositivoEstandar) {
		super(dispositivoEstandar.getTipo(), dispositivoEstandar.getNombre(), dispositivoEstandar.getConsumo());
		this.setDispositivoEstandar(dispositivoEstandar);
	}

	public DispositivoEstandar getDispositivoEstandar() {
		return dispositivoEstandar;
	}

	public void setDispositivoEstandar(DispositivoEstandar dispositivoEstandar) {
		this.dispositivoEstandar = dispositivoEstandar;
	}
	
}
