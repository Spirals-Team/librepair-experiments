package uo.asw.entities;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "incidencias")
public class Incidence {

	public static final List<IncidenceStatus> estados = Arrays.asList(IncidenceStatus.values());
	public static final List<TipoIncidencia> tipos = Arrays.asList(TipoIncidencia.values());
	
	@Id
	private ObjectId _id;

	private String name, description, emailAgente;

	private Date date;
	private IncidenceStatus status;

	private List<String> tags;

	private Map<String, String> properties;

	// Implementacion de notificaciones valores peligrosos

	private TipoIncidencia type;

	private Double valor;
	private double latitud, longitud;

	public Incidence(String name, String description, String agent, List<String> tags, TipoIncidencia tipo,
			Double valor) {
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.date = new Date();
		this.status = IncidenceStatus.OPENED;
		this.emailAgente = agent;
		this.date = new Date();
		this.tags = tags;
		this.type = tipo;
		this.valor = valor;

	}
	
	public Incidence() {}

	public String getAgente()
	{
		return emailAgente;
	}
	
	public void setAgente(String agente)
	{
		this.emailAgente=agente;
	}
	
	public TipoIncidencia getTipo() {
		return type;
	}

	public void setTipo(TipoIncidencia tipo) {
		this.type = tipo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getTags() {
		return tags;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public IncidenceStatus getStatus() {
		return status;
	}

	public void setStatus(IncidenceStatus status) {
		this.status = status;
	}

	public String toJSON() {
		return "{ \"_id\" : \"" + _id + "\", " 
				+ " \"name\" : \"" + name + "\", " 
				+ " \"description\" : \""+ description + "\", " 
				+ " \"date\" : \"" + date + "\", " 
				+ " \"status\" : \"" + status + "\", "
				+ " \"agent\" : \"" + emailAgente + "\", " 
				+ " \"tags\" : [" + tagsList() + "], "
				+ " \"type\" : \"" + type + "\", " 
				+ " \"valor\" : " + valor + ", "
				+ " \"latitud\" : " + latitud + ", "
				+ " \"longitud\" : " + longitud + "} ";
	}

	private String tagsList() {
		String list = "";
		for (int i = 0; i < tags.size(); i++)
			list += "\"" + tags.get(i) + "\"" + ((i == tags.size() - 1) ? "" : ", ");
		return list;
	}

	

	@Override
	public String toString() {
		return "Incidence [_id=" + _id + ", name=" + name + ", description=" + description + ", agent=" + emailAgente
				+ ", date=" + date + ", status=" + status + ", tags=" + tags + ", properties=" + properties + ", type="
				+ type + ", valor=" + valor + ", latitud=" + latitud + ", longitud=" + longitud + "]";
	}

	public ObjectId getId() {
		return _id;
	}

	public void setIdentificador(ObjectId id) {
		this._id = id;
	}

	public Date getDate() {
		return date;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public void setName(String name) 
	{
		this.name=name;
	}
	
	public static TipoIncidencia parseTipo(String tipo) {
		return tipos.stream().filter(x -> x.toString().equals(tipo)).findFirst().get();
	}
	
	public static IncidenceStatus parseEstado(String estado) {
		return estados.stream().filter(x -> x.toString().equals(estado)).findFirst().get();
	}

}