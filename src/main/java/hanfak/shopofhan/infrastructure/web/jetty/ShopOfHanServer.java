package hanfak.shopofhan.infrastructure.web.jetty;

import hanfak.shopofhan.infrastructure.properties.Settings;
import hanfak.shopofhan.infrastructure.web.server.WebServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import static java.lang.String.format;

public class ShopOfHanServer implements WebServer {

    private final Server server;

    public ShopOfHanServer(Settings settings) {
        this.server = new Server(settings.serverPort());
    }

    public ShopOfHanServer withContext(ServletContextHandler servletHandler) {
        server.setHandler(servletHandler);
        return this;
    }

    @Override
    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            throw new IllegalStateException(format("Could not start server on port '%d'", server.getURI().getPort()), e);
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new IllegalStateException(format("Could not stop server on port '%d'", server.getURI().getPort()), e);
        }
    }
}
