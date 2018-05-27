package ru.job4j.carsale;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import ru.job4j.models.Advert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ServletAddAdvertPage extends HttpServlet {
    private final CarStorage carStorage = CarStorage.INSTANCE;
    private DiskFileItemFactory factory;

    @Override
    public void init() throws ServletException {
        factory = new DiskFileItemFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("login") == null) {
            resp.sendRedirect("login");
            return;
        } else {
            req.getRequestDispatcher("/WEB-INF/views/AddCarPage.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(req);
            if (isMultipart) {
                int id = (int) req.getSession().getAttribute("user_id");
                String name = "";
                String desc = "";
                int idBrand = -1;
                int idModel = -1;
                byte[] picture = {};
                Iterator iter = new ServletFileUpload(factory).parseRequest(req).iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        String text = IOUtils.toString(item.getInputStream(), "utf-8");
                        if (item.getFieldName().equals("selBrand")) {
                            idBrand = Integer.parseInt(text);
                        } else if (item.getFieldName().equals("selModel")) {
                            idModel = Integer.parseInt(text);
                        } else if (item.getFieldName().equals("name")) {
                            name = text;
                        } else if (item.getFieldName().equals("desc")) {
                            desc = text;
                        }
                    } else {
                        if (item.getContentType().contains("image/jpeg")) {
                            picture = Base64.getEncoder().encode(item.get());
                        }
                    }
                }
                if (!name.isEmpty() && !desc.isEmpty() && idBrand > 0 && idModel > 0) {
                    carStorage.addObject(new Advert(carStorage.getUser(id), idBrand, idModel, name, desc, picture));
                }
            }
            resp.sendRedirect("mainpage");
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
}
