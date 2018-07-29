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

import codeu.model.data.Message;
import codeu.model.data.ModelDataTestHelpers.TestMessageBuilder;
import codeu.model.data.ModelDataTestHelpers.TestUserBuilder;
import codeu.model.data.User;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import codeu.model.store.basic.HashtagStore;

public class ProfileServletTest {

  private ProfileServlet profileServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private MessageStore mockMessageStore;
  private UserStore mockUserStore;
  private HashtagStore mockHashtagStore;

  @Before
  public void setup() {
    profileServlet = new ProfileServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp"))
        .thenReturn(mockRequestDispatcher);

    mockMessageStore = Mockito.mock(MessageStore.class);
    profileServlet.setMessageStore(mockMessageStore);

    mockUserStore = Mockito.mock(UserStore.class);
    profileServlet.setUserStore(mockUserStore);

    mockHashtagStore = Mockito.mock(HashtagStore.class);
    profileServlet.setHashtagStore(mockHashtagStore);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    User fakeUser = new TestUserBuilder().withName("test_user").build();
    Mockito.when(mockRequest.getRequestURI()).thenReturn("/users/test_user");
    Mockito.when(mockUserStore.getUser("test_user")).thenReturn(fakeUser);

    List<Message> fakeMessagesByUser = new ArrayList<>();
    fakeMessagesByUser.add(new TestMessageBuilder().withAuthorId(fakeUser.getId()).build());
    Mockito.when(mockMessageStore.getMessagesByUser(fakeUser.getId()))
        .thenReturn(fakeMessagesByUser);

    profileServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("messagesByUser", fakeMessagesByUser);
    Mockito.verify(mockRequest).setAttribute("profileOwner", "test_user");
    Mockito.verify(mockRequest).setAttribute("user", fakeUser);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_UserNotLoggedIn() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

    profileServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockMessageStore, Mockito.never()).addMessage(Mockito.any(Message.class));
    Mockito.verify(mockResponse).sendRedirect("/login");
  }

  @Test
  public void testDoPost_InvalidUser() throws IOException, ServletException {
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_user");
    Mockito.when(mockUserStore.getUser("test_user")).thenReturn(null);

    profileServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockMessageStore, Mockito.never()).addMessage(Mockito.any(Message.class));
    Mockito.verify(mockResponse).sendRedirect("/login");
  }

  @Test
  public void testDoPost_CleansHtmlContent() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("About Me"))
        .thenReturn("Contains <b>html</b> and <script>JavaScript</script> content.");
    Mockito.when(mockRequest.getParameter("hashtag")).thenReturn("Sports");
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_user");

    User fakeUser = new TestUserBuilder().withName("test_user").build();
    Mockito.when(mockUserStore.getUser("test_user")).thenReturn(fakeUser);

    profileServlet.doPost(mockRequest, mockResponse);

    Assert.assertEquals(mockUserStore.getUser("test_user").getAboutMe(),
        "Contains html and  content.");
    Mockito.verify(mockResponse).sendRedirect("/users/test_user");
  }
}
