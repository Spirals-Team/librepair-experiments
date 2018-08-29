package ar.com.utn.dds.sge.state;

public class ModoAhorro implements Estado {

	@Override
	public Float consumo(Float consumo) {
		return consumo * 0.75f;
	}

}
