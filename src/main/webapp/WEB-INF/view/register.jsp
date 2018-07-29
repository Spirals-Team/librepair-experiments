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
<title>Register</title>
<%@ include file = "/header.jsp" %>
<body>
  <style>
    html {
      zoom:80%;
    }
  </style>
  <script>
   $(document).ready(function(){
      $("#info").on({
        click: function(){
          var alertM = $($(document).find('small'));
          if(alertM.css("display") == "block"){
            alertM.css("display", "none");
            return
          }
          alertM.css("display", "block");
          }
      });
    });
  </script>
  
  <%@ include file = "/navigations.jsp" %>

  <div class="container">
    <h1 class="">Create a New Account: It's Free</h1>
    <br>
    
    <% if (request.getAttribute("error") != null) { %>
      <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <form action="/register" method="POST">
      <label class="form-control-label" for="username">Username</label>
      <input pattern="\S{3,}" required title="3 characters minimum: No spaces"
      class="form-control" type="text" name="username" id="username" placeholder="Username"required>
      <br>
      <label class="form-control-label" for="email">Email Address <i id="info" style="color:blue">&#x2139;</i></label>
      <div class="input-group mb-2 mr-sm-2 mb-sm-0">
        <div class="input-group-addon">@</div>
        <input class="form-control" type="email" name="email" placeholder="name@example.com">
      </div>
      <small class="form-text text-muted" style="display:none">In case, you forget your password.<br /></small>
      <br>
      <label class="form-control-label" for="password">Password</label>
      <input pattern="\S{3,}" required title="3 characters minimum: No spaces"
      class="form-control" type="password" name="password" id="password" placeholder="Password" required>
      <br>
      <button type="submit" class="btn">Submit</button>
    </form>
  </div>
</body>
</html>
