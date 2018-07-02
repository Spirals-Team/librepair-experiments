package br.com.igorventurelli.mercadolivre.api;

import br.com.igorventurelli.mercadolivre.api.impl.MercadoLivreAPIImpl;
import br.com.igorventurelli.mercadolivre.domain.AppInformation;
import br.com.igorventurelli.mercadolivre.http.HttpRequest;
import br.com.igorventurelli.mercadolivre.util.Whitebox;
import com.ning.http.client.Response;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MercadoLivreAPIImplTest {

    @Mock
    private HttpRequest http;

    @Mock
    private AppInformation appInformation = new AppInformation(1L, "secret");

    @InjectMocks
    private MercadoLivreAPIImpl api = new MercadoLivreAPIImpl(appInformation);

    @BeforeClass
    public static void setupClass() {
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure();
    }

    @Before
    public void setup() {
        final Response response = mock(Response.class);
        when(http.post(anyString(), any())).thenReturn(response);
        Whitebox.setInternalState(api, "http", http);
    }

    @Test
    public void shouldAuthorize() {
        api.authorize(anyString(), anyString());
    }
}
