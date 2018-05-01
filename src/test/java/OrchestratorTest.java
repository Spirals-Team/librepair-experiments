import model.MailSender;
import model.Orchestrator;
import org.junit.Assert;
import org.junit.Test;

public class OrchestratorTest {

//    @Test
//    public void userAddOneVehicleTest(){
//
//    }
    @Test
    public void orchestratorConstructorTest(){
        Orchestrator aux = new Orchestrator();

        Assert.assertTrue(aux.getUserSystem().isEmpty() && aux.getRentalSystem().isEmpty() && aux.getTransactionsSystem().isEmpty() && aux.getSendMail().getClass().equals(MailSender.class));

    }
}
