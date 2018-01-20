package hanfak.shopofhan.infrastructure.web;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RenderedContent {

    private final String body;
    private final String contentType;
    private final int statusCode;

    private RenderedContent(String body, String contentType, int statusCode) {
        this.body = body;
        this.contentType = contentType;
        this.statusCode = statusCode;
    }

    private static RenderedContent renderedContent(String body, String contentType, int statusCode) {
        return new RenderedContent(body, contentType, statusCode);
    }

    public static RenderedContent jsonContent(String body) {
        return renderedContent(body, "application/json", HttpServletResponse.SC_OK);
    }

    public static RenderedContent errorContent(String body) {
        return renderedContent(body, "text/plain", HttpServletResponse.SC_NOT_FOUND);
    }

    public static RenderedContent successfullyAddedStock(String body) {
        return renderedContent(body, "text/plain", HttpServletResponse.SC_OK);
    }

    public void render(HttpServletResponse response) throws IOException {
        response.setStatus(statusCode);
        response.setContentType(contentType);
        response.getWriter().print(body);
    }
}
