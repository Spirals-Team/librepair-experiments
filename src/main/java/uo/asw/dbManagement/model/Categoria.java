
package uo.asw.dbManagement.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import uo.asw.dbManagement.tipos.CategoriaTipos;

@Document(collection = "categorias")
public class Categoria {
	@Id
	private ObjectId id = new ObjectId();
	
	private CategoriaTipos categoria;
	
	public Categoria() {}

	public Categoria(CategoriaTipos categoria) {
		super();
		this.categoria = categoria;
	}
	
	public Categoria(String categoria) {
		super();
		this.categoria = this.obtenerCategoria(categoria);
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public CategoriaTipos getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaTipos categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", categoria=" + categoria 
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Categoria other = (Categoria) obj;
		if (categoria != other.categoria)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Devuelve un tipo de categoria según la categoria pasada como 
	 * parámetro
	 * @param String categoria
	 * @return CategoriaTipos
	 */
	private CategoriaTipos obtenerCategoria(String categoria) {  
		if (categoria.toUpperCase().equals("ACCIDENTE_CARRETERA"))
			return CategoriaTipos.ACCIDENTE_CARRETERA;
		if (categoria.toUpperCase().equals("INUNDACION"))
			return CategoriaTipos.INUNDACION;
		if (categoria.toUpperCase().equals("FUEGO"))
			return CategoriaTipos.FUEGO;
		if (categoria.toUpperCase().equals("ACCIDENTE_AEREO"))
			return CategoriaTipos.ACCIDENTE_AEREO;
		if (categoria.toUpperCase().equals("METEOROLOGICA"))
			return CategoriaTipos.METEOROLOGICA;
		if (categoria.toUpperCase().equals("AMBIENTE"))
			return CategoriaTipos.AMBIENTE;
		if (categoria.toUpperCase().equals("AUTOMATICO"))
			return CategoriaTipos.AUTOMATICO;
		if (categoria.toUpperCase().equals("CONTAMINACION"))
			return CategoriaTipos.CONTAMINACION;
		
		return CategoriaTipos.VALOR_NO_ASIGNADO;
	}

}
