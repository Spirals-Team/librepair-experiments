package hanfak.shopofhan.wiring;

import hanfak.shopofhan.infrastructure.properties.Settings;
import hanfak.shopofhan.infrastructure.web.server.EndPoint;
import hanfak.shopofhan.infrastructure.web.server.WebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static hanfak.shopofhan.wiring.ShopOfHanURLs.*;

public class ShopOfHan {
    private final static Logger logger = LoggerFactory.getLogger(ShopOfHan.class);
    private static WebServer webserver;
    private static Wiring wiring = new Wiring();

    public static void main(String... arguments) throws Exception {
        new ShopOfHan().startWebServer(loadSettings(wiring), wiring);
    }

    public void startWebServer(Settings settings, Wiring wiring) throws Exception {
        logger.info("Starting Shop Of Han app");
        startServer(settings, wiring);
    }

    private void startServer(Settings settings, Wiring wiring) {
        webserver = wiring.webserverBuilder(settings)
                .registerProductAvailabilityByNameEndPoint(EndPoint.get(PRODUCT_AVAILABILITY_BY_NAME), wiring.productAvailabilityByNameServlet())
                .registerProductAvailabilityByIdEndPoint(EndPoint.get(PRODUCT_AVAILABILITY_BY_ID), wiring.productAvailabilityByIdServlet())
                .registerproductStockCheckByIdEndPoint(EndPoint.get(PRODUCT_STOCK_CHECK_BY_ID), wiring.productStockCheckByIdServlet())
                .registerAddProductEndPoint(EndPoint.post(PRODUCTS), wiring.addProductServlet())
                .registerAddStockEndPoint(EndPoint.post(STOCK), wiring.addStockServlet())
                .registerDeleteProductEndPoint(EndPoint.delete(PRODUCT), wiring.removeProductServlet())
                .registerStatusProbeEndPoint(EndPoint.get(STATUS_PAGE), wiring.statusProbeServlet())
                .build();
        webserver.start();
    }

    private static Settings loadSettings(Wiring wiring) {
        return wiring.settings();
    }

    // INFO: For testsing only
    public void stopWebServer() throws Exception {
        logger.info("Closing Shop Of Han app");
        webserver.stop();
    }
}
