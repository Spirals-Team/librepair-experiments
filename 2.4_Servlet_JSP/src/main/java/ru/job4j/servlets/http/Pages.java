package ru.job4j.servlets.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class for print all pages what programm have.
 *
 * @author ifedorenko
 * @since 02.08.2018
 */
public class Pages {
    /**
     * Print all users for page list.
     * @param response response
     * @throws IOException exception
     */
    public static void printUsers(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        StringBuilder sb = new StringBuilder();
        ValidateService.getInstance().findAll().stream().forEach(n -> sb.append("<tr>"
                + "<td>" + n.getName() + "</td> "
                + "<td>" + n.getLogin() + "</td> "
                + "<td>" + n.getEmail() + "</td> "
                + "<td>"
                    + "<form action='edit' method='get'>"
                    + "<input type='submit' value='Edit'>"
                    + "<input type='hidden' name='id' value='" + n.getId() + "'/>"
                    + "</form>"
                + "</td>"
                + "<td>"
                    + "<form action='delete' method='get'>"
                        + "<input type='submit' value='Delete'>"
                        + "<input type='hidden' name='id' value='" + n.getId() + "'/>"
                    + "</form>"
                + "</td>"
                + "</tr>"));
        PrintWriter writer = new PrintWriter(response.getOutputStream());
        writer.append("<!DOCTYPE html>"
                + "<html>"
                    + "<head>"
                        + "<meta charset='UTF-8'>"
                        + "<title>All Users</title>"
                    + "</head>"
                    + "<body>"
                        + "<form action='create'>"
                            + "<input type='submit' value='create user'>"
                        + "</form>");
        if (ValidateService.getInstance().findAll().size() > 0) {
            writer.append("<table>"
                    + "<tr>"
                        + "<td> Name </td>"
                        + "<td> login </td>"
                        + "<td> email </td>"
                    + "</tr>"
                    + sb.toString()
                    + "</table>");
        }
        writer.append("</body></html)");
        writer.flush();
    }

    /**
     * Print form for editing user on page edit.
     * @param request request
     * @param response response
     * @throws IOException exception
     */
    public static void printEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        int id = Integer.valueOf(request.getParameter("id"));
        User user = ValidateService.getInstance().findById(id);
        PrintWriter writer = new PrintWriter(response.getOutputStream());
        writer.append("<!DOCTYPE html>"
                + "<html>"
                    + "<head>"
                        + "<meta charset='UTF-8'>"
                        + "<title>Edit user</title>"
                    + "</head>"
                    + "<body>"
                        + "<form method='post'>"
                            + "<input type='hidden' name='id' value='" + id + "'/>"
                            + "<input type ='text' name='name' value='" + user.getName() + "'>"
                            + "<input type ='text' name='email' value='" + user.getEmail() + "'>"
                            + "<input type='submit' value='Edit'>"
                        + "</form>"
                        + "<form action='list'>"
                            + "<input type='submit' value='Main page'>"
                        + "</form>"
                    + "</body>"
                + "</html>");
        writer.flush();
    }

    /**
     * Print form for creating user on page create.
     * @param response response
     * @throws IOException exception
     */
    public static void printCreate(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = new PrintWriter(response.getOutputStream());
        writer.append("<!DOCTYPE html>"
                + "<html>"
                    + "<head>"
                        + "<meta charset='UTF-8'>"
                        + "<title>Create user</title>"
                    + "</head>"
                    + "<body>"
                        + "<form method='post'>"
                            + "Name <input type ='text' name='name'>"
                            + "Login <input type ='text' name='login'>"
                            + "Email <input type ='text' name='email'>"
                            + "<input type='submit' value='Create'>"
                        + "</form>"
                        + "<form action='list'>"
                            + "<input type='submit' value='Main page'>"
                        + "</form>"
                    + "</body>"
                + "</html>");
        writer.flush();
    }
}
