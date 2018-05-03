package tests_cucumber.steps;

import java.util.List;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dada;
import cucumber.api.java.es.Entonces;
import inciDashboard_e5a.model.User;

public class VerIncidenciasSteps {
    
    @Dada("<Agente logueado en el sistema$")
//    @Dada("^la siguiente lista de usuarios:$")
    public void una_lista_de_usuarios(List<User> usuarios) throws Throwable {
	for (User u : usuarios)
	    System.out.println(u.getEmail());
    }
/*
    @Cuando("^introduzco el email \"(.+)\" y contraseña \"(.+)\"$")
    public void introduzco_el_email_y_contrasena(String email, String contrasena) throws Throwable {
	System.out.println("Logueado usuario con email " + email);
    }

    @Entonces("^puedo entrar al sistema y se muestra el mensaje de bienvenida")
    public void puedo_entrar_al_sistema() throws Throwable {
	System.out.println("Mensaje de bienvenida mostrado");
    }
*/ 
//	Cuando el agente esta en el dashboard
//	Entonces puede ver sus incidencias
    
}
