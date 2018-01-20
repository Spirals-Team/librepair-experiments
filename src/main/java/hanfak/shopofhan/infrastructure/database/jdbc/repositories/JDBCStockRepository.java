package hanfak.shopofhan.infrastructure.database.jdbc.repositories;

import hanfak.shopofhan.application.crosscutting.StockRepository;
import hanfak.shopofhan.domain.ProductStock;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.product.ProductName;
import hanfak.shopofhan.domain.stock.Stock;
import hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.*;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.util.Optional;

import static hanfak.shopofhan.domain.ProductStock.productStock;
import static hanfak.shopofhan.domain.product.ProductName.productName;

// TODO Module test to test database is working
public class JDBCStockRepository implements StockRepository {

    private static final String SQL_STATEMENT = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product_name=?";
    private static final String SQL_STATEMENT_TWO = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?";
    private static final String SQL_INSERT_STOCK = "INSERT INTO stock (product_id, stock_id, stock_description, amount) VALUES (?, ?, ?, ?)";
    private final Logger logger;
    private final JdbcRecordReader<ProductId, ProductStock> productStockByProductIdReader;
    private final JdbcRecordReader<ProductName, ProductStock> productStockByProductNameReader;
    private final JdbcWriter<Stock> stockWriter;

    public JDBCStockRepository(Logger logger, JdbcRecordReaderFactory jdbcRecordReaderFactory, JdbcWriterFactory jdbcWriterFactory) {
        this.logger = logger;
        this.productStockByProductIdReader = jdbcRecordReaderFactory.create(SQL_STATEMENT_TWO, this::extractResultSet, this::setSelectByProductIdParameters, ProductStock.class);
        this.productStockByProductNameReader = jdbcRecordReaderFactory.create(SQL_STATEMENT, this::extractResultSet, this::setSelectByProductNameParameters, ProductStock.class);
        this.stockWriter = jdbcWriterFactory.create(SQL_INSERT_STOCK, this::setInsertParameters);
    }

    @Override
    public Optional<ProductStock> checkStockByName(ProductName productName) {
        return productStockByProductNameReader.readRecord(productName);
    }

    @Override
    public Optional<ProductStock> checkStockById(ProductId productId) {
        return productStockByProductIdReader.readRecord(productId);
    }

    @Override
    public void addStock(Stock stock) {
        stockWriter.write(stock);
    }

    protected ProductStock extractResultSet(EnhancedResultSet enhancedResultSet) throws SQLException {
        return productStock(
                productName(enhancedResultSet.getString("product_name")),
                enhancedResultSet.getInt("amount"));
    }

    public void setInsertParameters(EnhancedPreparedStatement enhancedPreparedStatement, Stock stock) throws SQLException {
        enhancedPreparedStatement.setString(stock.productId.value);
        enhancedPreparedStatement.setString(stock.stockDescription.value);
        enhancedPreparedStatement.setString(stock.stockId.value);
        enhancedPreparedStatement.setInt(stock.amount.value);
    }

    private void setSelectByProductIdParameters(EnhancedPreparedStatement enhancedPreparedStatement, ProductId productId) throws SQLException {
        enhancedPreparedStatement.setString(productId.value);
    }

    private void setSelectByProductNameParameters(EnhancedPreparedStatement enhancedPreparedStatement, ProductName productName) throws SQLException {
        enhancedPreparedStatement.setString(productName.value);
    }
}
