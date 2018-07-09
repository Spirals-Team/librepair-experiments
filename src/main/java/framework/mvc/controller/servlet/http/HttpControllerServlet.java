/* Controller.java
 * Created on 17 April 2006, 21:12
 */

package framework.mvc.controller.servlet.http;

import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import framework.mvc.controller.CommandPool;
import framework.mvc.controller.ControllerException;

/**
 * ControllerServlet Class.
 */
public class HttpControllerServlet extends HttpServlet {

    static {
        final CommandPool invoker = CommandPool.getInstance();
    }

    /** The debug level. */
    private final int debugLevel = 3;

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException {
        final Map<?, ?> parameters = request.getParameterMap();
        try {
            response.sendRedirect(execute(parameters));
        } catch (final ControllerException controllerException) {
            if (this.debugLevel >= 3) {
                controllerException.printStackTrace(System.err);
            }
            throw new javax.servlet.ServletException(controllerException);
        } catch (final java.io.IOException ioException) {
            if (this.debugLevel >= 3) {
                ioException.printStackTrace(System.err);
            }
            throw new javax.servlet.ServletException(ioException);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException {
        final Map<?, ?> parameters = request.getParameterMap();
        try {
            execute(parameters);
        } catch (final ControllerException commandException) {
            commandException.printStackTrace();
        }
    }

    /**
     * Execute.
     *
     * parameters
     * string
     * controller exception
     */
    protected String execute(final Map<?, ?> parameters) throws ControllerException {
        if (parameters.get("EXCEPTION").equals("TRUE")) {
            throw new ControllerException();
        }
        return (String) parameters.get("url");
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.GenericServlet#getServletInfo()
     */
    @Override
    public String getServletInfo() {
        return this.getClass().getSimpleName() + "- Short description";
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
    }

}
