// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.Hashtag;
import codeu.model.data.HashtagCreator;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.HashtagStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.util.Util;
import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class ProfileServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  /** Store class that gives access to Hashtags. */
  private HashtagStore hashtagStore;

  /** Set up state for handling user requests. */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setMessageStore(MessageStore.getInstance());
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
   * Sets the MessageStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Sets the HashtagStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setHashtagStore(HashtagStore hashtagStore) {
    this.hashtagStore = hashtagStore;
  }

  /**
   * This function fires when a user navigates to the profiles page. It gets the user's name from
   * the URL and then forwards to profile.jsp for rendering.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String requestUrl = request.getRequestURI();
    String profileOwner = requestUrl.substring("/users/".length());
    profileOwner = profileOwner.toLowerCase();
    
    User user = userStore.getUser(profileOwner);
    if (user == null) {
      // user is not logged in, redirect to login page
      response.sendRedirect("/login");
      return;
    }

    UUID userID = user.getId();
    if (userID == null) {
      // there is no user id, redirect to login page
      response.sendRedirect("/login");
      return;
    }

    List<Message> messagesByUser = messageStore.getMessagesByUser(userID);
    List<User> users = userStore.getUsers();
    String currentHashtags = user.getHashtagNames();
    Map<String, Hashtag> hashtagMap = hashtagStore.getAllHashtags();
    Set<String> userSet = userStore.getUsersWithSameHashtag(user, hashtagMap);

    request.setAttribute("users", users);
    request.setAttribute("messagesByUser", messagesByUser);
    request.setAttribute("profileOwner", profileOwner);
    request.setAttribute("hashtagMap", hashtagMap);
    request.setAttribute("currentHashtags", currentHashtags);
    request.setAttribute("user", user);
    request.setAttribute("userWithSameHashtags", userSet);
    request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
  }

  /**
   * This function fires when user submits the form on the profiles page. It gets the user's
   * username from the session, assigns that username to the user, collects the about me content,
   * and then redirects back to the profile page with the displayed about me information.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, redirect to login page
      response.sendRedirect("/login");
      return;
    }

    User user = userStore.getUser(username);
    if (user == null) {
      // user is not logged in, redirect to login page
      response.sendRedirect("/login");
      return;
    }

    String aboutMeContent = request.getParameter("About Me");
    String newHashtagContent = request.getParameter("hashtag");

    if (newHashtagContent != null) {
      String cleanedHashtag = Jsoup.clean(newHashtagContent, Whitelist.none());
      cleanedHashtag = cleanedHashtag.replaceAll("[^a-zA-Z0-9]", "");
      cleanedHashtag = cleanedHashtag.toLowerCase();
      Hashtag newHashtag = new Hashtag(UUID.randomUUID(), cleanedHashtag, Instant.now(),
          new HashSet<String>(), new HashSet<String>());
      hashtagStore.addHashtag(newHashtag, HashtagCreator.USER, user.getId());
      user.addHashtag(cleanedHashtag);
      newHashtag.addUser(user.getId());
      hashtagStore.updateHashtag(newHashtag);
    }

    if (aboutMeContent != null) {
      // this removes any HTML from the content
      String cleanedAboutMeContent = Jsoup.clean(aboutMeContent, Whitelist.none());
      user.setAboutMe(cleanedAboutMeContent);
    }

    userStore.updateUser(user);
    response.sendRedirect("/users/" + username);
  }
}
