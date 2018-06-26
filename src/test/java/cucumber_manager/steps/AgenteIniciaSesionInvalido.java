package cucumber_manager.steps;

import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;
import uo.asw.ManagerApplication;
import uo.asw.services.AgentsService;

@ContextConfiguration(classes = ManagerApplication.class, loader = SpringBootContextLoader.class)
@WebAppConfiguration
@ActiveProfiles("INTEGRATION_TEST")
public class AgenteIniciaSesionInvalido 
{
	@Autowired
    AgentsService agentsService;
    
    String user, password, type;

    @Dado("^el email inventado \'([^\"]*)\'$")
    public void email(String user) 
    {
    	this.user = user;
    	System.out.println("El usuario del agente es " + user);
    }

    @Y("^su password inventada es \'([^\"]*)\'$")
    public void password(String password) 
    {
    	this.password = password;
    	System.out.println("La contraseña del agente es " + password);
    }

    @Y("^su tipo inventado es \'([^\"]*)\'$")
    public void tipo(String type) 
    {
    	this.type = type;
    	System.out.println("El tipo del agente es " + type);
    }

    @Cuando("^tratamos de logearnos con esas credenciales erroneas$")
    public void agente_introduce_datos_formulario() 
    {
    	assertTrue(agentsService.getAgent(user)==null);
    }

    @Entonces("^el agente no ingresa en el sistema al no estar registrado$")
    public void se_envia_la_peticion() 
    {
    	System.out.println("Usuario no logeado en el sistema");
    }
}
