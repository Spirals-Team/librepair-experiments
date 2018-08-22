package ar.com.utn.dds.sge.conditions;

public abstract class Condicion <T>{
	private Comparable<T> condicionante;
	
	public Condicion(Comparable<T> condicionante) {
		super();
		this.condicionante = condicionante;
	}
	

	/**
	 * @return the condicionante
	 */
	public Comparable<T> getCondicionante() {
		return condicionante;
	}

	/**
	 * @param condicionante the condicionante to set
	 */
	public void setCondicionante(Comparable<T> condicionante) {
		this.condicionante = condicionante;
	}

	/**
	 * @param valor Valor a comparar
	 * @return Retorna true en caso de que la condicion se cumpla
	 */
	public abstract Boolean comparar(Comparable<T> valor);

}
