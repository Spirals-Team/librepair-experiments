package pl.rmitula.restfullshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rmitula.restfullshop.model.Order;
import pl.rmitula.restfullshop.model.Product;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByProduct(Product product);
}
