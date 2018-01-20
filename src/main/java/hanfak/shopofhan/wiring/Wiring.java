package hanfak.shopofhan.wiring;

import hanfak.shopofhan.application.createproduct.AddProductUseCase;
import hanfak.shopofhan.application.createstock.AddStockUseCase;
import hanfak.shopofhan.application.crosscutting.ProductRepository;
import hanfak.shopofhan.application.crosscutting.ProductStockRepository;
import hanfak.shopofhan.application.crosscutting.StockRepository;
import hanfak.shopofhan.application.productavailability.ProductCheckByIdUseCase;
import hanfak.shopofhan.application.productavailability.ProductCheckByNameUseCase;
import hanfak.shopofhan.application.productavailability.ProductStockCheckByIdUseCase;
import hanfak.shopofhan.infrastructure.database.connection.MySqlJDBCDatabaseConnectionManager;
import hanfak.shopofhan.infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.JdbcRecordReaderFactory;
import hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.JdbcWriterFactory;
import hanfak.shopofhan.infrastructure.database.jdbc.repositories.JDBCProductRepository;
import hanfak.shopofhan.infrastructure.database.jdbc.repositories.JDBCProductStockRepository;
import hanfak.shopofhan.infrastructure.database.jdbc.repositories.JDBCStockRepository;
import hanfak.shopofhan.infrastructure.monitoring.DatabaseConnectionProbe;
import hanfak.shopofhan.infrastructure.properties.PropertiesReader;
import hanfak.shopofhan.infrastructure.properties.Settings;
import hanfak.shopofhan.infrastructure.web.Marshaller;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;
import hanfak.shopofhan.infrastructure.web.createproduct.AddProductServlet;
import hanfak.shopofhan.infrastructure.web.createproduct.AddProductUnmarshaller;
import hanfak.shopofhan.infrastructure.web.createproduct.AddProductWebService;
import hanfak.shopofhan.infrastructure.web.createstock.AddStockServlet;
import hanfak.shopofhan.infrastructure.web.createstock.AddStockUnmarshaller;
import hanfak.shopofhan.infrastructure.web.createstock.AddStockWebService;
import hanfak.shopofhan.infrastructure.web.jetty.JettyWebserverBuilder;
import hanfak.shopofhan.infrastructure.web.productavailability.ProductAvailabilityMarshaller;
import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdServlet;
import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdUnmarshaller;
import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdWebService;
import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameServlet;
import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameUnmarshaller;
import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameWebService;
import hanfak.shopofhan.infrastructure.web.productavailability.productstockcheckbyavailability.ProductStockCheckByIdMarshaller;
import hanfak.shopofhan.infrastructure.web.productavailability.productstockcheckbyavailability.ProductStockCheckByIdServlet;
import hanfak.shopofhan.infrastructure.web.productavailability.productstockcheckbyavailability.ProductStockCheckByIdUnmarshaller;
import hanfak.shopofhan.infrastructure.web.productavailability.productstockcheckbyavailability.ProductStockCheckByIdWebService;
import hanfak.shopofhan.infrastructure.web.removeproduct.RemoveProductServlet;
import hanfak.shopofhan.infrastructure.web.server.WebServerBuilder;
import hanfak.shopofhan.infrastructure.web.statusprobeservlet.StatusProbeServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO find way of not making all methods public
// TODO make singleton
@SuppressWarnings("UseUtilityClass")
public class Wiring {

    public static final String ENVIRONMENT = "localhost";

    // TODO singleton pattern
    public Settings settings() {
        return new Settings(new PropertiesReader(ENVIRONMENT));
    }

    // TODO Extract to separate hanfak.shopofhan.wiring for database
    public JDBCDatabaseConnectionManager databaseConnectionManager() {
        return new MySqlJDBCDatabaseConnectionManager(settings(), logger(MySqlJDBCDatabaseConnectionManager.class));
//        return new PoolingJDBCDatabasConnectionManager(logger(PoolingJDBCDatabasConnectionManager.class));
    }

