package uo.asw.inciManager.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import uo.asw.chatbot.Chat;
import uo.asw.chatbot.Mensaje;

@Service
public class AgentService {
	
	@Autowired
	private IncidenciasService incidenciasService;

	private Chat chatbot = new Chat();
	
	private String idConnectedAgent;
	private Map<String, Object> datosAgente;

	RestTemplate restTemplate = new RestTemplate();

	/**
	 * Metodo que sirve de comunicacion con agents , se le envia el usuario, login y
	 * pass y devuelve el id del usuario.
	 * 
	 * @param login
	 * @param password
	 * @param kind
	 * @return
	 */
	public Map<String, Object> communicationAgents(String login, String password, String kind) {
		Map<String, Object> datosAgente = new HashMap<String, Object>();
		Map<String, Object> datosJson = new HashMap<String, Object>();
		String urlAgents = "http://localhost:8091/user";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		datosJson.put("login", login);
		datosJson.put("password", password);
		datosJson.put("kind", kind);
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(datosJson, headers);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(urlAgents, request, String.class);
			JSONObject json = new JSONObject(response.getBody());
			System.out.println("------------ OK -------------");
			/* añadimos los que sean necesarios... */
			datosAgente.put("id", json.getString("id"));
			datosAgente.put("location", json.getString("location"));
			this.datosAgente = datosAgente;
			return datosAgente;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() != HttpStatus.NOT_FOUND) {
				throw ex;
			}
		}
		this.datosAgente=null;
		return null;
	}

	public void setIdConnected(String idAgent) {
		idConnectedAgent = idAgent;
		
		chatbot = new Chat();
	}

	public String getIdConnected() {
		return idConnectedAgent;
	}

	public Map<String, Object> getDatosAgente() {
		return datosAgente;
	}

	public void setDatosAgente(Map<String, Object> datosAgente) {
		this.datosAgente = datosAgente;
	}
	
	public String getLatitude() {
		return ((String) datosAgente.get("location")).split(" - ")[0];
	}
	
	public String getLongitude() {
		return ((String) datosAgente.get("location")).split(" - ")[1];
	}
		
	public String getLocation() {
		return (String) datosAgente.get("location");
	}
	
	//----- chatbot
	public void addNewMensajeChat(String mensaje) {
		Mensaje m = new Mensaje(new Date(), mensaje, idConnectedAgent);
		chatbot.addMensaje(m);
		chatbot.calcularRespuesta(mensaje);
		if(chatbot.getMensajes().get(chatbot.getMensajes().size() -1).getContenido() 
				== "Su incidencia ha sido creada con éxito") {
			chatbot.getInci().setIdAgente(idConnectedAgent);
			incidenciasService.addIncidencia(chatbot.getInci());
			incidenciasService.guardarPropiedadesYcategoria(chatbot.getInci());
			incidenciasService.enviarIncidenciaWeb(chatbot.getInci());
		}
	}
	public List<Mensaje> getMensajesChatBot(){
		return chatbot.getMensajes();
	}
}
