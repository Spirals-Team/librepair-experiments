package pl.rmitula.restfullshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rmitula.restfullshop.exception.ConflictException;
import pl.rmitula.restfullshop.exception.NotFoundException;
import pl.rmitula.restfullshop.model.Category;
import pl.rmitula.restfullshop.model.Product;
import pl.rmitula.restfullshop.dao.ProductRepository;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product findById(long id) {
        Product product = productRepository.findOne(id);

        if(product != null) {
            return product;
        } else {
            throw new NotFoundException("Not found product with id: " + id);
        }
    }

    public Product findByName(String name) {
        Product product = productRepository.findByNameIgnoreCase(name);

        if(product != null) {
            return product;
        } else {
            throw new NotFoundException("Not found product with name: " + name);
        }
    }

    public List<Product> findByCategoryId(Long categoryId) {
        Category category = categoryService.findById(categoryId);

        if (category != null) {
            List<Product> products = productRepository.findByCategory(category);

            if(!products.isEmpty()) {
                return products;

            } else {
                throw new NotFoundException("Not found products for category with id: " + categoryId);
            }
        } else {
            throw  new NotFoundException("Not found category with id: " + categoryId);
        }
    }

    public Long create(Product product) {
        Product name = productRepository.findByNameIgnoreCase(product.getName());

        if(name == null) {
            return productRepository.save(product).getId();
        } else {
            throw new ConflictException("This name is already associated with a different product.");
        }
    }

    public void update(Long id, String name, Long category, Integer quanityInStock, Double price) {
        Product product = productRepository.findOne(id);

        if(product != null) {
            Product procuctName = productRepository.findByNameIgnoreCase(product.getName());

            if(procuctName != null && procuctName.getId() != id) {
                throw new ConflictException("This name is already associated with another product.");
            }

            Category productCategory = categoryService.findById(category);

            product.setCategory(productCategory);
            product.setName(name);
            product.setQuanityInStock(quanityInStock);
            product.setPrice(price);

            productRepository.save(product);
        } else {
            throw new NotFoundException("Not found product with id: " + id);
        }
    }

    public void delete(long id) {
        Product product = productRepository.findOne(id);

        if(product != null) {
            productRepository.delete(id);
        } else {
            throw new NotFoundException("Not found product with id: " + id);
        }
    }

}
