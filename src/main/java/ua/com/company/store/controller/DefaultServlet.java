package ua.com.company.store.controller;

import org.apache.log4j.Logger;
import ua.com.company.store.controller.command.CommandFactory;
import ua.com.company.store.controller.command.CommandTypical;
import ua.com.company.store.utils.CommandKeyGenerator;
import ua.com.company.store.utils.JDBCConnectionPoolManager;
import ua.com.company.store.utils.RedirectionManager;
import ua.com.company.store.utils.ServletWrapper;
import ua.com.company.store.exceptions.ServiceException;
import ua.com.company.store.locale.AppLocale;
import ua.com.company.store.model.dao.connection.JDBCConnectionPool;
import ua.com.company.store.model.dao.exceptions.PersistException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;



@WebServlet(name = "default servlet ", urlPatterns = {"/store/*"}, loadOnStartup = 1)
@MultipartConfig
public class DefaultServlet extends HttpServlet {
    private Logger logger = Logger.getRootLogger();
   private JDBCConnectionPoolManager  jdbcConnectionPoolManager ;

    public DefaultServlet() throws PersistException {

    }

    @Override
    public void init() throws ServletException {
        jdbcConnectionPoolManager = JDBCConnectionPoolManager.getInstance();
        jdbcConnectionPoolManager.setJdbcConnectionPool(JDBCConnectionPool.getInstanceConnectionPool());
        getServletContext().setAttribute("locales", AppLocale.getAppLocales());
        logger.info("Initialized default servlet  " + this.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setCharacterEncoding("utf-8");
        String key = CommandKeyGenerator.generateCommandKeyByRequest(req);
        logger.info("Query with url: " + key);
        CommandTypical commandTypical = CommandFactory.getCommand(key);
        logger.info("Command key execution: " + commandTypical.toString());
        String redirectionPath = commandTypical.execute(req, resp);

        logger.info("Redirection path: " + redirectionPath);
        forwardToCommandResultPage(new ServletWrapper(req, resp), redirectionPath);

    }

    private void forwardToCommandResultPage(ServletWrapper servletWrapper, String commandRes) throws ServletException, IOException {
        if (!commandRes.contains(RedirectionManager.REDIRECTION)) {
            servletWrapper.getRequest().getRequestDispatcher(commandRes).forward(servletWrapper.getRequest(),servletWrapper.getResponse());
        } else {
            String[] newCommandRes = commandRes.split(" ");
            servletWrapper.getResponse().sendRedirect(newCommandRes[0]);
        }
    }

    private void redirectToHomePageWithErrorMessage(ServletWrapper servletWrapper, ServiceException se) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("error", se.getMessage());
        try {
            RedirectionManager.getRediractionManger().redirectWithParams(servletWrapper, "main.jsp", urlParams);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
