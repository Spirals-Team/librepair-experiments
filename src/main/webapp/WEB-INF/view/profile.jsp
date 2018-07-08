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
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.time.Instant" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="java.util.UUID" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.Hashtag" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>
<%@ page import="codeu.model.store.basic.HashtagStore" %>
<%@ page import="codeu.model.data.StyleText" %>
<%@ page import="codeu.model.util.Util" %>

<%
User activeUser = (User) request.getAttribute("user");
String profileOwner = (String) request.getAttribute("profileOwner");
List<Message> messagesByUser = (List<Message>) request.getAttribute("messagesByUser");
List<User> users = (List<User>) request.getAttribute("users");
Map<String,Hashtag> tags = (Map<String,Hashtag>) request.getAttribute("hashtags");
List<String> hashWords = new ArrayList<String>();
%>

<!DOCTYPE html>
<html>
<title>My Profile</title>
<%@ include file = "/header.jsp" %>
<body>
  <style>
    #chat {
      background-color: white;
      height: 500px;
      width: 750px;
      overflow-y: scroll
    }
    html {
      zoom:80%;
    }
    .texts {
      font-size:20px;
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };
  </script>

  <%@ include file = "/navigations.jsp" %>

  <div id="container">
    <% if (request.getAttribute("error") != null) { %>
      <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <% if (request.getSession().getAttribute("user") != null) { %>

      <h1><%=profileOwner%>'s Profile Page</h1>
      <hr/>
      <strong>About <%=profileOwner%></strong><br>
      <p><%=StyleText.style(activeUser.getAboutMe())%></p>

      <!--
          Only show the editable fields if the logged in user is the
          owner of this profile.
      -->
      <% if (request.getSession().getAttribute("user").equals(profileOwner)) { %>
        <form action="/users/<%=request.getSession().getAttribute("user") %>" method="POST">
          <div class="form-group">
            <label class="form-control-label">Edit Your About Me (Only you can see this):</label>
            <textarea rows="5" cols="75" name="About Me" placeholder="I'm currently a student at..."></textarea>
          </div>
          <button type="submit" class="btn">submit</button>
        </form>
        <hr/>
      <% } %>

      <h1><%=profileOwner%>'s Sent Messages</h1>
      <div id="chat">
        <ul>
          <% for (Message message : messagesByUser) {
            Instant time = message.getCreationTime();
            String creation = Util.FormatDateTime(time);
          %>
            <li class="texts"><strong><%= creation %>:</strong> <%= StyleText.style(message.getContent()) %></li>
          <% } %>
        </ul>
      </div>
      <hr/>
    <% } %>

    <h1>Hashtags in this Profile</h1>
    <ul>
      <% for (User user: users) { %>
        <%
          Pattern pattern = Pattern.compile("#(\\S+)");
          Matcher mat = pattern.matcher(user.getAboutMe());
          while (mat.find())
            hashWords.add(mat.group(1));
        %>
      <% } %>
      <%= hashWords %>
    </ul>
  </div>
</body>
</html>
