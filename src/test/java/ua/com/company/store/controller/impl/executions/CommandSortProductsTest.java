package ua.com.company.store.controller.impl.executions;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.company.store.model.entity.additional.ProductImage;
import ua.com.company.store.service.ProductImageService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Владислав on 10.01.2018.
 */
public class CommandSortProductsTest {
    private ProductImageService productService;
    private List<ProductImage> list;
    @Before
    public void setUp() throws Exception {
        productService = mock(ProductImageService.class);
        list = new ArrayList<>();
        list.add(new ProductImage(0, "bbb", "fewfwe", 123, "wefwe"));
        list.add(new ProductImage(1, "aaa", "fewfwe", 111, "wefwe"));
        list.add(new ProductImage(2, "ccc", "fewfwe", 134, "wefwe"));
        list.add(new ProductImage(3, "ddd", "fewfwe", 145, "wefwe"));

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void sortingProductsV1() throws Exception {


        when(productService.getAllProducts()).thenReturn(list);

        CommandSortProducts sortProducts = new CommandSortProducts(productService);
     List<ProductImage> sortedByCommand = sortProducts.sortProducts("1", productService.getAllProducts());
        Assert.assertEquals(sortedByCommand.get(0).getPrice(),111);

        List<ProductImage> sortedByCommandv2 = sortProducts.sortProducts("0", productService.getAllProducts());
        Assert.assertEquals(sortedByCommandv2.get(0).getPrice(),145);

    }
    @Test
    public void sortingProductsV2() throws Exception {


        when(productService.getAllProducts()).thenReturn(list);

        CommandSortProducts sortProducts = new CommandSortProducts(productService);


        List<ProductImage> sortedByCommandv2 = sortProducts.sortProducts("0", productService.getAllProducts());
        Assert.assertEquals(sortedByCommandv2.get(0).getPrice(),145);

    }



}