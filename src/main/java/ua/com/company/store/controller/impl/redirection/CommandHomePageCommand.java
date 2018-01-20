package ua.com.company.store.controller.impl.redirection;

import ua.com.company.store.constants.Redirection;
import ua.com.company.store.controller.command.CommandTypical;
import ua.com.company.store.utils.CookiesAction;
import ua.com.company.store.utils.RedirectionManager;
import ua.com.company.store.model.entity.User;
import ua.com.company.store.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Владислав on 07.12.2017.
 */
public class CommandHomePageCommand implements CommandTypical {

    private UserService userService;

    public CommandHomePageCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (CookiesAction.getUserFromCookie(req, userService).size()==0) {
            return Redirection.MAIN_PAGE + " " + RedirectionManager.REDIRECTION;
        }else {
            List list = CookiesAction.getUserFromCookie(req, userService);
            User user1 = (User) list.get(0);
            session.setAttribute("user", user1);
            req.setAttribute("user", user1);
            return Redirection.MAIN_PAGE+ " " + RedirectionManager.REDIRECTION;
        }
    }

    @Override
    public String toString() {
        return "CommandHomePageCommand{}";
    }
}
