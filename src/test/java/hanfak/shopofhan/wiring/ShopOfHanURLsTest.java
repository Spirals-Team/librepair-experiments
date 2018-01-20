package hanfak.shopofhan.wiring;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static hanfak.shopofhan.wiring.ShopOfHanURLs.*;
import static java.util.Arrays.stream;

@RunWith(TableRunner.class)
public class ShopOfHanURLsTest implements WithAssertions {
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_NAME = "/produc" + "tscheck/n" + "ame/*";
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_ID = "/produc" + "tsch" + "eck/i" + "d/*";
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_STOCK_CHECK_BY_ID = "/fullProd" + "uctStockCheck/id/*";
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_STATUS_PAGE = "/stat" + "us";
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_ADD_PRODUCT = "/pro" + "ducts";
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_ADD_STOCK = "/st" + "ock";
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_DELETE_PRODUCT = "/prod" + "uc" +"t/id/*";

    @Table({
            @Row({PRODUCT_AVAILABILITY_BY_NAME, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_NAME}),
            @Row({PRODUCT_AVAILABILITY_BY_ID, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_ID}),
            @Row({PRODUCT_STOCK_CHECK_BY_ID, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_STOCK_CHECK_BY_ID}),
            @Row({STATUS_PAGE, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_STATUS_PAGE}),
            @Row({PRODUCTS, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_ADD_PRODUCT}),
            @Row({PRODUCT, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_DELETE_PRODUCT}),
            @Row({STOCK, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_ADD_STOCK})
    })
    @Test
    public void externalUrlsShouldNotChangeByAccident(String actual, String expected) {
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void blowUpIfAddANewUrlToEnsureItsEndpointIsTestedHere() throws Exception {
        assertThat(cerberusEndpointUrls().size()).isEqualTo(ShopOfHanURLsTest.class.getDeclaredFields().length);
    }

    private List<Field> cerberusEndpointUrls() {
        return stream(ShopOfHanURLs.class.getDeclaredFields()).collect(Collectors.toList());
    }
}