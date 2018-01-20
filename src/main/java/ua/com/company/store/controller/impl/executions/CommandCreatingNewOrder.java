package ua.com.company.store.controller.impl.executions;

import org.apache.log4j.Logger;
import ua.com.company.store.constants.Redirection;
import ua.com.company.store.controller.command.CommandTypical;
import ua.com.company.store.model.entity.Order;
import ua.com.company.store.model.entity.Product;
import ua.com.company.store.model.entity.User;
import ua.com.company.store.service.OrderService;
import ua.com.company.store.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Владислав on 19.12.2017.
 */
public class CommandCreatingNewOrder implements CommandTypical {
    private OrderService orderService;
    private ProductService productService;
    private Logger logger = Logger.getRootLogger();


    public CommandCreatingNewOrder(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


       User user;
       if (req.getSession() == null || req.getSession().getAttribute("user")==null){
           return Redirection.ACCESS_ERROR_PAGE;
       }

        user = (User) req.getSession().getAttribute("user");


        String producrId = req.getParameter("id");
        Product product = productService.getByParameter(producrId);
        req.getSession().setAttribute("product",product);
        req.setAttribute("product",product);
        Order order = new Order.OrderBuilder().setId(0)
                .setProductId(Integer.parseInt(producrId))
                .setEntityId(user.getId())
                .setData(new Date(Calendar.getInstance().getTime().getTime()))
                .setPaid(false)
                .build();
        orderService.addOrder(order);
        logger.info("Successful added " + order.toString());


        return Redirection.SUCCESSFUL_CREATED_ORDER;
    }
}
