package hanfak.shopofhan.infrastructure.web.server;

import hanfak.shopofhan.infrastructure.web.createproduct.AddProductServlet;
import hanfak.shopofhan.infrastructure.web.createstock.AddStockServlet;
import hanfak.shopofhan.infrastructure.web.jetty.ShopOfHanServer;
import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdServlet;
import hanfak.shopofhan.infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameServlet;
import hanfak.shopofhan.infrastructure.web.productavailability.productstockcheckbyavailability.ProductStockCheckByIdServlet;
import hanfak.shopofhan.infrastructure.web.removeproduct.RemoveProductServlet;
import hanfak.shopofhan.infrastructure.web.statusprobeservlet.StatusProbeServlet;

public interface WebServerBuilder {
    WebServerBuilder registerProductAvailabilityByNameEndPoint(EndPoint endPoint, ProductAvailabilityByNameServlet productAvailabilityByNameServlet);
    WebServerBuilder registerProductAvailabilityByIdEndPoint(EndPoint endPoint, ProductAvailabilityByIdServlet productAvailabilityByIdServlet);
    WebServerBuilder registerStatusProbeEndPoint(EndPoint endPoint, StatusProbeServlet statusProbeServlet);
    WebServerBuilder registerproductStockCheckByIdEndPoint(EndPoint endPoint, ProductStockCheckByIdServlet productStockCheckByIdServlet);
    WebServerBuilder registerAddProductEndPoint(EndPoint endPoint, AddProductServlet addProductServlet);
    WebServerBuilder registerAddStockEndPoint(EndPoint endPoint, AddStockServlet addStockServlet);
    WebServerBuilder registerDeleteProductEndPoint(EndPoint endPoint, RemoveProductServlet removeProductServlet);

    ShopOfHanServer build();
}
