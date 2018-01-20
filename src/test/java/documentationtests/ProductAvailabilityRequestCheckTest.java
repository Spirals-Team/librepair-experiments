//package documentationtests;
//
//import hanfak.shopofhan.application.ProductCheckUseCase;
//import com.googlecode.yatspec.junit.SpecRunner;
//import hanfak.shopofhan.domain.ProductName;
//import hanfak.shopofhan.domain.ProductStock;
//import hanfak.shopofhan.application.crosscutting.ProductToCheck;
//import hanfak.shopofhan.application.crosscutting.StockRepository;
//import hanfak.shopofhan.infrastructure.web.productavailability.ProductAvailabilityRequest;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.ThrowableAssert.catchThrowable;
//import static org.mockito.Mockito.*;
//
//@RunWith(SpecRunner.class)
//public class ProductAvailabilityRequestCheckTest {
//
//    private Throwable thrown;
//
//    @Test
//    public void shouldReturnStockAmountForItem() throws Exception {
//        givenStockRepositoryContainsTheProduct(LORD_OF_THE_RINGS, withStock(5));
//        whenCheckingStockOfProduct(requestOne);
//        thenLogThatTheProductWasCheckedAndFound();
//        andTheProducHasAmountOfStockOf(5, forProduct(LORD_OF_THE_RINGS));
//    }
//
//    @Test
//    public void shouldReturnZeroStockAmountForItem() throws Exception {
//        givenStockRepositoryContainsTheProduct(CATCH_22, withStock(0));
//        whenCheckingStockOfProduct(requestTwo);
//        thenLogThatTheProductWasCheckedAndFound();
//        andTheProducHasAmountOfStockOf(0, forProduct(CATCH_22));
//    }
//
//    @Test
//    public void shouldThrowErrorIfProductIsNotStocker() {
//        givenSTockRepositoryDoesNotContainTheProduct(FIFTY_SHADES);
//        whencheckingProducThatIsNotInStock();
//        thenLogThatTheProductWasNotFound();
//        andAnErrorIsThrown();
//    }
//
//    private void whencheckingProducThatIsNotInStock() {
//        thrown = catchThrowable(() -> { whenCheckingStockOfProduct(requestThree); });
//    }
//
//    private void andAnErrorIsThrown() {
//        assertThat(thrown).isInstanceOf(IllegalStateException.class)
//                .hasMessage("Product is not found");
//    }
//
//    private void givenSTockRepositoryDoesNotContainTheProduct(String product) {
//        when(stockRepository.checkStockByName(product)).thenReturn(Optional.empty());
//    }
//
//    private Integer withStock(int stock) {
//        return stock;
//    }
//
//    private String forProduct(String product) {
//        return product;
//    }
//
//    private void andTheProducHasAmountOfStockOf(int expectedAmount, String product) {
//        assertThat(checkedStock.productName).isEqualTo(product);
//        assertThat(checkedStock.amountInStock).isEqualTo(expectedAmount);
//    }
//
//    private void thenLogThatTheProductWasCheckedAndFound() {
//        verify(logger).info("checking stock...");
//        verify(logger).info("Stock is there");
//    }
//
//    private void thenLogThatTheProductWasNotFound() {
//        verify(logger).warn("Stock not there");
//    }
//
//    private void whenCheckingStockOfProduct(ProductToCheck product) {
//        checkedStock = productCheckUseCase.checkStock(product);
//    }
//
//    private void givenStockRepositoryContainsTheProduct(String product, Integer amount) {
//        when(stockRepository.checkStockByName(product)).thenReturn(Optional.of(ProductStock.productStock(ProductName.productName(product), amount)));
//    }
//
//    private static final String LORD_OF_THE_RINGS = "Lord Of The Rings";
//    private static final String CATCH_22 = "Catch-22";
//    private static final String FIFTY_SHADES = "50 Shades";
//    private static final ProductName requestOne = ProductAvailabilityRequest.productAvailabilityRequest(ProductName.productName(LORD_OF_THE_RINGS));
//    private static final ProductName requestTwo = ProductAvailabilityRequest.productAvailabilityRequest(CATCH_22);
//    private static final ProductName requestThree = ProductAvailabilityRequest.productAvailabilityRequest(FIFTY_SHADES);
//
//    private final StockRepository stockRepository = mock(StockRepository.class);
//    private final Logger logger = mock(Logger.class);
//    private ProductCheckUseCase productCheckUseCase = new ProductCheckUseCase(stockRepository, logger);
//    private ProductStock checkedStock;
//}
//
////@After
////public void teardown() {
////    logger.
////}
