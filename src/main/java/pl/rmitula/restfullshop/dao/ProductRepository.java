package pl.rmitula.restfullshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rmitula.restfullshop.model.Category;
import pl.rmitula.restfullshop.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);

    Product findByNameIgnoreCase(String name);

    List<Product> findByCategory(Category category);
}
