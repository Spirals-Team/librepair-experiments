// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the Admin page. */
public class AdminServlet extends HttpServlet {

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Set up state for handling data collection for the Admin Page. */
  @Override
  public void init() throws ServletException {
    super.init();
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
    setUserStore(UserStore.getInstance());
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.setAttribute("totalUsers", userStore.getUsers().size());
    request.setAttribute("totalConversations", conversationStore.getAllConversations().size());
    request.setAttribute("totalMessages", messageStore.getTotalMessages());
    request.setAttribute("mostActive", getMostActiveUser());
    request.setAttribute("newestUser", getNewestUser());
    request.setAttribute("wordiestUser", getWordiestUser());
    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    response.sendRedirect("/admin");
  }

  /**
   * Returns the name of most active user. The most active user is defined by the user that sends
   * the most Messages.
   */
  public String getMostActiveUser() {
    if (userStore.getUsers().isEmpty()) {
      return "No Users.";
    }
    User currentMostActiveUser = userStore.getUsers().get(0);
    int currentMostMessages = 0;
    for (User user : userStore.getUsers()) {
      int messageCount = messageStore.getMessagesByUser(user.getId()).size();
      if (messageCount > currentMostMessages) {
        currentMostMessages = messageCount;
        currentMostActiveUser = user;
      }
    }
    return currentMostActiveUser.getName();
  }

  /** Returns the name of the most recently added User. */
  public String getNewestUser() {
    if (userStore.getUsers().isEmpty()) {
      return "No Users.";
    }
    return userStore.getUsers().get(userStore.getUsers().size() - 1).getName();
  }

  /**
   * Returns the name of the wordiest user. The wordiest user is the user that has the highest count
   * of characters in all the Messages the user sent, whitespace is excluded.
   */
  public String getWordiestUser() {
    if (userStore.getUsers().isEmpty()) {
      return "No Users.";
    }
    User currentWordiestUser = userStore.getUsers().get(0);
    int currentWordiest = 0;
    for (User user : userStore.getUsers()) {
      int charCount = 0;
      List<Message> messageList = messageStore.getMessagesByUser(user.getId());
      for (Message message : messageList) {
        String characters = message.getContent().replaceAll("\\s+", "");
        charCount += characters.length();
      }
      if (charCount > currentWordiest) {
        currentWordiestUser = user;
        currentWordiest = charCount;
      }
    }
    return currentWordiestUser.getName();
  }
}
