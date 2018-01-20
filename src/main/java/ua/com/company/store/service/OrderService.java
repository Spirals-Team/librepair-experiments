package ua.com.company.store.service;

import ua.com.company.store.utils.JDBCConnectionPoolManager;
import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.model.dao.daoAbstract.GenericDAO;
import ua.com.company.store.model.dao.exceptions.PersistException;
import ua.com.company.store.model.dao.factory.MySqlDaoFactory;
import ua.com.company.store.model.dao.impl.OrderDAO;
import ua.com.company.store.model.dao.impl.ProductDAO;
import ua.com.company.store.model.dao.impl.UserDAO;
import ua.com.company.store.model.entity.Order;
import ua.com.company.store.model.entity.User;
import ua.com.company.store.model.entity.additional.UserProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 19.12.2017.
 */
public class OrderService {
    private GenericDAO genericDAO;


    public OrderService(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }
    private static class Holder{
        static OrderService INSTANCE;

        static {
            try {
                INSTANCE = new OrderService(MySqlDaoFactory.getMysqlDaoFactory(JDBCConnectionPoolManager.getInstance().getJdbcConnectionPool()).getDao(Order.class));
            } catch (PersistException e) {
                e.printStackTrace();
            }
        }
    }
    public static OrderService getInstance(){
        return Holder.INSTANCE;
    }

    public int addOrder(Order order){
        OrderDAO orderDAO = (OrderDAO) genericDAO;
        return orderDAO.insert(order);
    }
    public Order getOrderById(int id){
        OrderDAO orderDAO = (OrderDAO) genericDAO;
      return orderDAO.getById(id);
    }
    public List<Order> getAllOrders(){
        OrderDAO orderDAO = (OrderDAO) genericDAO;
        return orderDAO.getAll();
    }
    public Order getByParameter(User user){
        OrderDAO orderDAO = (OrderDAO) genericDAO;
        return orderDAO.getByParameter(String.valueOf(user.getId()));
    }
    public boolean deleteOrder(Order order){
        OrderDAO orderDAO = (OrderDAO) genericDAO;
        orderDAO.delete(order);
        return true;
    }


    /**
     *
     * Deprecated (Bad realisation)(My bad)
     * @return
     */
    public List<UserProduct> getOrderWithInformation(){
        List<UserProduct> userProductList = new ArrayList<>();
        UserService userService = new UserService(new UserDAO(JDBCConnectionPool.getInstanceConnectionPool()));
        ProductService productService = new ProductService(new ProductDAO(JDBCConnectionPool.getInstanceConnectionPool()));

        for (Order order: getAllOrders()){
            UserProduct userProduct = new UserProduct();
            userProduct.setUserName(userService.getById(order.getEntityId()).getNickname());
            userProduct.setProductTitle(productService.getById(order.getProductId()).getTitle());
            userProduct.setDate(order.getDate());
            userProduct.setIs_paid(order.isSuccessful_paid());
            userProductList.add(userProduct);
        }
        return userProductList;
    }
}
