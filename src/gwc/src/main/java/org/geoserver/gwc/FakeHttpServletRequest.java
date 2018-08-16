/* (c) 2014 Open Source Geospatial Foundation - all rights reserved
 * (c) 2001 - 2013 OpenPlans
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.gwc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@SuppressWarnings("rawtypes")
class FakeHttpServletRequest implements HttpServletRequest {

    private static final Enumeration EMPTY_ENUMERATION =
            new Enumeration() {
                @Override
                public boolean hasMoreElements() {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public Object nextElement() {
                    // TODO Auto-generated method stub
                    return null;
                }
            };

    private String workspace;

    private Map<String, String> parameterMap = new HashMap<String, String>(10);

    private Cookie[] cookies;

    public FakeHttpServletRequest(Map<String, String> parameterMap, Cookie[] cookies) {
        this(parameterMap, cookies, null);
    }

    public FakeHttpServletRequest(
            Map<String, String> parameterMap, Cookie[] cookies, String workspace) {
        this.parameterMap = parameterMap;
        this.cookies = cookies;
        this.workspace = workspace;
    }

    /** Standard interface */
    public String getAuthType() {
        throw new ServletDebugException();
    }

    public String getContextPath() {
        return "/geoserver";
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public long getDateHeader(String arg0) {
        throw new ServletDebugException();
    }

    public String getHeader(String arg0) {
        return null;
    }

    public Enumeration getHeaderNames() {
        return EMPTY_ENUMERATION;
    }

    public Enumeration getHeaders(String arg0) {
        throw new ServletDebugException();
    }

    public int getIntHeader(String arg0) {
        throw new ServletDebugException();
    }

    public String getMethod() {
        return "GET";
    }

    public String getPathInfo() {
        throw new ServletDebugException();
    }

    public String getPathTranslated() {
        throw new ServletDebugException();
    }

    public String getQueryString() {
        throw new ServletDebugException();
    }

    public String getRemoteUser() {
        throw new ServletDebugException();
    }

    public String getRequestURI() {
        if (workspace != null && !workspace.isEmpty()) {
            return "/geoserver/" + workspace + "/wms";
        } else {
            return "/geoserver/wms";
        }
    }

    public StringBuffer getRequestURL() {
        throw new ServletDebugException();
    }

    public String getRequestedSessionId() {
        throw new ServletDebugException();
    }

    public String getServletPath() {
        throw new ServletDebugException();
    }

    public HttpSession getSession() {
        throw new ServletDebugException();
    }

    public HttpSession getSession(boolean arg0) {
        throw new ServletDebugException();
    }

    public Principal getUserPrincipal() {
        throw new ServletDebugException();
    }

    public boolean isRequestedSessionIdFromCookie() {
        throw new ServletDebugException();
    }

    public boolean isRequestedSessionIdFromURL() {
        throw new ServletDebugException();
    }

    public boolean isRequestedSessionIdFromUrl() {
        throw new ServletDebugException();
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return false;
    }

    @Override
    public void login(String username, String password) throws ServletException {}

    @Override
    public void logout() throws ServletException {}

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return null;
    }

    public boolean isRequestedSessionIdValid() {
        throw new ServletDebugException();
    }

    public boolean isUserInRole(String arg0) {
        throw new ServletDebugException();
    }

    public Object getAttribute(String arg0) {
        throw new ServletDebugException();
    }

    public Enumeration getAttributeNames() {
        throw new ServletDebugException();
    }

    public String getCharacterEncoding() {
        return "UTF-8";
    }

    public int getContentLength() {
        throw new ServletDebugException();
    }

    public String getContentType() {
        return null;
    }

    public ServletInputStream getInputStream() throws IOException {
        throw new ServletDebugException();
    }

    public String getLocalAddr() {
        throw new ServletDebugException();
    }

    public String getLocalName() {
        throw new ServletDebugException();
    }

    public int getLocalPort() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }

    public Locale getLocale() {
        throw new ServletDebugException();
    }

    public Enumeration getLocales() {
        throw new ServletDebugException();
    }

    public String getParameter(String arg0) {
        return parameterMap.get(arg0);
    }

    public Map getParameterMap() {
        return parameterMap;
    }

    public Enumeration getParameterNames() {
        return Collections.enumeration(parameterMap.keySet());
    }

    public String[] getParameterValues(String arg0) {
        throw new ServletDebugException();
    }

    public String getProtocol() {
        throw new ServletDebugException();
    }

    public BufferedReader getReader() throws IOException {
        throw new ServletDebugException();
    }

    public String getRealPath(String arg0) {
        throw new ServletDebugException();
    }

    public String getRemoteAddr() {
        return "127.0.0.1";
    }

    public String getRemoteHost() {
        return "localhost";
    }

    public int getRemotePort() {
        throw new ServletDebugException();
    }

    public RequestDispatcher getRequestDispatcher(String arg0) {
        throw new ServletDebugException();
    }

    public String getScheme() {
        return "http";
    }

    public String getServerName() {
        return "localhost";
    }

    public int getServerPort() {
        return 8080;
    }

    public boolean isSecure() {
        throw new ServletDebugException();
    }

    public void removeAttribute(String arg0) {
        throw new ServletDebugException();
    }

    public void setAttribute(String arg0, Object arg1) {
        throw new ServletDebugException();
    }

    public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
        if (!arg0.equals("UTF-8")) {
            throw new ServletDebugException();
        }
    }
}
