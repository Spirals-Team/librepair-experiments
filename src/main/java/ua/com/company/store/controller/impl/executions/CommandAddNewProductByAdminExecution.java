package ua.com.company.store.controller.impl.executions;

import org.apache.log4j.Logger;
import ua.com.company.store.constants.Redirection;
import ua.com.company.store.controller.command.CommandTypical;
import ua.com.company.store.model.dto.ProductDto;
import ua.com.company.store.model.entity.Image;
import ua.com.company.store.model.entity.Product;
import ua.com.company.store.service.ProductImageService;
import ua.com.company.store.validation.ValidatorAbstract;
import ua.com.company.store.validation.products.DescriptionValidator;
import ua.com.company.store.validation.products.ImageInformValidator;
import ua.com.company.store.validation.products.TitleValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

/**
 * Created by Владислав on 03.12.2017.
 */
public class CommandAddNewProductByAdminExecution implements CommandTypical {

    private ProductImageService productService;


    private Logger logger = Logger.getRootLogger();

    public CommandAddNewProductByAdminExecution(ProductImageService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDto productDto = getNewProductInputs(req.getParameter("title"), req.getParameter("description"), Integer.parseInt(req.getParameter("price")), downloadImage(req, resp));
        if (doValidationInputs(productDto)) {
            Image image = new Image.ImageBuilder().setID(0)
                    .setPath(productDto.getImageInformation()[0])
                    .setdata(productDto.getImageInformation()[1]).build();
            Product product = new Product.ProductBuilder().setId(0)
                    .setTitle(productDto.getTitle())
                    .setDescr(productDto.getDescription())
                    .setPrice(productDto.getPrice())
                    .setImgId(0).build();
            productService.addProductAndImage(product, image);
            req.setAttribute("successful", "Successful added new product: " + product.getTitle());
            return Redirection.ADMIN_PAGE;
        } else {
            req.setAttribute("error", " NUll inputs!!!");
            return Redirection.ERRORS_WITH_INPUTS;
        }
    }


    /**
     * Method for transportation information about product in object DTO
     * @param title
     * @param desc
     * @param price
     * @param imgInform
     * @return
     */
    private ProductDto getNewProductInputs(String title, String desc, int price, String[] imgInform) {
        return new ProductDto(title, desc, price, imgInform);
    }

    /**
     * Function witch do validation inputs using change of responsibility pattern
     * @param productDto
     * @return
     */
    private boolean doValidationInputs(ProductDto productDto) {
        ValidatorAbstract validatorAbstractTitle = new TitleValidator();
        ValidatorAbstract validatorAbstractDescription = new DescriptionValidator();
        ValidatorAbstract validatorAbstractPrice = new DescriptionValidator();
        ValidatorAbstract validatorAbstractImageInform = new ImageInformValidator();

        validatorAbstractTitle.setNextValidator(validatorAbstractDescription);
        validatorAbstractDescription.setNextValidator(validatorAbstractPrice);
        validatorAbstractPrice.setNextValidator(validatorAbstractImageInform);

        return validatorAbstractTitle.validate(productDto.getTitle(), productDto.getDescription(), String.valueOf(productDto.getPrice()), productDto.getImageInformation()[0], productDto.getImageInformation()[1]);
    }

    /**
     * Method witch do byte-byte writing image for product from Admin
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private String[] downloadImage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String path = "G:\\JAVA\\EPAM\\GiveawayStore\\web\\resources\\images";
        final Part filePart = req.getPart("file");
        final String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream fileContent = null;

        try {
            out = new FileOutputStream(new File(path + File.separator + fileName));
            fileContent = filePart.getInputStream();
            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            logger.info("File being uploaded ");

        } catch (FileNotFoundException e) {
            logger.info("Problems during file upload. Error: {0}" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
            if (fileContent != null) {
                out.close();
            }
        }

        return new String[]{path, fileName};
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
