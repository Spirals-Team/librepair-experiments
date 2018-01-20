package testinfrastructure;

import hanfak.shopofhan.application.crosscutting.ProductStockRepository;
import hanfak.shopofhan.domain.ProductStockList;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.stock.Stock;
import hanfak.shopofhan.domain.stock.StockDescription;
import hanfak.shopofhan.domain.stock.StockId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static hanfak.shopofhan.domain.ProductStockList.productStockList;
import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;
import static hanfak.shopofhan.domain.stock.Stock.stock;
import static hanfak.shopofhan.domain.stock.StockAmount.stockAmount;
import static hanfak.shopofhan.domain.stock.StockId.stockId;
import static java.util.Arrays.asList;

public class TestProductStockRepository implements ProductStockRepository {

    @SuppressWarnings("WeakerAccess")
    public TestProductStockRepository() {
        populateProductStockLists();
    }

    @Override
    public Optional<ProductStockList> findListOfProductStock(ProductId productId) {
        System.out.println("productId = 5234523452345" + productId);
        return productStockLists.stream()
                .filter(doesProductStockHaveProductId(productId))
                .findFirst();
    }

    private Predicate<ProductStockList> doesProductStockHaveProductId(ProductId productId) {
        return productStockList -> productStockList.product.productId.equals(productId);
    }

    private void populateProductStockLists() {
        productStockLists.add(JOY_OF_JAVA);
        productStockLists.add(SQL_THE_SEQUEL);
    }

    private static final List<ProductStockList> productStockLists = new ArrayList<>();
    private static final List<Stock> JOY_OF_JAVA_STOCK = new ArrayList<>(asList(stock(stockAmount(4), stockId("STD1"), StockDescription.stockDescription("Single Pack"),null)));
    private static final Product  JOY_OF_JAVA_PRODUCT = product(productDescription("Book about java"), productId("JOJ1"), productName("Joy Of Java"));
    private static final ProductStockList JOY_OF_JAVA = productStockList(JOY_OF_JAVA_PRODUCT, JOY_OF_JAVA_STOCK);
    private static final List<Stock> SQL_THE_SEQUEL_STOCK = new ArrayList<>(asList(stock(stockAmount(0), stockId("STD1"), StockDescription.stockDescription("Single Pack"),null), stock(stockAmount(3), StockId.stockId("STD2"), StockDescription.stockDescription("Multi Pack"),null)));
    private static final Product  SQL_THE_SEQUEL_PRODUCT = product(productDescription("Book about SQL"), productId("STS1"), productName("SQL then sequel"));
    private static final ProductStockList SQL_THE_SEQUEL = productStockList(SQL_THE_SEQUEL_PRODUCT, SQL_THE_SEQUEL_STOCK);
}
