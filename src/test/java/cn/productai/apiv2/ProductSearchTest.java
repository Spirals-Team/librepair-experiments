package cn.productai.apiv2;

import cn.productai.api.core.DefaultProfile;
import cn.productai.api.core.IProfile;
import cn.productai.apiv2.impl.ProductSearchImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductSearchTest {

    private static IProfile profile = new DefaultProfile();
    private static ProductSearch productSearch = new ProductSearchImpl();

    @BeforeAll
    static void initAll() {
        profile.setAccessKeyId(System.getenv("X_CA_ACCESS_KEY_ID"));
        productSearch.setProfile(profile);
    }

    @Test
    @DisplayName("Service query")
    void testQuery() {
        String serviceId = "70vr47ka";
        String imageUrl = "https://styleai-shopping.oss-cn-beijing.aliyuncs.com/14d1b1e8a38ae767cc67834799be5ef9ed4595b9.jpg";
        String result = productSearch.query(serviceId, imageUrl,
                null, null, null, null, null, null,
                null);
        System.out.println("query");
        System.out.println(result);
    }

    @Test
    @DisplayName("List all services")
    void testListAllService() {
        String result = productSearch.listAllService();
        System.out.println("List services");
        System.out.println(result);
    }

    @Test
    @DisplayName("Create service")
    void testCreateService() {
        String productSetId = "0ingz90g";
        String result = productSearch.createService("Name", "general", productSetId);
        System.out.println("Create service");
        System.out.println(result);
    }

    @Test
    @DisplayName("Get service by Id")
    void testGetServiceById() {
        String productSetId = "0ingz90g";
        String result = productSearch.getServiceById(productSetId);
        System.out.println("Get service by Id");
        System.out.println(result);
    }

    @Test
    @DisplayName("Update service")
    void testUpdateService() {
        String productSetId = "0ingz90g";
        String result = productSearch.updateServiceName(productSetId, "new name");
        System.out.println("Update service");
        System.out.println(result);
    }

    @Test
    @DisplayName("Delete service")
    void testDeleteService() {
        String productSetId = "0ingz90g";
        String result = productSearch.deleteServiceById(productSetId);
        System.out.println("Delete service");
        System.out.println(result);
    }
}
