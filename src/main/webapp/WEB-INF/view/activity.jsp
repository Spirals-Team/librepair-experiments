<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Activity" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.Action" %>
<%@ page import="codeu.model.data.StyleText" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.store.basic.ConversationStore" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>
<%@ page import="codeu.model.util.Util" %>
<%@ page import="java.util.UUID" %>

<%
List<Activity> activities = (List<Activity>) request.getAttribute("activities");
%>

<!DOCTYPE html>
<html>
<head>
<title>Activity Feed</title>
<%@ include file = "/header.jsp" %>

  <style>
    #activityfeed {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
    html {
      zoom:80%;
    }
    .texts {
      font-size:20px;
    }
  </style>

</head>
<body>
  <%@ include file = "/navigations.jsp" %>

    <div class="container">
      <h1>Activity Feed</h1>
      <% if (activities == null || activities.size() == 1) { %>
      <h3> Nothing happened on the site so far! </h3>
      <% } else { %>
        <h3> Here's everything that's happened on the site so far! </h3>
          <div id="activityfeed">
            <ul>
              <%
              for (Activity activity: activities) {
                if(!activity.isPrivate()) {
                  Action action = activity.getAction();
                  UUID id = activity.getId();
                  UUID idOwnerId = activity.getOwnerId();
                  String time = Util.FormatDateTime(activity.getCreationTime());
                  String name = "";
                  switch(action) {

                  case REGISTER_USER:
                    name = UserStore.getInstance().getUser(id).getName();%>
                    <li class="texts"><b><%= time %>:</b> <a href="/users/<%= name%>"><%= name %></a> joined CodeByters!</li>
                    <% break;

                  case CREATE_CONV:
                     name = UserStore.getInstance().getUser(idOwnerId).getName();
                     Conversation conv1 = ConversationStore.getInstance().getConversationById(id);%>
                     <li class="texts"><b><%= time %>:</b> <a href="/users/<%= name%>"><%= name %></a> created a new conversation:
                     <a href="/chat/<%= conv1.getTitle() %>"> <%= conv1.getTitle() %></a>.</li>
                     <% break;

                  case SEND_MESSAGE:
                     name = UserStore.getInstance().getUser(idOwnerId).getName();
                     Message mess = MessageStore.getInstance().getMessageById(id);
                     Conversation conv2 = ConversationStore.getInstance().getConversationById(mess.getConversationId());%>
                     <li class=texts><b><%= time %>:</b> <a href="/users/<%= name%>"><%= name %></a>  sent a message in
                     <a href="/chat/<%= conv2.getTitle() %>"> <%= conv2.getTitle() %> </a>: "<%= StyleText.style(mess.getContent()) %>".</li>

                  <% } %>
                <% } %>
              <% } %>
            </ul>
          </div>
          <% } %>
    </div>
</body>
</html>
