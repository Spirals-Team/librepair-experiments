<%@page pageEncoding="UTF-8" contentType="text/plain;charset=UTF-8"%>
<%@page import="java.net.InetAddress"%>
${project.parent.artifactId}.version=${project.version}
${project.parent.artifactId}.localhost.hostaddress=<%=InetAddress.getLocalHost().getHostAddress() %>
${project.parent.artifactId}.localhost.canonicalhostname=<%=InetAddress.getLocalHost().getCanonicalHostName() %>
${project.parent.artifactId}.localhost.hostname=<%=InetAddress.getLocalHost().getHostName() %>
<% 
HttpSession theSession = request.getSession( false );

try {
    if( theSession != null ) {
      synchronized( theSession ) {
        theSession.invalidate();
      }
    }
} catch (Exception e) {
}
%>