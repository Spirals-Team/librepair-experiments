package testinfrastructure;

import hanfak.shopofhan.application.crosscutting.ProductRepository;
import hanfak.shopofhan.application.crosscutting.ProductStockRepository;
import hanfak.shopofhan.application.crosscutting.StockRepository;
import hanfak.shopofhan.infrastructure.properties.PropertiesReader;
import hanfak.shopofhan.infrastructure.properties.Settings;
import hanfak.shopofhan.wiring.Wiring;


// interface for both hanfak.shopofhan.wiring and TestWiring
public class TestWiring extends Wiring {
    public static final String ENVIRONMENT = "test";
    // TODO singleton pattern

    @Override
    public Settings settings() {
        return new Settings(new PropertiesReader(ENVIRONMENT));
    }

    @Override
    public StockRepository stockRepository() {
        return new TestStockRepository();
    }

    @Override
    public ProductStockRepository productStockRepository() {
        return new TestProductStockRepository();
    }

    @Override
    public ProductRepository productRepository() {
        return new TestProductRepository();
    }
}
