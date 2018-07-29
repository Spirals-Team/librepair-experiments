<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=#myNavbar>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand"><img src="https://i.pinimg.com/originals/1e/00/78/1e0078f8266738d005b5fc7d00f9a66e.png" style="width: 10%;"></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <nav>
        <a href="/">Home</a>
        <a href="/conversations">Conversations</a>
        <% if (request.getSession().getAttribute("user") != null) { %>
          <a href="/users/<%= request.getSession().getAttribute("user")%>">My Profile</a>
          <a href="/logout.jsp">Logout</a>
        <% } else { %>
          <a href="/login">Login</a>
        <% } %>
        <a href="/activity">Activity Feed</a>
        <a href="/about.jsp">About Us</a>
        </nav>
      </ul>
    </div>
  </div>
</nav>
