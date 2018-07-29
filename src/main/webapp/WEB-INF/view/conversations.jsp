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
<%@ page import="java.util.Map" %>
<%@ page import="codeu.model.data.Hashtag" %>
<%@ page import="codeu.model.store.basic.HashtagStore" %>
<%
List<Conversation> conversations = (List<Conversation>) request.getAttribute("conversations");
Map<String,Hashtag> allHashtags = (Map<String,Hashtag>) request.getAttribute("hashtagMap");
%>

<!DOCTYPE html>
<html>
<title>Conversations</title>
<%@ include file = "/header.jsp" %>
<body>
  <style>
    html {
      zoom:80%;
    }
  </style>

  <%@ include file = "/navigations.jsp" %>

  <div class="container">

    <% if (request.getAttribute("error") != null) { %>
      <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <% if (request.getSession().getAttribute("user") != null) { %>
      <h1>New Conversation</h1>
      <form class="form-group" action="/conversations" method="POST">
        <label class="form-control-label">Title:</label>
        <input pattern="\S{3,}" required title="3 characters minimum: No spaces"
        class="form-control"type="text" name="conversationTitle" required>
        <div class="form-check">
          <label class="form-check-label" for="checkF">
          Private
          </label>
          <input class="form-check-input" type="checkbox" value="true" id="checkF" name="isPrivate">
        </div>
        <button type="submit" class="btn">Create</button>
      </form>

      <hr/>
    <% } %>

    <h1>Conversations</h1>

    <% if (conversations == null || conversations.isEmpty()) { %>
      <p>Create a conversation to get started.</p>
    <% } else { %>
      <ul class="mdl-list">
        <% for (Conversation conversation : conversations) { %>
            <% if (!conversation.isPrivate()) { %>
              <li><a href="/chat/<%= conversation.getTitle() %>">
                  <%= conversation.getTitle() %></a></li>
           <% } else if(request.getSession().getAttribute("user") != null &&
                        conversation.check(request.getSession().getAttribute("user").toString())) { %>
                   <li><a href="/chat/<%= conversation.getTitle() %>">&#x1F512;<%= conversation.getTitle() %></a></li>
              <% } %>
        <% } %>
      </ul>
    <% } %>
    <hr/>
    
    <h1>Hashtag Conversations</h1>

    <% if (allHashtags == null || allHashtags.isEmpty()) { %>
      <p>Create a Hash-Tag conversation to get started.</p>
    <% } else { %>
      <ul class="mdl-list">
        <% for (Conversation conversation : conversations) { %>
        	<% if (conversation.getTitle().contains("*")){ %>
            	<% if (!conversation.isPrivate()) { %>
              		<li><a href="/chat/<%= conversation.getTitle() %>"> 
                  		<%= conversation.getTitle() %></a></li>
           		<% } else if(request.getSession().getAttribute("user") != null &&
                        		conversation.check(request.getSession().getAttribute("user").toString())) { %>
                   		<li><a href="/chat/<%= conversation.getTitle() %>">&#x1F512;<%= conversation.getTitle().replaceAll("*", "#") %></a></li>
              	<% } %>
           <% } %>
        <% } %>
      </ul>
    <% } %>
    <hr/>

  </div>
</body>
</html>
