package pl.rmitula.restfullshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rmitula.restfullshop.dao.OrderRepository;
import pl.rmitula.restfullshop.dao.ProductRepository;
import pl.rmitula.restfullshop.dao.UserRepository;
import pl.rmitula.restfullshop.exception.BadRequestException;
import pl.rmitula.restfullshop.exception.ConflictException;
import pl.rmitula.restfullshop.exception.NotFoundException;
import pl.rmitula.restfullshop.model.Order;
import pl.rmitula.restfullshop.model.Product;
import pl.rmitula.restfullshop.model.Status;
import pl.rmitula.restfullshop.model.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long create(Long productId, Long userId, Integer quanity, Status status) {
        Product product = productRepository.findOne(productId);
        User user = userRepository.findOne(userId);

        if(user != null) {
            if(product != null) {

                if(product.getQuanityInStock() == 0) {
                    //FIXME: Not sure about response code here
                    throw new NotFoundException("Product with id: " + productId + " is out of stock.");
                }

                if(quanity == 0) {
                    throw new BadRequestException("Quanity cannot be 0.");
                }

                if(product.getQuanityInStock() < quanity) {
                    //FIXME: Not sure about response code here
                    throw new ConflictException("There is not enough product in stock.");
                } else {
                    LocalDateTime date = LocalDateTime.now();

                    Order order = new Order();
                    order.setProduct(product);
                    order.setUser(user);
                    order.setQuantity(quanity);
                    order.setOrderDate(date);
                    order.setStatus(status);

                    product.setQuanityInStock(product.getQuanityInStock() - quanity);

                    return orderRepository.save(order).getId();
                }
            } else {
                throw new NotFoundException("Not found product with id: " + productId);
            }
        } else {
            throw new NotFoundException("Not found user with id: " + userId);
        }
    }

    public Order findById(long id) {
        Order order = orderRepository.findOne(id);

        if(order != null) {
            return order;
        } else {
            throw new NotFoundException("Not found order with id: " + id);
        }
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public List<Order> findByProductId(Long productId) {
        Product product = productRepository.findById(productId);

        if(product != null) {
            List <Order> orders = orderRepository.findByProduct(product);

            if(!orders.isEmpty()) {
                return orders;
            } else {
                throw new NotFoundException("Not found any orders of this product.");
            }
        } else {
            throw new NotFoundException("Not found product with id: " + productId);
        }
    }
}