    // TODO singleton pattern
    public Logger logger(Class<?> cls) {
        return LoggerFactory.getLogger(cls);
    }

    private Unmarshaller productAvailabilityByNameUnmarshaller() {
        return new ProductAvailabilityByNameUnmarshaller();
    }

    private Unmarshaller productAvailabilityByIdUnmarshaller() {
        return new ProductAvailabilityByIdUnmarshaller(logger(ProductAvailabilityByIdUnmarshaller.class));
    }

    private Marshaller productAvailabilityMarshaller() {
        return new ProductAvailabilityMarshaller();
    }

    private Marshaller productAvailabilityByIdMarshaller() {
        return new ProductAvailabilityMarshaller();
    }

    public StockRepository stockRepository() {
        return new JDBCStockRepository(logger(JDBCStockRepository.class), new JdbcRecordReaderFactory(logger(JdbcRecordReaderFactory.class), databaseConnectionManager()), new JdbcWriterFactory(logger(JdbcWriterFactory.class), databaseConnectionManager()));
    }

    public ProductStockRepository productStockRepository() {
        return new JDBCProductStockRepository(logger(JDBCProductStockRepository.class), databaseConnectionManager());
    }

    private DatabaseConnectionProbe databaseConnectionProbe() {
        return new DatabaseConnectionProbe(logger(DatabaseConnectionProbe.class), settings(), databaseConnectionManager());
    }

    StatusProbeServlet statusProbeServlet() {
        return new StatusProbeServlet(databaseConnectionProbe());
    }

    private ProductCheckByNameUseCase productCheckByNameUseCase() {
        return new ProductCheckByNameUseCase(stockRepository(), logger(ProductCheckByNameUseCase.class));
    }

    private ProductCheckByIdUseCase productCheckByIdUseCase() {
        return new ProductCheckByIdUseCase(stockRepository(), logger(ProductCheckByIdUseCase.class));
    }

    ProductAvailabilityByNameServlet productAvailabilityByNameServlet() {
        return new ProductAvailabilityByNameServlet(productAvailabilityByNameUnmarshaller(), productAvailabilityByNameWebService());
    }

    private ProductAvailabilityByNameWebService productAvailabilityByNameWebService() {
        return new ProductAvailabilityByNameWebService(productCheckByNameUseCase(), productAvailabilityMarshaller());
    }

    ProductAvailabilityByIdServlet productAvailabilityByIdServlet() {
        return new ProductAvailabilityByIdServlet(productAvailabilityByIdUnmarshaller(), productAvailabilityByIdWebService());
    }

    private ProductAvailabilityByIdWebService productAvailabilityByIdWebService() {
        return new ProductAvailabilityByIdWebService(productCheckByIdUseCase(), productAvailabilityByIdMarshaller());
    }

    WebServerBuilder webserverBuilder(Settings settings) {
        return new JettyWebserverBuilder(settings);
    }

    public ProductStockCheckByIdServlet productStockCheckByIdServlet() {
        return new ProductStockCheckByIdServlet(new ProductStockCheckByIdUnmarshaller(), new ProductStockCheckByIdWebService(new ProductStockCheckByIdMarshaller(), new ProductStockCheckByIdUseCase(productStockRepository(), logger(ProductStockCheckByIdUseCase.class))));
    }

    public AddProductServlet addProductServlet() {
        return new AddProductServlet(new AddProductUnmarshaller(), new AddProductWebService(new AddProductUseCase(productRepository(), logger(AddProductUseCase.class))));
    }

    public ProductRepository productRepository() {
        return new JDBCProductRepository(logger(JDBCProductRepository.class), databaseConnectionManager());
    }

    public AddStockServlet addStockServlet() {
        return new AddStockServlet(new AddStockUnmarshaller(), new AddStockWebService(new AddStockUseCase(productRepository(), stockRepository())));
    }

    public RemoveProductServlet removeProductServlet() {
        return new RemoveProductServlet();
    }
}
