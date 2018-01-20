package hanfak.shopofhan.infrastructure.web.productavailability;

import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameRequest;
import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameUnmarshaller;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductAvailabilityUnmarshallerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    public void pathParamsshouldIgnoreEverythingAfterSecondForwardSlash() throws Exception {
        when(request.getPathInfo()).thenReturn("/hello/bye");
        ProductAvailabilityByNameUnmarshaller productAvailabilityUnmarshaller = new ProductAvailabilityByNameUnmarshaller();

        ProductAvailabilityByNameRequest unmarshall = productAvailabilityUnmarshaller.unmarshall(request);

        assertThat(unmarshall).isEqualTo(ProductAvailabilityByNameRequest.productAvailabilityRequest("hello"));
    }
}