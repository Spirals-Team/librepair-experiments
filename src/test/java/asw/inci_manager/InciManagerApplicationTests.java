package asw.inci_manager;

import asw.InciManagerApplication;
import asw.inci_manager.inci_manager_gest.entities.Agent;
import asw.inci_manager.inci_manager_gest.entities.Incidence;
import asw.inci_manager.inci_manager_gest.services.AgentService;
import asw.inci_manager.inci_manager_gest.services.IncidenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InciManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InciManagerApplicationTests {

    @Autowired
    IncidenceService incidenceService;

    @Autowired
    AgentService agentService;

    @Test
    public void testAgentModel() {
        Agent paco = new Agent("Paco", "123456", "", "paco@gmail.com", "paco", "Person");
        Agent pepe = new Agent("pepe", "213456", "", "pepe@gmail.com", "pepe", "Person");

        assert paco.equals(paco);
        assert !paco.equals(null);
        assert !paco.equals(new Integer(1));
        assert paco.hashCode() == paco.hashCode();

        pepe.setPassword("pass");
        pepe.setEmail("pepe2@gmail.com");
        assert pepe.getEmail().equals("pepe2@gmail.com");

        // Test agente
        assert paco.getNombre().equals("Paco");
        assert paco.getPassword().equals("123456");
        assert paco.getLocation().equals("");
        assert paco.getIdent().equals("paco");
        assert paco.getKind().equals("Person");
        assert paco.getKindCode() == -1; // TODO: cambiar cuando esté implementada,la llamada REST
    }

    @Test
    public void testIncidenciaModel() {
        Agent paco = new Agent("Paco", "123456", "", "paco@gmail.com", "paco", "Person");

        Incidence i = new Incidence(paco, "incidencia 1", "descripción de la incidencia", "45.678, 12.896", new HashSet<String>());

        i.getAgent().setPassword("pass");
        i.getAgent().setEmail("paco@gmail.com");

        // Test agente dentro de la incidencia
        assert i.getAgent().getNombre().equals("Paco");
        assert i.getAgent().getPassword().equals("pass");
        assert i.getAgent().getLocation().equals("");
        assert i.getAgent().getIdent().equals("paco");
        assert i.getAgent().getKind().equals("Person");

        // Test campos incidencia
        assert i.getIncidenceName().equals("incidencia 1");
        assert i.getDescription().equals("descripción de la incidencia");
        assert i.getLocation().equals("45.678, 12.896");
        assert i.getLabels().equals("");
        assert i.getCampos() == null;
        assert i.getExpiration() == null;
    }

    @Test
    public void testListaIncidencias(){
        Agent pepe = agentService.getAgentByEmailFlexible("pepe@gmail.com");

        Set<Incidence> incidenceSet = incidenceService.getIncidencesFromAgent(pepe);
        assert !incidenceSet.isEmpty(); // Tiene incidencias porque InsertDataSample las carga.


    }

}
