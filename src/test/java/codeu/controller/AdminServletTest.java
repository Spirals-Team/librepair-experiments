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

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AdminServletTest {

  private AdminServlet adminServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private ConversationStore mockConversationStore;
  private MessageStore mockMessageStore;
  private UserStore mockUserStore;

  @Before
  public void setup() {
    adminServlet = new AdminServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
        .thenReturn(mockRequestDispatcher);

    mockConversationStore = Mockito.mock(ConversationStore.class);
    adminServlet.setConversationStore(mockConversationStore);

    mockMessageStore = Mockito.mock(MessageStore.class);
    adminServlet.setMessageStore(mockMessageStore);

    mockUserStore = Mockito.mock(UserStore.class);
    adminServlet.setUserStore(mockUserStore);
  }

  @Test
  public void testDoGet_TotalUser_NoUser() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("totalUsers", 0);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_TotalUser_MoreThanOneUser() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    fakeUserList.add(new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now()));
    fakeUserList.add(new User(UUID.randomUUID(), "User01", "FakeHashedPassword02", Instant.now()));
    fakeUserList.add(new User(UUID.randomUUID(), "User02", "FakeHashedPassword03", Instant.now()));
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("totalUsers", fakeUserList.size());
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_TotalConversations_NoConversations() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<Conversation> fakeConversationList = new ArrayList<>();
    Mockito.when(mockConversationStore.getAllConversations()).thenReturn(fakeConversationList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("totalConversations", 0);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_TotalConversations_MoreThanOneConversations()
      throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<Conversation> fakeConversationList = new ArrayList<>();
    fakeConversationList.add(
        new Conversation(UUID.randomUUID(), UUID.randomUUID(), "Conversation1", Instant.now(), false));
    fakeConversationList.add(
        new Conversation(UUID.randomUUID(), UUID.randomUUID(), "Conversation2", Instant.now(), false));
    fakeConversationList.add(
        new Conversation(UUID.randomUUID(), UUID.randomUUID(), "Conversation3", Instant.now(),
                false));
    Mockito.when(mockConversationStore.getAllConversations()).thenReturn(fakeConversationList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("totalConversations", fakeConversationList.size());
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_TotalMessages_NoMessages() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<Message> fakeMessageList = new ArrayList<>();
    Mockito.when(mockMessageStore.getTotalMessages()).thenReturn(fakeMessageList.size());

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("totalMessages", 0);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_TotalMessages_MoreThanOneMessages() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<Message> fakeMessageList = new ArrayList<>();
    fakeMessageList.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), false, "Message1", Instant.now()));
    fakeMessageList.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), false, "Message2", Instant.now()));
    fakeMessageList.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), false, "Message3", Instant.now()));
    Mockito.when(mockMessageStore.getTotalMessages()).thenReturn(fakeMessageList.size());

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("totalMessages", fakeMessageList.size());
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_MostActive_NoUser() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("mostActive", "No Users.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_MostActive_OneUser() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    User user1 = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    fakeUserList.add(user1);

    List<Message> fakeMessageList_User1 = new ArrayList<>();
    fakeMessageList_User1.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message1", Instant.now()));
    fakeMessageList_User1.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message2", Instant.now()));

    Mockito.when(mockMessageStore.getMessagesByUser(user1.getId()))
        .thenReturn(fakeMessageList_User1);
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("mostActive", "Admin01");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_MostActive_MoreThanOneUser_DifferentMessageCount()
      throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    User user1 = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    fakeUserList.add(user1);
    User user2 = new User(UUID.randomUUID(), "Admin02", "FakeHashedPassword02", Instant.now());
    fakeUserList.add(user2);
    User user3 = new User(UUID.randomUUID(), "Admin03", "FakeHashedPassword03", Instant.now());
    fakeUserList.add(user3);

    List<Message> fakeMessageList_User1 = new ArrayList<>();
    fakeMessageList_User1.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message1", Instant.now()));
    fakeMessageList_User1.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message2", Instant.now()));
    List<Message> fakeMessageList_User2 = new ArrayList<>();
    fakeMessageList_User2.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message1", Instant.now()));
    fakeMessageList_User2.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message2", Instant.now()));
    fakeMessageList_User2.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message3", Instant.now()));
    List<Message> fakeMessageList_User3 = new ArrayList<>();
    fakeMessageList_User3.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message1", Instant.now()));

    Mockito.when(mockMessageStore.getMessagesByUser(user1.getId()))
        .thenReturn(fakeMessageList_User1);
    Mockito.when(mockMessageStore.getMessagesByUser(user2.getId()))
        .thenReturn(fakeMessageList_User2);
    Mockito.when(mockMessageStore.getMessagesByUser(user3.getId()))
        .thenReturn(fakeMessageList_User3);
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("mostActive", "Admin02");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_MostActive_MoreThanOneUser_SameMessageCount()
      throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    User user1 = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    fakeUserList.add(user1);
    User user2 = new User(UUID.randomUUID(), "Admin02", "FakeHashedPassword02", Instant.now());
    fakeUserList.add(user2);
    User user3 = new User(UUID.randomUUID(), "Admin03", "FakeHashedPassword03", Instant.now());
    fakeUserList.add(user3);

    List<Message> fakeMessageList_User1 = new ArrayList<>();
    fakeMessageList_User1.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message1", Instant.now()));
    List<Message> fakeMessageList_User2 = new ArrayList<>();
    fakeMessageList_User2.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message2", Instant.now()));
    List<Message> fakeMessageList_User3 = new ArrayList<>();
    fakeMessageList_User3.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message3", Instant.now()));

    Mockito.when(mockMessageStore.getMessagesByUser(user1.getId()))
        .thenReturn(fakeMessageList_User1);
    Mockito.when(mockMessageStore.getMessagesByUser(user2.getId()))
        .thenReturn(fakeMessageList_User2);
    Mockito.when(mockMessageStore.getMessagesByUser(user3.getId()))
        .thenReturn(fakeMessageList_User3);
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("mostActive", "Admin01");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_NewestUser_NoUser() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("newestUser", "No Users.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_NewestUser_OneUser() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    fakeUserList.add(new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now()));
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("newestUser", "Admin01");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_NewestUser_MoreThanOneUser() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    fakeUserList.add(new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now()));
    fakeUserList.add(new User(UUID.randomUUID(), "Admin02", "FakeHashedPassword02", Instant.now()));
    fakeUserList.add(new User(UUID.randomUUID(), "Admin03", "FakeHashedPassword03", Instant.now()));
    fakeUserList.add(new User(UUID.randomUUID(), "Admin04", "FakeHashedPassword04", Instant.now()));
    fakeUserList.add(new User(UUID.randomUUID(), "Admin05", "FakeHashedPassword05", Instant.now()));
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("newestUser", "Admin05");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_WordiestUser_MoreThanOneUser_DifferentCharCount_Test1()
      throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    User user1 = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    fakeUserList.add(user1);
    User user2 = new User(UUID.randomUUID(), "Admin02", "FakeHashedPassword02", Instant.now());
    fakeUserList.add(user2);
    User user3 = new User(UUID.randomUUID(), "Admin03", "FakeHashedPassword03", Instant.now());
    fakeUserList.add(user3);

    List<Message> fakeMessageList_User1 = new ArrayList<>();
    fakeMessageList_User1.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message1", Instant.now()));
    List<Message> fakeMessageList_User2 = new ArrayList<>();
    fakeMessageList_User2.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message22", Instant.now()));
    List<Message> fakeMessageList_User3 = new ArrayList<>();
    fakeMessageList_User3.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message333", Instant.now()));

    Mockito.when(mockMessageStore.getMessagesByUser(user1.getId()))
        .thenReturn(fakeMessageList_User1);
    Mockito.when(mockMessageStore.getMessagesByUser(user2.getId()))
        .thenReturn(fakeMessageList_User2);
    Mockito.when(mockMessageStore.getMessagesByUser(user3.getId()))
        .thenReturn(fakeMessageList_User3);
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("wordiestUser", "Admin03");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_WordiestUser_MoreThanOneUser_DifferentCharCount_Test2()
      throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    User user1 = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    fakeUserList.add(user1);
    User user2 = new User(UUID.randomUUID(), "Admin02", "FakeHashedPassword02", Instant.now());
    fakeUserList.add(user2);
    User user3 = new User(UUID.randomUUID(), "Admin03", "FakeHashedPassword03", Instant.now());
    fakeUserList.add(user3);

    List<Message> fakeMessageList_User1 = new ArrayList<>();
    fakeMessageList_User1.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message1", Instant.now()));
    fakeMessageList_User1.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message2", Instant.now()));
    List<Message> fakeMessageList_User2 = new ArrayList<>();
    fakeMessageList_User2.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message2", Instant.now()));
    fakeMessageList_User2.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "LongMessage1", Instant.now()));
    List<Message> fakeMessageList_User3 = new ArrayList<>();
    fakeMessageList_User3.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message3", Instant.now()));

    Mockito.when(mockMessageStore.getMessagesByUser(user1.getId()))
        .thenReturn(fakeMessageList_User1);
    Mockito.when(mockMessageStore.getMessagesByUser(user2.getId()))
        .thenReturn(fakeMessageList_User2);
    Mockito.when(mockMessageStore.getMessagesByUser(user3.getId()))
        .thenReturn(fakeMessageList_User3);
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("wordiestUser", "Admin02");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_WordiestUser_MoreThanOneUser_DifferentCharCount_WithWhiteSpace()
      throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    User user1 = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    fakeUserList.add(user1);
    User user2 = new User(UUID.randomUUID(), "Admin02", "FakeHashedPassword02", Instant.now());
    fakeUserList.add(user2);
    User user3 = new User(UUID.randomUUID(), "Admin03", "FakeHashedPassword03", Instant.now());
    fakeUserList.add(user3);

    List<Message> fakeMessageList_User1 = new ArrayList<>();
    fakeMessageList_User1.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message1", Instant.now()));
    fakeMessageList_User1.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message2", Instant.now()));
    List<Message> fakeMessageList_User2 = new ArrayList<>();
    fakeMessageList_User2.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "M e s s a g e 2", Instant.now()));
    fakeMessageList_User2.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "M e s s a g e 3", Instant.now()));
    List<Message> fakeMessageList_User3 = new ArrayList<>();
    fakeMessageList_User3.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message4", Instant.now()));

    Mockito.when(mockMessageStore.getMessagesByUser(user1.getId()))
        .thenReturn(fakeMessageList_User1);
    Mockito.when(mockMessageStore.getMessagesByUser(user2.getId()))
        .thenReturn(fakeMessageList_User2);
    Mockito.when(mockMessageStore.getMessagesByUser(user3.getId()))
        .thenReturn(fakeMessageList_User3);
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("wordiestUser", "Admin01");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_WordiestUser_MoreThanOneUser_SameCharCount()
      throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    User user1 = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    fakeUserList.add(user1);
    User user2 = new User(UUID.randomUUID(), "Admin02", "FakeHashedPassword02", Instant.now());
    fakeUserList.add(user2);
    User user3 = new User(UUID.randomUUID(), "Admin03", "FakeHashedPassword03", Instant.now());
    fakeUserList.add(user3);

    List<Message> fakeMessageList_User1 = new ArrayList<>();
    fakeMessageList_User1.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message1", Instant.now()));
    List<Message> fakeMessageList_User2 = new ArrayList<>();
    fakeMessageList_User2.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message2", Instant.now()));
    List<Message> fakeMessageList_User3 = new ArrayList<>();
    fakeMessageList_User3.add(
        new Message(
            UUID.randomUUID(), UUID.randomUUID(), user1.getId(), false, "Message3", Instant.now()));

    Mockito.when(mockMessageStore.getMessagesByUser(user1.getId()))
        .thenReturn(fakeMessageList_User1);
    Mockito.when(mockMessageStore.getMessagesByUser(user2.getId()))
        .thenReturn(fakeMessageList_User2);
    Mockito.when(mockMessageStore.getMessagesByUser(user3.getId()))
        .thenReturn(fakeMessageList_User3);
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("wordiestUser", "Admin01");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_WordiestUser_NoUser() throws IOException, ServletException {
    User user = new User(UUID.randomUUID(), "Admin01", "FakeHashedPassword01", Instant.now());
    user.setAdmin(true);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(user);
    List<User> fakeUserList = new ArrayList<>();
    Mockito.when(mockUserStore.getUsers()).thenReturn(fakeUserList);

    adminServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequest).setAttribute("wordiestUser", "No Users.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}