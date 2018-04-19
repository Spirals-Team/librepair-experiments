package pl.rmitula.restfullshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rmitula.restfullshop.model.Category;
import pl.rmitula.restfullshop.model.Product;
import pl.rmitula.restfullshop.model.dto.ProductDto;
import pl.rmitula.restfullshop.service.CategoryService;
import pl.rmitula.restfullshop.service.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static pl.rmitula.restfullshop.controller.converter.Converter.fromProductDto;
import static pl.rmitula.restfullshop.controller.converter.Converter.toProductDto;

@RestController
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    final String url = "api/products";

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping(url)
    public List<ProductDto> get() {
        return productService.getProducts().stream().map((Product product) -> toProductDto(product)).collect(Collectors.toList());
    }

    @GetMapping(url + "/{id}")
    public ProductDto findById(@PathVariable(name = "id") long id) {
        return toProductDto(productService.findById(id));
    }

    @GetMapping(url + "/findByName/{name}")
    public ProductDto findByName(@PathVariable(name = "name") String name) {
        return toProductDto(productService.findByName(name));
    }

    @GetMapping("api/category/{categoryId}/products")
    public List<ProductDto> findByCategoryId(@PathVariable(name = "categoryId") Long categoryId) {
        return productService.findByCategoryId(categoryId).stream().map((Product product) -> toProductDto(product)).collect(Collectors.toList());
    }

    @PostMapping(url)
    public ResponseEntity<Long> create(@RequestBody @Valid ProductDto productDto) {
        //TODO: To compare with OrderController create method.
        Category category = categoryService.findById(productDto.getCategory());

        if(category != null) {
            return new ResponseEntity<>(productService.create(fromProductDto(productDto, category)), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(url + "/{id}")
    public void update(@PathVariable(name = "id") long id, @RequestBody @Valid ProductDto productDto) {
        productService.update(id, productDto.getName(), productDto.getCategory(), productDto.getQuanityInStock(), productDto.getPrice());
    }

    @DeleteMapping(url + "/{id}")
    public void delete(@PathVariable(name = "id") long id){
            productService.delete(id);
    }

}
