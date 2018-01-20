package hanfak.shopofhan.domain.product;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class ProductNameTest implements WithAssertions{
    @Test
    public void validateNameIsLessThan4Characters() throws Exception {
        Throwable thrown = catchThrowable(() -> {  ProductName.productName("abc"); });
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void productNameGreaterThan3Characters() throws Exception {
        ProductName validProductName = ProductName.productName("abcdefgh");

        assertThat(validProductName).isNotInstanceOf(IllegalArgumentException.class);
        assertThat(validProductName).isInstanceOf(ProductName.class);

    }
}