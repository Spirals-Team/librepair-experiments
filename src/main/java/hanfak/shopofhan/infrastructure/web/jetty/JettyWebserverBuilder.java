package hanfak.shopofhan.infrastructure.web.jetty;

import hanfak.shopofhan.infrastructure.properties.Settings;
import hanfak.shopofhan.infrastructure.web.createproduct.AddProductServlet;
import hanfak.shopofhan.infrastructure.web.createstock.AddStockServlet;
import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdServlet;
import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameServlet;
import hanfak.shopofhan.infrastructure.web.productavailability.productstockcheckbyavailability.ProductStockCheckByIdServlet;
import hanfak.shopofhan.infrastructure.web.removeproduct.RemoveProductServlet;
import hanfak.shopofhan.infrastructure.web.server.EndPoint;
import hanfak.shopofhan.infrastructure.web.server.WebServerBuilder;
import hanfak.shopofhan.infrastructure.web.statusprobeservlet.StatusProbeServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.zalando.logbook.DefaultHttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.servlet.LogbookFilter;

import javax.servlet.http.HttpServlet;
import java.util.EnumSet;

import static javax.servlet.DispatcherType.ASYNC;
import static javax.servlet.DispatcherType.ERROR;
import static javax.servlet.DispatcherType.REQUEST;
import static org.slf4j.LoggerFactory.getLogger;
import static org.zalando.logbook.DefaultHttpLogWriter.Level.INFO;

public class JettyWebserverBuilder implements WebServerBuilder {
    private final Settings settings;
    private final ServletContextHandler servletHandler = new ServletContextHandler();

    public JettyWebserverBuilder(Settings settings) {
        this.settings = settings;
    }

    @Override
    public WebServerBuilder registerProductAvailabilityByNameEndPoint(EndPoint endPoint, ProductAvailabilityByNameServlet productAvailabilityByNameServlet) {
        addServlet(productAvailabilityByNameServlet, endPoint);
        return this;
    }

    @Override
    public WebServerBuilder registerProductAvailabilityByIdEndPoint(EndPoint endPoint, ProductAvailabilityByIdServlet productAvailabilityByIdServlet) {
        addServlet(productAvailabilityByIdServlet, endPoint);
        return this;
    }

    @Override
    public WebServerBuilder registerStatusProbeEndPoint(EndPoint endPoint, StatusProbeServlet statusProbeServlet) {
        addServlet(statusProbeServlet, endPoint);
        return this;
    }

    @Override
    public WebServerBuilder registerproductStockCheckByIdEndPoint(EndPoint endPoint, ProductStockCheckByIdServlet productStockCheckByIdServlet) {
        addServlet(productStockCheckByIdServlet, endPoint);
        return this;
    }

    @Override
    public WebServerBuilder registerAddProductEndPoint(EndPoint endPoint, AddProductServlet addProductServlet) {
        addServlet(addProductServlet, endPoint);
        return this;
    }

    @Override
    public WebServerBuilder registerAddStockEndPoint(EndPoint endPoint, AddStockServlet addStockServlet) {
        addServlet(addStockServlet, endPoint);
        return this;
    }

    @Override
    public WebServerBuilder registerDeleteProductEndPoint(EndPoint endPoint, RemoveProductServlet removeProductServlet) {
        addServlet(removeProductServlet, endPoint);
        return this;
    }

    @Override
    public ShopOfHanServer build() {
        addLoggingFilter();
        return new ShopOfHanServer(settings).withContext(servletHandler);
    }

    private void addServlet(HttpServlet httpServlet, EndPoint endPoint) {
        servletHandler.addServlet(new ServletHolder(httpServlet), endPoint.path);
    }

    private void addLoggingFilter() {
        Logbook logbook = Logbook.builder()
                .writer(new DefaultHttpLogWriter(getLogger(JettyWebserverBuilder.class), INFO))
                .build();
        FilterHolder filterHolder = new FilterHolder(new LogbookFilter(logbook));
        servletHandler.addFilter(filterHolder, "/*", EnumSet.of(REQUEST, ASYNC, ERROR));
    }
}
