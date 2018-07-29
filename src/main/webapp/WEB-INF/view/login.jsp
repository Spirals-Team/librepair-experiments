<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<!DOCTYPE html>
<html>
<title>Login</title>
<%@ include file = "/header.jsp" %>
<body>
  <style>
    html {
      zoom:80%;
    }
  </style>

  <%@ include file = "/navigations.jsp" %>

  <div class="container">
    <h1>Login</h1>

    <% if (request.getAttribute("error") != null) { %>
      <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>
    <% if (request.getAttribute("sent") != null) { %>
      <h2 style="color:green"><%= request.getAttribute("sent") %></h2>
    <% } %>

    <form class="form-group" action="/login" method="POST">
      <label class="form-control-label" for="username">Username</label>
      <input class="form-control" type="text" name="username" id="username" placeholder="Username" required>
      </br>
      <label class="form-control-label" for="password">Password</label>
      <input class="form-control" type="password" name="password" id="password" placeholder="Password" required>
      </br>
      <button type="submit" class="btn">Login</button>
    </form>

    <p>New users can register <a href="/register">here</a>.</p>
    <p>Forgot password? Click <a href="/resetQuestion">here</a>.</p>
  </div>
</body>
</html>
