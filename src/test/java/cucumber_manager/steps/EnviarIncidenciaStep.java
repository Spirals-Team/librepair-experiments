package cucumber_manager.steps;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;
import uo.asw.entities.Incidence;
import uo.asw.entities.TipoIncidencia;
import uo.asw.services.IncidenceService;

public class EnviarIncidenciaStep {
    
    @Autowired
    IncidenceService incidenceService;
    
    String user, password, type;
    String nombreIncidencia, descripcionIncidencia;
    List<String> etiquetas;

    @Dado("^el email del agente que va a enviar la incidencia \'([^\"]*)\'$")
    public void email(String user) 
    {
    	this.user = user;
    	System.out.println("El usuario del agente es " + user);
    }

    @Y("^su password \'([^\"]*)\'$")
    public void password(String password) 
    {
    	this.password = password;
    }

    @Y("^su tipo \'([^\"]*)\'$")
    public void tipo(String type) 
    {
    	this.type = type;
    }
    
    
    @Dado("^la incidencia recogida por un agente loggeado$")
    public void la_incidencia_con_agente_loggeado() throws Throwable 
    {	
    	System.out.println("El agente que envia la incidencia es:"+user);
    }

    @Y("^con nombre \'([^\"]*)\'$")
    public void nombre_de_la_incidencia(String nombreIncidencia) throws Throwable 
    {
    	this.nombreIncidencia = nombreIncidencia;
    	System.out.println("El nombre de la incidencia es: " + nombreIncidencia);
    }

    @Y("^descripcion \'([^\"]*)\'$")
    public void descripcion_de_la_incidencia(String descripcionIncidencia) throws Throwable 
    {
    	this.descripcionIncidencia = descripcionIncidencia;
    	System.out.println("La descripcion de la incidencia es: " + descripcionIncidencia);
    }

    @Y("^etiquetas \'([^\"]*)\'$")
    public void etiquetas_de_la_incidencia(ArrayList<String> etiquetas) throws Throwable 
    {
    	this.etiquetas = etiquetas;
    	System.out.println("Éstas son las etiquetas de la incidencia");
    	for (String string : etiquetas) 
    	{
			System.out.println(string);
		}
    	
    }

    @Cuando("^tratamos de enviar esa incidencia a través de Kafka$")
    public void introduzco_los_datos_en_el_formulario() 
    {
		incidenceService.addIncidence(new Incidence(nombreIncidencia, descripcionIncidencia, user, etiquetas, TipoIncidencia.SENSOR_TEMPERATURA, 0.0));
		
    }
    
    @Entonces("^se envia la incidencia correctamente$")
    public void se_envia_la_peticion() 
    {
    	System.out.println("Incidencia enviada correctamente");
    }

}
