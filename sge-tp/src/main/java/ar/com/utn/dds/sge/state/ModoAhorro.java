package ar.com.utn.dds.sge.state;

import ar.com.utn.dds.sge.models.DispositivoInteligente;

public class ModoAhorro implements Estado {

	@Override
	public void manejar(DispositivoInteligente di) {
		// TODO Auto-generated method stub
		di.activarModoAhorro();
	}

}
