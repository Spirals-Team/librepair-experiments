package uo.asw.dbManagement.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import uo.asw.dbManagement.tipos.PropiedadTipos;

@Document(collection = "valores")
public class ValorLimite {

	@Id
	private ObjectId id = new ObjectId();

	private double valorMax;
	private double valorMin;
	private String propiedad;
	private boolean maxCritico;
	private boolean minCritico;

	public ValorLimite() {
		
	}
	
	
	public ValorLimite(double valorMax, double valorMin, String propiedad, boolean maxCritico,
			boolean minCritico) {
		this.valorMax = valorMax;
		this.valorMin = valorMin;
		this.propiedad = propiedad;
		this.maxCritico = maxCritico;
		this.minCritico = minCritico;
	}


	public ValorLimite(String propiedad, double valorMax, double valorMin) {
		this.propiedad = propiedad;
		this.valorMax = valorMax;
		this.valorMin = valorMin;
	}

	@Override
	public String toString() {
		return "ValorLimite [id=" + id + ", valorMax=" + valorMax + ", valorMin=" + valorMin + ", propiedad="
				+ propiedad + ", maxCritico=" + maxCritico + ", minCritico=" + minCritico + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((propiedad == null) ? 0 : propiedad.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorMax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(valorMin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValorLimite other = (ValorLimite) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (propiedad != other.propiedad)
			return false;
		if (Double.doubleToLongBits(valorMax) != Double.doubleToLongBits(other.valorMax))
			return false;
		if (Double.doubleToLongBits(valorMin) != Double.doubleToLongBits(other.valorMin))
			return false;
		return true;
	}

	public ObjectId getId() {
		return id;
	}

	public double getValorMax() {
		return valorMax;
	}

	public void setValorMax(double valorMax) {
		this.valorMax = valorMax;
	}

	public double getValorMin() {
		return valorMin;
	}

	public void setValorMin(double valorMin) {
		this.valorMin = valorMin;
	}

	public String getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public boolean isMaxCritico() {
		return maxCritico;
	}

	public void setMaxCritico(boolean maxCritico) {
		this.maxCritico = maxCritico;
	}

	public boolean isMinCritico() {
		return minCritico;
	}

	public void setMinCritico(boolean minCritico) {
		this.minCritico = minCritico;
	}
	
	

}
