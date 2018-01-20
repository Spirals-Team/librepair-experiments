package ua.com.company.store.controller.impl.executions;

import ua.com.company.store.constants.Redirection;
import ua.com.company.store.controller.command.CommandTypical;
import ua.com.company.store.model.entity.additional.ProductImage;
import ua.com.company.store.service.ProductImageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Владислав on 10.01.2018.
 */
public class CommandSortProducts implements CommandTypical {
    private ProductImageService productService;

    public CommandSortProducts(ProductImageService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String versionOfSort = req.getParameter("v");
        List<ProductImage> productsBefore = productService.getAllProducts();
        List productsAfter = sortProducts(versionOfSort, productsBefore);
        req.setAttribute("list", productsAfter);


        return Redirection.MAIN_PAGE;
    }

    List sortProducts(String param, List<ProductImage> list) {
        if (param.equals("1")) {
            list.sort(new Comparator<ProductImage>() {
                @Override
                public int compare(ProductImage o1, ProductImage o2) {
                    return o1.getPrice() > o2.getPrice() ? 1 : o1.getPrice() == o2.getPrice() ? 0 : -1;
                }
            });
            return list;}

        if (param.equals("0")) {
            list.sort(new Comparator<ProductImage>() {
                @Override
                public int compare(ProductImage o1, ProductImage o2) {
                    return o1.getPrice() < o2.getPrice() ? 1 : o1.getPrice() == o2.getPrice() ? 0 : -1;
                }
            });
            return list;
        }
        throw new RuntimeException("Uncorrected param from server");
    }
}
