package hanfak.shopofhan.infrastructure.web.productavailabilityById;

import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdUnmarshaller;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductAvailabilityByIdUnmarshallerTest implements WithAssertions {

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private Logger logger = mock(Logger.class);

    @Test
    public void logsErrorIfUnmarshallingFails() throws Exception {
        when(request.getPathInfo()).thenReturn("/tooLongAProductIDToBeFeasible");
        ProductAvailabilityByIdUnmarshaller unmarshaller = new ProductAvailabilityByIdUnmarshaller(logger);

        Throwable thrown = catchThrowable(() -> {  unmarshaller.unmarshall(request); });
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);

        Mockito.verify(logger).error("Bad product id argument");
    }
}