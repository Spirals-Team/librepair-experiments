package ru.aserdyuchenko.servlet;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import ru.aserdyuchenko.storage.DataSource;
import ru.aserdyuchenko.storage.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 * @since 05.11.2017 18:30
 */
public class ServletEditUser extends HttpServlet {
    Logger logger = Logger.getLogger(ServletEditUser.class);


    public void init() {
        org.apache.log4j.BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);

    }

    /**
     * Get role by login.
     * @param login
     * @return role
     */
    private String getRoleByLogin(String login) throws SQLException {
        String userRole = "";
        DataSource storage = DataSource.getInstance();
        List<User> allUsers = storage.getList();
        System.out.println("allUsers: " + allUsers.toString());
        for(User user : allUsers) {
            if (user.getLogin().equals(login))
                userRole = user.getRole();
        }
        System.out.println("userRole: " + userRole);
        return userRole;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            logger.info("Hello this is an info message, from doGet method in ServletEditUser.java");
            HttpSession session = request.getSession();

            synchronized (session) {
                //TODO Rewrite this code!!!
                String login = session.getAttribute("login").toString(); // this work!
                System.out.println("login: "+login);
                System.out.println("role: "+getRoleByLogin(login));
                request.setAttribute("role", getRoleByLogin(login));
                request.setAttribute("login", session.getAttribute("login"));
                request.setAttribute("users", DataSource.getInstance().getList());
                //TODO Add another attributes.
            }

            request.getRequestDispatcher("/edit.html").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");

        try {
            HttpSession session = request.getSession();
            synchronized (session) {
                Class.forName("org.postgresql.Driver");
                DataSource storage = DataSource.getInstance();
//                if (session.getAttribute("role").toString().equals("admin")) {
//                    storage.update(request.getParameter("name"), request.getParameter("email"), request.getParameter("createDate"), request.getParameter("login"), request.getParameter("password"), request.getParameter("role"));
//                } else if(session.getAttribute("role").toString().equals("user")) {
//                    storage.update(request.getParameter("name"), request.getParameter("email"), request.getParameter("createDate"), session.getAttribute("login").toString(), request.getParameter("password"), request.getParameter("role"));
//                }
                if (request.getParameter("role").toString().equals("admin")) {
                    storage.update(request.getParameter("name"), request.getParameter("email"), request.getParameter("createDate"), request.getParameter("login"), request.getParameter("password"), request.getParameter("role"));
                } else if(request.getParameter("role").toString().equals("user")) {
                    storage.updateByUser(request.getParameter("name"),
                            request.getParameter("email"),
                            request.getParameter("createDate"),
                            request.getParameter("login")
                    );
                }
                response.sendRedirect(String.format("%s/editUser", request.getContextPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
