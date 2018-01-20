package hanfak.shopofhan.infrastructure.database.jdbc;

import hanfak.shopofhan.domain.ProductStockList;
import hanfak.shopofhan.domain.stock.Stock;
import hanfak.shopofhan.infrastructure.database.jdbc.repositories.JDBCProductStockRepository;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JDBCProductStockRepositoryTest implements WithAssertions {

    @Test
    public void returnProductStockById() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement(QUERY)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getString("product_name")).thenReturn("A Product");
        when(resultSet.getString("product_description")).thenReturn("blah");
        when(resultSet.getString("product_id")).thenReturn("abc1");
        when(resultSet.getInt("amount")).thenReturn(5);
        when(resultSet.getString("stock_description")).thenReturn("stock description");
        when(resultSet.getString("stock_id")).thenReturn("SID");
        when(resultSet.next()).thenReturn(true, false);

        Optional<ProductStockList> result = jdbcProductStockRepository.findListOfProductStock(productId("abc1"));

        List<Stock> stock = new ArrayList<>();
        stock.add((stock(stockAmount(5), stockId("SID"), stockDescription("stock description"), productId("abc1"))));
        ProductStockList expectedResult = productStockList(product(productDescription("blah"), productId("abc1"), productName("A Product")), stock);
        assertThat(result.get()).isEqualTo(expectedResult);
    }

    @Test
    public void throwDatabaseErrorForProductAndStockById() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement("sql statement")).thenThrow(new RuntimeException());


        jdbcProductStockRepository.findListOfProductStock(productId("abc1"));

        verify(logger).error("error java.lang.NullPointerException");
    }

    @Test
    public void returnNoProductStockByIdIfNothingInTheDatabase() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement(QUERY)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<ProductStockList> result = jdbcProductStockRepository.findListOfProductStock(productId("abc1"));;

        assertThat(result).isEqualTo(Optional.empty());
    }

    private static final String QUERY = "SELECT product.product_name, product.product_id, product.product_description, stock.amount, stock.stock_id, stock.stock_description FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?";

    private final Logger logger = mock(Logger.class);
    private final JDBCDatabaseConnectionManager databaseConnectionManager = mock(JDBCDatabaseConnectionManager.class);
    private final Connection connection = mock(Connection.class);
    private final PreparedStatement statement = mock(PreparedStatement.class);
    private final ResultSet resultSet = mock(ResultSet.class);
    private final JDBCProductStockRepository jdbcProductStockRepository = new JDBCProductStockRepository(logger, databaseConnectionManager);

}