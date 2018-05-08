package inciManager.uo.asw.mvc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import inciManager.uo.asw.chatbot.Chat;
import inciManager.uo.asw.chatbot.Mensaje;

@Service
public class AgentService {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private IncidenciasService incidenciasService;

	private Chat chatbot = new Chat();
	
	RestTemplate restTemplate = new RestTemplate();

	/**
	 * Metodo que sirve de comunicacion con el modulo Agents ,se le envia el usuario, login y
	 * pass en un JSON y nos contesta con un OK o un NO, en caso de ser un OK
	 * nos devuelve todos los datos del agente, se puede modificar este
	 * metodo para añadir los distintos campos que queramos utilizar de esta respuesta.
	 * 
	 * @param login del agente, en este caso el nombre que se utiliza para inciar
	 * sesion en Manager (String)
	 * @param password del agente (String)
	 * @param kind de agente (Sring)
	 * @return un map con la información extraida del JSON
	 */
	public Map<String, Object> communicationAgents(String login, String password, String kind) {
		Map<String, Object> datosAgente = new HashMap<String, Object>();
		Map<String, Object> datosJson = new HashMap<String, Object>();
		String urlAgents = "http://18.237.112.43:8091/user";
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
			creaSesion(json.getString("id"), datosAgente);
			return datosAgente;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() != HttpStatus.NOT_FOUND) {
				throw ex;
			}
		}
		return null;
	}
	
	/**
	 * Método para crear la sesión de cada usuario que accede a la web
	 * @param idUsuario el id del Usuario en sesión
	 * @param datosAgente sus datos
	 */
	private void creaSesion(String idUsuario, Map<String, Object> datosAgente) {
		httpSession.setAttribute("idAgente", idUsuario);
		httpSession.setAttribute("datosAgente", datosAgente);
	}

	public void setIdConnected(String idAgent) {
		httpSession.setAttribute("sesion", idAgent);
		
		chatbot = new Chat();
	}

	public String getIdConnected() {
		return (String) httpSession.getAttribute("idAgente");
	}
	
	public void borrarDatosAgente() {
		httpSession.setAttribute("datosAgente", null);
	}

	public Map<String, Object> getDatosAgente() {
		return (Map<String, Object>) httpSession.getAttribute("datosAgente");
	}
	
	public String getLatitude() {
		return getLocation().get("latitud");
	}
	
	public String getLongitude() {
		return getLocation().get("longitud");
	}
	
	private Map<String, String> getLocation(){
		Map<String, String> localizacion = new HashMap<String, String>();
		String[] splited = ((String)getDatosAgente().get("location")).split("\"");
		localizacion.put("latitud", splited[1]);
		localizacion.put("longitud", splited[3]);
		return localizacion;
	}
	
	//----- chatbot
	public void addNewMensajeChat(String mensaje) {
		Mensaje m = new Mensaje(new Date(), mensaje, getIdConnected());
		chatbot.addMensaje(m);
		chatbot.calcularRespuesta(mensaje);
		if(chatbot.getMensajes().get(chatbot.getMensajes().size() -1).getContenido() 
				== "Su incidencia ha sido creada con éxito") {
			chatbot.getInci().setIdAgente(getIdConnected());
			incidenciasService.addIncidencia(chatbot.getInci());
			incidenciasService.guardarPropiedadesYcategoria(chatbot.getInci());
			incidenciasService.enviarIncidenciaWeb(chatbot.getInci());
		}
	}
	public List<Mensaje> getMensajesChatBot(){
		return chatbot.getMensajes();
	}
}
