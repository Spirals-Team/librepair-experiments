package ua.com.company.store.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Filter witch using for encoding request from client utf-8
 * (as default filter gets coding ad null )
 */
@WebFilter(urlPatterns = {"/store/*"}, initParams = {@WebInitParam(name = "encoding", value = "utf-8", description = "Encoding param")})
public class EncodingFilter implements Filter {
    private String defaultEnc;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultEnc = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String codeReq = servletRequest.getCharacterEncoding();
        if (defaultEnc != null && !defaultEnc.equalsIgnoreCase(codeReq)) {
            servletRequest.setCharacterEncoding(defaultEnc);
            servletResponse.setCharacterEncoding(defaultEnc);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        defaultEnc = null;
    }
}
