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
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.StyleText" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
List<User> users = (List<User>) request.getAttribute("users");
%>

<!DOCTYPE html>
<html>
<head>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <title><%= conversation.getTitle() %></title>
  <%@ include file = "/header.jsp" %>

  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
    html {
      zoom:80%;
    }
    .listMessages {
      font-size:20px;
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };

    <% if (request.getSession().getAttribute("user") != null) { %>
       var authorLogin = "<%= request.getSession().getAttribute("user")%>";
            $(document).ready(function(){
              $("li.listMessages > span").on({
                mouseenter: function(){
                  $(this).css("cursor", "default");
                  var author = $($(this).siblings()).find('a').text();
                  if(author == authorLogin) {
                    $(this).css("cursor", "not-allowed");
                    $(this).css("color", "red");
                    $(this).css("text-decoration", "line-through");
                  }
                },
                mouseleave: function(){
                  var author = $($(this).siblings()).find('a').text();
                  if (author == authorLogin) {
                    $(this).css("color", "#444");
                    $(this).css("text-decoration", "none");
                  }
                },
                click: function(){
                  var author = $($(this).siblings()).find('a').text();
                  if (author == authorLogin) {
                    if(confirm("Are you sure you want to delete this message?")){
                      var listMessage = $(this).parent(".listMessages");
                      $(listMessage).fadeOut("slow");
                      $.post("", {
                            deletedMessageId: ($(listMessage).attr("value"))
                      });
                    }
                  }
                }
              });
            });
    <% } %>
  </script>
</head>
<body onload="scrollChat()">
  <%@ include file = "/navigations.jsp" %>

  <div class="container">
    <h1><%= conversation.getTitle() %>
        <a href="" style="float: right">&#8635;</a></h1>
    <hr/>
    <% if (conversation.isPrivate()) { %>
      <!--In case this is a private conv, let's add users!-->
      <%@ include file = "/add.jsp" %>
    <% } %>
    <div>
      <div id="chat">
      <ul>
        <% for (Message message : messages) {
          String author = UserStore.getInstance()
              .getUser(message.getAuthorId()).getName(); %>
          <li class="listMessages" value="<%=message.getId()%>"><strong><a id="author" href="/users/<%= author%>"><%= author %></a>:</strong> <span class="messageOutput" ><%= StyleText.style(message.getContent()) %></%></span></li>
        <% } %>
      </ul>
    </div>

    <% if (request.getSession().getAttribute("user") != null) { %>
      <!--adding emoji menu-->
      <%@ include file = "/emoji.jsp" %>

      <form class="form-group" action="/chat/<%= conversation.getTitle() %>" method="POST">
        <input class="form-control" id="input" type="text" style="font-size:20px" name="messageInput" required>
        <button type="submit" class="btn btn-default">Send</button>
      </form>
    <% } else { %>
      <p><a href="/login">Login</a> to send a message.</p>
    <% } %>
      </div>
    <hr/>
  </div>
</body>
</html>
