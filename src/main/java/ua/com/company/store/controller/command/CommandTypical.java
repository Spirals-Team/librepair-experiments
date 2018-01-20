package ua.com.company.store.controller.command;

import ua.com.company.store.model.dao.daoAbstract.GenericDAO;
import ua.com.company.store.model.dao.factory.FactoryDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Владислав on 22.11.2017.
 */
public interface CommandTypical {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
