package ua.com.company.store.controller.impl.executions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.company.store.service.OrderService;
import ua.com.company.store.service.UserService;

/**
 * Created by Владислав on 09.01.2018.
 */
public class CommandExecuteOrderTest {
    private CommandExecuteOrder executeOrder;
    @Before
    public void setUp() throws Exception {
  executeOrder = new CommandExecuteOrder(OrderService.getInstance(),UserService.getInstance());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void execute() throws Exception {
      executeOrder.sendMessage("vd1321@mail.ru","test","test");
    }

    @Test
    public void sendMessage() throws Exception {
          //executeOrder.sendMessage("vd1321@mail.ru","test","test msg","vdvoreckij4@gmail.com","Forester18");
    }

}