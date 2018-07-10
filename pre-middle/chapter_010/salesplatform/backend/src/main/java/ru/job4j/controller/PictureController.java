package ru.job4j.controller;

import ru.job4j.dao.PictureDaoImpl;

import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PictureController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/*");
        byte[] pic = new PictureDaoImpl().findOne(Long.valueOf(req.getParameter("pic"))).getData();

        try (ServletOutputStream sos = resp.getOutputStream();
             ImageOutputStream ios = new MemoryCacheImageOutputStream(sos)) {
            ios.write(pic);
            sos.flush();
            ios.flush();
        }
    }
}

