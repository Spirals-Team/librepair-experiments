package ar.com.utn.dds.sge.state;

public class Apagado implements Estado {

	@Override
	public Float consumo(Float consumo) {
		return 0.0f;
	}

}
