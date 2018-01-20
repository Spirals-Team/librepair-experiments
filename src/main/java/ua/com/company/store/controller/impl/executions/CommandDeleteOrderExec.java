package ua.com.company.store.controller.impl.executions;

import org.apache.log4j.Logger;
import ua.com.company.store.constants.Redirection;
import ua.com.company.store.controller.command.CommandTypical;
import ua.com.company.store.exceptions.ServiceException;
import ua.com.company.store.model.entity.Order;
import ua.com.company.store.model.entity.User;
import ua.com.company.store.service.OrderService;
import ua.com.company.store.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Владислав on 31.12.2017.
 */
public class CommandDeleteOrderExec implements CommandTypical {
  private OrderService orderService;
  private UserService userService;
  private Logger logger = Logger.getRootLogger();

    public CommandDeleteOrderExec(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userFormSession;
        if (req.getAttribute("user") != null) {
            userFormSession= (User) req.getAttribute("user");
        } else {
            userFormSession= (User) req.getSession().getAttribute("user");
        }
        if (req.getSession() == null || req.getSession().getAttribute("user") == null || !userFormSession.isRole()) {
            return Redirection.ACCESS_ERROR_PAGE;
        }

        String userNameByOrder = req.getParameter("userName");
        System.out.println(userNameByOrder);
        User user = userService.getUserByNickName(userNameByOrder);
        System.out.println(user.getNickname());
        Order order = null;
        try{
            order = orderService.getByParameter(user);
        }catch (ServiceException e){
            logger.error(e);
        }

        orderService.deleteOrder(order);

        logger.info("Successful deleted order " + order.toString());
        req.setAttribute("listOrders",orderService.getOrderWithInformation());




        return Redirection.ADMIN_PAGE;
    }
}
