package com.uniovi.apacheKafka;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.uniovi.controllers.InciDashboardController;
import com.uniovi.model.Incidence;
import com.uniovi.model.Propiedad;
import com.uniovi.model.User;
import com.uniovi.repositories.UsersRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.ManagedBean;

/**
 * Created by ISMAEL on 20/3/18.
 */
@ManagedBean
public class MessageListener {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    InciDashboardController controller;

    // Con la anotacion KafkaListener indicamos que nos suscribimos al topic.
    // Puede que esta clase no se tenga que utilizar pero asi seran mas o menos los
    // metodos que recojan incidencias de kafka
    @KafkaListener(topics = "incidencia")
    public void listen(String data) {
	System.out.println(data);
	for (SseEmitter emitter : controller.emitters) {
	    try {
		emitter.send(data, MediaType.APPLICATION_JSON);
	    } catch (IOException e) {
		emitter.complete();
	    }
	}
    }

    private Incidence convertirJSONaIncidence(String data) throws JSONException {
	// Ejemplo de incidencia en JSON
	// String data = "{ \"identificador\": \"usuario\", \"password\":
	// \"contraUsuario\", \"nombre\": \"nieve\","
	// + " \"descripcion\": \"atasco en A6 por nevadas\", \"localizacion\":
	// \"32.23&23.23\", "
	// + "\"etiquetas\": [ \"Nieve\", \"Frio\", \"Atasco\" ], "
	// + "\"propiedades\": [{\"P1\" : \"V1\" },{ \"P2\" : \"V2\"}]}";
	JSONObject obj = new JSONObject(data);
	String identificador = getString(data, obj, "identificador");
	String password = getString(data, obj, "password");
	String nombre = getString(data, obj, "nombre");
	String descripcion = getString(data, obj, "descripcion");
	String localizacion = getString(data, obj, "localizacion");

	String[] arrayEtiquetas = getStringArray(data, obj, "etiquetas");
	Set<String> etiquetas = arrayEtiquetas != null ? new HashSet<String>(Arrays.asList(arrayEtiquetas))
		: new HashSet<String>();

	Set<Propiedad> propiedades = getSetPropiedades(data, obj) != null ? getSetPropiedades(data, obj)
		: new HashSet<Propiedad>();
	// Cargo el agente de la base de datos
	User user = usersRepository.findByIdentificador(identificador);

	Incidence incidence;
	if (identificador != null && user != null) {
	    incidence = null;// new Incidence(user, nombre, descripcion, localizacion, etiquetas,
			     // propiedades);
	    // Debemos mirar como tratar las listas
	} else {
	    incidence = null;
	}

	return incidence;
    }

    public String getString(String txt, JSONObject json, String key) throws JSONException {
	if (!txt.contains(key))
	    return null;
	return json.getString(key);
    }

    public String[] getStringArray(String txt, JSONObject json, String key) throws JSONException {
	if (!txt.contains(key))
	    return null;
	JSONArray arrayJSON = json.getJSONArray(key);
	String[] strings = new String[arrayJSON.length()];
	for (int i = 0; i < arrayJSON.length(); i++)
	    strings[i] = arrayJSON.getString(i);

	return strings;
    }

    public Set<Propiedad> getSetPropiedades(String txt, JSONObject json) throws JSONException {
	if (!txt.contains("propiedades"))
	    return null;

	JSONArray jsonArray = json.getJSONArray("propiedades");
	Set<Propiedad> propiedades = new HashSet<>();
	for (int i = 0; i < jsonArray.length(); i++) {
	    JSONObject jsonProperty = jsonArray.getJSONObject(i);
	    // Con la version 1.4.7 el metodo no da error
	    // String nombre = JSONObject.getNames(jsonProperty)[0];
	    String nombre = "nombre";
	    String valor = jsonProperty.getString(nombre);

	    Propiedad propiedad = new Propiedad(nombre, valor);
	    propiedades.add(propiedad);
	}
	return propiedades;
    }
}
