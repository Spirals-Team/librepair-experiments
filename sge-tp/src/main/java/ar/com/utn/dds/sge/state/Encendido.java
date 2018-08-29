package ar.com.utn.dds.sge.state;

public class Encendido implements Estado {

	@Override
	public Float consumo(Float consumo) {
		return consumo;
	}

}
