package hanfak.shopofhan.infrastructure.database.jdbc.repositories;

import hanfak.shopofhan.application.crosscutting.ProductStockRepository;
import hanfak.shopofhan.domain.ProductStockList;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.product.ProductDescription;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.product.ProductName;
import hanfak.shopofhan.domain.stock.Stock;
import hanfak.shopofhan.infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hanfak.shopofhan.domain.ProductStockList.productStockList;
import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;
import static hanfak.shopofhan.domain.stock.Stock.stock;
import static hanfak.shopofhan.domain.stock.StockAmount.stockAmount;
import static hanfak.shopofhan.domain.stock.StockDescription.stockDescription;
import static hanfak.shopofhan.domain.stock.StockId.stockId;

// TODO Module test to test database is working
@SuppressWarnings("Duplicates")
public class JDBCProductStockRepository implements ProductStockRepository {

    private static final String SQL_STATEMENT_THREE = "SELECT product.product_name, product.product_id, product.product_description, stock.amount, stock.stock_id, stock.stock_description FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?";
    private final Logger logger;
    private final JDBCDatabaseConnectionManager databaseConnectionManager;

    // TODO extract a reader and inject, which handles the connection and making the call pass into reader.readProductStock the sql
    public JDBCProductStockRepository(Logger logger, JDBCDatabaseConnectionManager databaseConnectionManager) {
        this.logger = logger;
        this.databaseConnectionManager = databaseConnectionManager;
    }


    //TODO better method name
    @Override
    public Optional<ProductStockList> findListOfProductStock(ProductId productId) {
        return executeSql(productId.value, SQL_STATEMENT_THREE);
    }

    private Optional<ProductStockList> executeSql(String queryParameter, String sqlStatement) {
        // TODO tidy up
        try {
            Optional<Connection> connection = databaseConnectionManager.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get(); //TODO change to the above
                     PreparedStatement stmt = dbConnection.prepareStatement(sqlStatement)) {

                    Optional<ProductStockList> resultSet = lookUpAllStock(queryParameter, stmt);
                    if (resultSet.isPresent()) return resultSet;
                }
            }
        } catch (Exception e) {
            logger.error("error " + e);
        }
        return Optional.empty();
    }

    private Optional<ProductStockList> lookUpAllStock(String productId, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, productId);
        ResultSet resultSet = stmt.executeQuery();
        // TODO change nulls
        List<Stock> stock = new ArrayList<>();
        ProductName productName = null;
        ProductDescription productDescription = null;
        Optional<ProductId> productIdRetrieved = Optional.empty();
        while (resultSet.next()) {
            logger.info("Getting data from database and storing in java pojo"); // TODO test
            productName = productName(resultSet.getString("product_name"));
            productIdRetrieved = Optional.of(productId(resultSet.getString("product_id")));
            productDescription = productDescription(resultSet.getString("product_description"));
            stock.add(stock(stockAmount(resultSet.getInt("amount")),
                    stockId(resultSet.getString("stock_id")),
                    stockDescription(resultSet.getString("stock_description")),
                    productId(resultSet.getString("product_id"))));
        }

        resultSet.close();

        if (productIdRetrieved.isPresent()) {
            Product product = product(productDescription, productIdRetrieved.get(), productName);
            return Optional.of(productStockList(product, stock));
        }
        return Optional.empty();

    }
}
