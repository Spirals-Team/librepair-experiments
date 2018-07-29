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

import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.Conversation;
import codeu.model.data.Hashtag;
import codeu.model.data.HashtagCreator;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.HashtagStore;
import codeu.model.store.basic.UserStore;

/** Servlet class responsible for the conversations page. */
public class ConversationServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;
  
  /** Store class that gives access to HashtagStore. */
  private HashtagStore hashtagStore;

  /**
   * Set up state for handling conversation-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setHashtagStore(HashtagStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }
  
  /**
   * Sets the HashtagStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setHashtagStore(HashtagStore hashtagStore) {
    this.hashtagStore = hashtagStore;
  }

  /**
   * This function fires when a user navigates to the conversations page. It gets all of the
   * conversations from the model and forwards to conversations.jsp for rendering the list.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    List<Conversation> conversations = conversationStore.getAllConversations();
    Map<String, Hashtag> hashtagMap = hashtagStore.getAllHashtags();
    request.setAttribute("conversations", conversations);
    request.setAttribute("hashtagMap", hashtagMap);
    request.getRequestDispatcher("/WEB-INF/view/conversations.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the form on the conversations page. It gets the
   * logged-in username from the session and the new conversation title from the submitted form
   * data. It uses this to create a new Conversation object that it adds to the model.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");

    if (username == null) {
      // user is not logged in, don't let them create a conversation
      response.sendRedirect("/conversations");
      return;
    }
    username = username.toLowerCase();

    User user = userStore.getUser(username);
    if (user == null) {
      // user was not found, don't let them create a conversation
      response.sendRedirect("/conversations");
      return;
    }

    String conversationTitle = request.getParameter("conversationTitle");
    conversationTitle = conversationTitle.toLowerCase();
    
    String hashTag = "";
    String titleWithoutHash = conversationTitle;
    
    if (conversationTitle.contains("#")) { //if contains hash 
    	int hashIndex = conversationTitle.indexOf("#"); //index of hash
    	String startHash = conversationTitle.substring(hashIndex);
    	int spaceIndex = startHash.length(); //if conversation title contains hash-tag only
    	if (spaceIndex > 1) {  // no hash-tag is '#' is at the end of the conversation-title
    		hashTag = conversationTitle.substring(hashIndex + 1); //find hash-tag substring
    	}
    	titleWithoutHash = titleWithoutHash.replaceAll("#", "*"); //remove all hash
    } // works only for the first hash-tag in a conversation title
    	
    if (!titleWithoutHash.matches("[\\w*]*")) {
      request.setAttribute("error", "Please enter only letters and numbers. (Only one Hashtag allowed.)");
      request.getRequestDispatcher("/WEB-INF/view/conversations.jsp").forward(request, response);
      return;
    } 

    if (conversationStore.isTitleTaken(titleWithoutHash)) {
      // conversation title is already taken, just go into that conversation instead of creating a
      // new one
      response.sendRedirect("/chat/" + titleWithoutHash);
      return;
    }

    boolean isPrivate = Boolean.valueOf(request.getParameter("isPrivate"));
    Conversation conversation =
        new Conversation(UUID.randomUUID(), user.getId(), titleWithoutHash, Instant.now(), (isPrivate?true:false));
    if(isPrivate) {
      // Add the first user, which is the creator.
      conversation.addUser(user.getId());
      // For admin purpose, add the admin too.
      conversation.addUser(userStore.getUser("admin01").getId());
    }
    
    conversationStore.addConversation(conversation);
    
    if (!(hashTag.equals(""))) { //if hashtag not equal to "";
    Hashtag newHashtag = new Hashtag(UUID.randomUUID(), hashTag, Instant.now(),
    		new HashSet<String>(), new HashSet<String>());
    newHashtag.addConversation(conversationStore.getConversationWithTitle(titleWithoutHash).getId());
    hashtagStore.addHashtag(newHashtag, HashtagCreator.CONVERSATION, conversation.getId());
    }
    
    response.sendRedirect("/chat/" + titleWithoutHash);
  }
}
