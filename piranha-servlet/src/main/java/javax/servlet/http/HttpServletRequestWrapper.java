/*
 *  Copyright (c) 2002-2018, Manorrock.com. All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      1. Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *
 *      2. Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package javax.servlet.http;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestWrapper;

/**
 * The HttpServletRequestWrapper API.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HttpServletRequestWrapper extends ServletRequestWrapper implements HttpServletRequest {

    /**
     * Constructor.
     *
     * @param request the wrapped request.
     */
    public HttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * Authenticate the request.
     *
     * @param response the response.
     * @throws IOException when an I/O error occurs.
     * @throws ServletException when a Servlet error occurs.
     */
    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return getWrapped().authenticate(response);
    }

    /**
     * Change the session id.
     *
     * @return the new session id.
     */
    @Override
    public String changeSessionId() {
        return getWrapped().changeSessionId();
    }

    /**
     * Get the auth type.
     *
     * @return the auth type.
     */
    @Override
    public String getAuthType() {
        return getWrapped().getAuthType();
    }

    /**
     * Get the context path.
     *
     * @return the context path.
     */
    @Override
    public String getContextPath() {
        return getWrapped().getContextPath();
    }

    /**
     * Get the cookies.
     *
     * @return the cookies, or null if none.
     */
    @Override
    public Cookie[] getCookies() {
        return getWrapped().getCookies();
    }

    /**
     * Get the date header.
     *
     * @param name the name.
     * @return the date, or -1 if not found.
     */
    @Override
    public long getDateHeader(String name) {
        return getWrapped().getDateHeader(name);
    }

    /**
     * Get the header.
     *
     * @param name the name.
     * @return the value, or null if not found.
     */
    @Override
    public String getHeader(String name) {
        return getWrapped().getHeader(name);
    }

    /**
     * Get the header names.
     *
     * @return the header names.
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return getWrapped().getHeaderNames();
    }

    /**
     * Get the headers.
     *
     * @param name the name.
     * @return the values.
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        return getWrapped().getHeaders(name);
    }

    /**
     * Get the int header.
     *
     * @param name the name.
     * @return the int, or -1 if not found.
     */
    @Override
    public int getIntHeader(String name) {
        return getWrapped().getIntHeader(name);
    }

    /**
     * Get the method.
     *
     * @return the method.
     */
    @Override
    public String getMethod() {
        return getWrapped().getMethod();
    }

    /**
     * Get the part.
     *
     * @param name the name.
     * @throws IOException when an I/O error occurs.
     * @throws ServletException when a Servlet error occurs.
     */
    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return getWrapped().getPart(name);
    }

    /**
     * Get the parts.
     *
     * @return the parts.
     * @throws IOException when an I/O error occurs.
     * @throws ServletException when a Servlet error occurs.
     */
    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return getWrapped().getParts();
    }

    /**
     * Get the path info.
     *
     * @return the path info.
     */
    @Override
    public String getPathInfo() {
        return getWrapped().getPathInfo();
    }

    /**
     * Get the path translated.
     *
     * @return the path translated.
     */
    @Override
    public String getPathTranslated() {
        return getWrapped().getPathTranslated();
    }

    /**
     * Get the query string.
     *
     * @return the query string.
     */
    @Override
    public String getQueryString() {
        return getWrapped().getQueryString();
    }

    /**
     * Get the remote user.
     *
     * @return the remote user.
     */
    @Override
    public String getRemoteUser() {
        return getWrapped().getRemoteUser();
    }

    /**
     * Get the request URI.
     *
     * @return the request URI.
     */
    @Override
    public String getRequestURI() {
        return getWrapped().getRequestURI();
    }

    /**
     * Get the request URL.
     *
     * @return the request URL.
     */
    @Override
    public StringBuffer getRequestURL() {
        return getWrapped().getRequestURL();
    }

    /**
     * Get the requested session id.
     *
     * @return the requested session id.
     */
    @Override
    public String getRequestedSessionId() {
        return getWrapped().getRequestedSessionId();
    }

    /**
     * Get the servlet path.
     *
     * @return the servlet path.
     */
    @Override
    public String getServletPath() {
        return getWrapped().getServletPath();
    }

    /**
     * Get the HTTP session.
     *
     * @return the HTTP session.
     */
    @Override
    public HttpSession getSession() {
        return getWrapped().getSession();
    }

    /**
     * Get the HTTP session.
     *
     * @param create the create flag.
     * @return the HTTP session, or null if not able to create.
     */
    @Override
    public HttpSession getSession(boolean create) {
        return getWrapped().getSession(create);
    }

    /**
     * Get the user principal.
     *
     * @return the user principal.
     */
    @Override
    public Principal getUserPrincipal() {
        return getWrapped().getUserPrincipal();
    }

    /**
     * Get the wrapped request.
     *
     * @return the wrapped request.
     */
    private HttpServletRequest getWrapped() {
        return (HttpServletRequest) super.getRequest();
    }

    /**
     * Is the requested session id from a cookie.
     *
     * @return true if it is, false otherwise.
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return getWrapped().isRequestedSessionIdFromCookie();
    }

    /**
     * Is the requested session id from a URL.
     *
     * @return true if it is, false otherwise.
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {
        return getWrapped().isRequestedSessionIdFromURL();
    }

    /**
     * Is the requested session if from a URL.
     *
     * @return true if it is, false otherwise.
     * @deprecated
     */
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        throw new UnsupportedOperationException();
    }

    /**
     * Is the requested session id valid.
     *
     * @return true if it is, false otherwise.
     */
    @Override
    public boolean isRequestedSessionIdValid() {
        return getWrapped().isRequestedSessionIdValid();
    }

    /**
     * Is the user in the role.
     *
     * @param role the role.
     * @return true if the user is, false otherwise.
     */
    @Override
    public boolean isUserInRole(String role) {
        return getWrapped().isUserInRole(role);
    }

    /**
     * Login.
     *
     * @param username the username.
     * @param password the password.
     * @throws ServletException when a Servlet error occurs.
     */
    @Override
    public void login(String username, String password) throws ServletException {
        getWrapped().login(username, password);
    }

    /**
     * Logout.
     *
     * @throws ServletException when a Servlet error occurs.
     */
    @Override
    public void logout() throws ServletException {
        getWrapped().logout();
    }

    /**
     * Upgrade the request.
     *
     * @param <T> the type of HTTP upgrade handler class.
     * @param handlerClass the handler class.
     * @return the HTTP upgrade handler.
     * @throws IOException when an I/O error occurs.
     * @throws ServletException when a Servlet error occurs.
     */
    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return getWrapped().upgrade(handlerClass);
    }
}
