package me.pagarme;

import me.pagar.model.PagarMe;
import me.pagar.model.PagarMeException;
import me.pagar.model.RestClient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RestClientTest extends BaseTest {
    private final String SUCCESS_MESSAGE = "Sucesso: sua conexão com a Pagar.me está utilizando o protocolo TLS 1.2.";
    private RestClient client = null;

    @Before
    public void beforeEach() throws PagarMeException {
        PagarMe.init("");
        client = new RestClient("GET", "https://tls12.pagar.me");
    }

    @Test
    public void testErrorOnTLS12Request () throws PagarMeException {
        Exception error = new Exception();
        System.setProperty("https.protocols", "TLSv1.1");

        try {
            client.execute();
        } catch (PagarMeException e) {
            error = e;
        }
        
        Assert.assertNotEquals(SUCCESS_MESSAGE, error.getMessage());
    }
    
    @Test
    public void testSuccessOnTLS12Request () throws PagarMeException {
        Exception error = new Exception();

        try {
            client.execute();
        } catch (PagarMeException e) {
            error = e;
        }

        Assert.assertTrue(error.getMessage().contains(SUCCESS_MESSAGE));
    }
}
