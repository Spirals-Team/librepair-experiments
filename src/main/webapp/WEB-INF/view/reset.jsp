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

<%
String isReset = (String) request.getAttribute("isReset");
%>

<!DOCTYPE html>
<html>
<title>Reset Password</title>
<%@ include file = "/header.jsp" %>
<body>
  <style>
    html {
      zoom:80%;
    }
  </style>
  
  <script>
    $(document).ready(function(){
      $('#password, #password_two').on('keyup', function(){
        if(($('#password').val() != $('#password_two').val())
           && $('#password_two').val() != ""){
        $('#error').css("display", "block");
        }else{
        $('#error').css("display", "none");
        }
      });
    });
  </script>

  <%@ include file = "/navigations.jsp" %>

  <div class="container">
    <h1>Reset Password</h1>
    <% if (request.getAttribute("error") != null) { %>
          <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>
    <% if (request.getAttribute("sent") != null) { %>
            <h2 style="color:green"><%= request.getAttribute("sent") %></h2>
    <% } %>

    <% if (isReset.equals("true")) { %>
      <form class="form-group" action="/reset" method="POST">
        <label class="form-control-label" for="password">New Password: </label>
        <input pattern="\S{3,}" required title="3 characters minimum: No spaces"
        class="form-control" type="password" name="password" id="password" required>
        </br>
        <label class="form-control-label" for="password_two">Confirm Password: </label>
        <p id="error" style="display:none; color:red"> The passwords you entered do not match. Please re-enter your password.</p>
        <input pattern="\S{3,}" required title="3 characters minimum: No spaces"
        class="form-control" type="password" name="password_two" id="password_two" required>
        </br>
        <button type="submit" class="btn">Reset</button>
        <input type="hidden" name="username" value= <%= request.getAttribute("username") %> />
      </form>
    <% } else { %>
       <p>Lost your password? Please enter your username. If you registered an email, you will receive a temporary code to use to reset password</p>
       <form class="form-group" action="/reset" method="POST">
          <label class="form-control-label" for="username">Username: </label>
          <input pattern="\S{3,}" required title="3 characters minimum: No spaces"
          class="form-control" type="text" name="username" id="username" required>
          </br>
          <button type="submit" class="btn">Reset</button>
       </form>
    <% } %>
  </div>
</body>
</html>
