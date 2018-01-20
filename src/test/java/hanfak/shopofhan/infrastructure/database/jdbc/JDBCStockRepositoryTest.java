package hanfak.shopofhan.infrastructure.database.jdbc;

import hanfak.shopofhan.domain.ProductStock;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.JdbcRecordReaderFactory;
import hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.JdbcWriterFactory;
import hanfak.shopofhan.infrastructure.database.jdbc.repositories.JDBCStockRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static hanfak.shopofhan.domain.product.ProductName.productName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JDBCStockRepositoryTest {

    // TODO tested by acceptance test
    @Test
    @Ignore
    public void returnProductById() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement(QUERY_PRODUCT_ID)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getString("product_name")).thenReturn("A Product");
        when(resultSet.getInt("amount")).thenReturn(5);
        when(resultSet.next()).thenReturn(true);

        Optional<ProductStock> result = jdbcStockRepository.checkStockById(ProductId.productId("abc1"));

        assertThat(result.get()).isEqualTo(ProductStock.productStock(productName("A Product"), 5));
    }

    // TODO tested by acceptance test
    @Test
    @Ignore
    public void returnNoProductByIdIfNothingInTheDatabase() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement(QUERY_PRODUCT_ID)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<ProductStock> result = jdbcStockRepository.checkStockById(ProductId.productId("abc1"));

        assertThat(result).isEqualTo(Optional.empty());
    }
    // TODO tested by acceptance test

    @Test
    @Ignore
    public void returnProductByName() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement(QUERY_PRODUCT_NAME)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getString("product_name")).thenReturn("A Product");
        when(resultSet.getInt("amount")).thenReturn(5);
        when(resultSet.next()).thenReturn(true);

        Optional<ProductStock> result = jdbcStockRepository.checkStockByName(productName("A product name"));

        assertThat(result.get()).isEqualTo(ProductStock.productStock(productName("A Product"), 5));
    }
    // TODO tested by acceptance test

    @Test
    @Ignore
    public void returnNoProductByNameIfNothingInTheDatabase() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement(QUERY_PRODUCT_NAME)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<ProductStock> result = jdbcStockRepository.checkStockByName(productName("A product name"));

        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    @Ignore
    public void throwDatabaseErrorForName() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement("sql statement")).thenThrow(new SQLException());

        jdbcStockRepository.checkStockByName(productName("A product name"));

        verify(logger).error("error java.lang.NullPointerException");
    }

    // TODO tested by recordReader test
    @Test
    @Ignore
    public void throwDatabaseErrorForId() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement("sql statement")).thenThrow(new SQLException());


        jdbcStockRepository.checkStockById(ProductId.productId("abc1"));

        verify(logger).error("error java.lang.NullPointerException");
    }

    private static final String QUERY_PRODUCT_ID = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?";
    private static final String QUERY_PRODUCT_NAME = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product_name=?";

    private final Logger logger = mock(Logger.class);
    private final JDBCDatabaseConnectionManager databaseConnectionManager = mock(JDBCDatabaseConnectionManager.class);
    private final Connection connection = mock(Connection.class);
    private final PreparedStatement statement = mock(PreparedStatement.class);
    private final ResultSet resultSet = mock(ResultSet.class);
//    private final JdbcRecordReader jdbcRecordReader = mock
    private final JdbcRecordReaderFactory jdbcRecordReaderFactory = mock(JdbcRecordReaderFactory.class);
    private final JdbcWriterFactory jdbcWriterFactory = mock(JdbcWriterFactory.class);
    private final JDBCStockRepository jdbcStockRepository = new JDBCStockRepository(logger, jdbcRecordReaderFactory, jdbcWriterFactory);
}