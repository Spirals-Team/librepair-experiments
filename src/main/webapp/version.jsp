<%@page contentType="text/plain"%>
<%@page import="java.net.InetAddress"%>
radi.ws.version=${version}<br/>
radi.ws.hostaddress=<%=InetAddress.getLocalHost().getHostAddress() %><br/>
radi.ws.canonicalhostname=<%=InetAddress.getLocalHost().getCanonicalHostName() %><br/>
radi.ws.hostname=<%=InetAddress.getLocalHost().getHostName() %><br/>
radi.ws.tomcat.version=<%= application.getServerInfo() %><br/>
radi.ws.tomcat.catalina_base=<%= System.getProperty("catalina.base") %><br/>
