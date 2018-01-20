package ua.com.company.store.controller.impl.executions;

import ua.com.company.store.constants.Redirection;
import ua.com.company.store.controller.command.CommandTypical;
import ua.com.company.store.model.entity.additional.ProductImage;
import ua.com.company.store.service.ProductImageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 11.01.2018.
 */
public class CommandSearchProduct implements CommandTypical {
    private ProductImageService productImageService;

    public CommandSearchProduct(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String textS = req.getParameter("searchText");
        List listProducts = productImageService.getAllProducts();

        req.setAttribute("list", doSearch(textS,listProducts));

        return Redirection.MAIN_PAGE;
    }
    List doSearch(String text, List<ProductImage> list){
        List newListOfProducts = new ArrayList();
        for (ProductImage productImage: list){
            if (containsIgnoreCase(productImage.getTitle(),text) || containsIgnoreCase(productImage.getDescription(),text)){
                newListOfProducts.add(productImage);
            }
        }
        return newListOfProducts;
    }
    private static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }
}
