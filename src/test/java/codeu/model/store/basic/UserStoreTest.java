package codeu.model.store.basic;

import static codeu.model.data.ModelDataTestHelpers.assertUserEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import codeu.model.data.ModelDataTestHelpers.TestUserBuilder;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UserStoreTest {

  private UserStore userStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    userStore = UserStore.getTestInstance(mockPersistentStorageAgent);
  }

  @Test
  public void testGetUser_byUsername_found() {
    final User user1 = new TestUserBuilder().build();
    final User user2 = new TestUserBuilder().withName("username_two").build();
    final User user3 = new TestUserBuilder().build();
    userStore.setUsers(Arrays.asList(user1, user2, user3));

    User resultUser = userStore.getUser("username_two");

    assertUserEquals(user2, resultUser);
  }

  @Test
  public void testGetUser_byUsername_notFound() {
    final User user1 = new TestUserBuilder().build();
    final User user2 = new TestUserBuilder().build();
    final User user3 = new TestUserBuilder().build();
    userStore.setUsers(Arrays.asList(user1, user2, user3));

    User resultUser = userStore.getUser("fake username");

    assertNull(resultUser);
  }

  @Test
  public void testGetUser_byId_found() {
    final UUID randomUserId = UUID.randomUUID();
    final User user1 = new TestUserBuilder().build();
    final User user2 = new TestUserBuilder().withId(randomUserId).build();
    final User user3 = new TestUserBuilder().build();
    userStore.setUsers(Arrays.asList(user1, user2, user3));

    User resultUser = userStore.getUser(randomUserId);

    assertUserEquals(user2, resultUser);
  }

  @Test
  public void testGetUser_byId_notFound() {
    final User user1 = new TestUserBuilder().build();
    final User user2 = new TestUserBuilder().build();
    final User user3 = new TestUserBuilder().build();
    userStore.setUsers(Arrays.asList(user1, user2, user3));

    User resultUser = userStore.getUser(UUID.randomUUID());

    assertNull(resultUser);
  }

  @Test
  public void testAddUserByName() {
    userStore.addUser("test username1", "Password1", /*admin=*/ false);
    User resultUser = userStore.getUser("test username1");
    assertEquals(resultUser.getName(), "test username1");
    assertEquals(resultUser.getPasswordHash().length(), 60);
    assertFalse(resultUser.isAdmin());
    Mockito.verify(mockPersistentStorageAgent).writeThrough(resultUser);
  }

  @Test
  public void testAddUserByName_admin() {
    userStore.addUser("test username1", "Password1", true);

    User resultUser = userStore.getUser("test username1");
    assertEquals(resultUser.getName(), "test username1");
    assertEquals(resultUser.getPasswordHash().length(), 60);
    assertTrue(resultUser.isAdmin());
    Mockito.verify(mockPersistentStorageAgent).writeThrough(resultUser);
  }

  @Test
  public void testAddUser() {
    final List<User> userList = new ArrayList<>();
    userList.add(new TestUserBuilder().build());
    userList.add(new TestUserBuilder().build());
    userStore.setUsers(userList);

    final User inputUser = new TestUserBuilder().withName("test_username").build();
    userStore.addUser(inputUser);

    User resultUser = userStore.getUser("test_username");
    assertEquals(inputUser, resultUser);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(inputUser);
  }

  @Test
  public void testIsUserRegistered_true() {
    final User user1 = new TestUserBuilder().build();
    final User user2 = new TestUserBuilder().withName("username_two").build();
    final User user3 = new TestUserBuilder().build();
    userStore.setUsers(Arrays.asList(user1, user2, user3));

    assertTrue(userStore.isUserRegistered("username_two"));
  }

  @Test
  public void testIsUserRegistered_false() {
    final User user1 = new TestUserBuilder().build();
    final User user2 = new TestUserBuilder().build();
    final User user3 = new TestUserBuilder().build();
    userStore.setUsers(Arrays.asList(user1, user2, user3));

    assertFalse(userStore.isUserRegistered("fake username"));
  }

  @Test
  public void testGetAdmins() {
    final User user1 = new TestUserBuilder().withAdmin(true).build();
    final User user2 = new TestUserBuilder().withAdmin(false).build();
    final User user3 = new TestUserBuilder().withAdmin(true).build();
    userStore.setUsers(Arrays.asList(user1, user2, user3));

    List<User> resultUsers = userStore.getAdmins();

    boolean foundInitialAdmin = false;
    assertEquals(3, resultUsers.size());
    Map<UUID, User> resultUsersSet = new HashMap<>();
    for (User resultUser : resultUsers) {
      if (resultUser.getName().equals("Admin01")) {
        assertFalse(foundInitialAdmin);
        foundInitialAdmin = true;
      } else {
        resultUsersSet.put(resultUser.getId(), resultUser);
      }
    }
    assertUserEquals(user1, resultUsersSet.get(user1.getId()));
    assertUserEquals(user3, resultUsersSet.get(user3.getId()));
    assertTrue(foundInitialAdmin);
  }
}
