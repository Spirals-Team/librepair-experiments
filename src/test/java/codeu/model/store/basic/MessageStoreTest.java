package codeu.model.store.basic;

import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentDataStore;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class MessageStoreTest {

  private MessageStore messageStore;
  private UserStore userStore;
  private PersistentStorageAgent mockPersistentStorageAgent;
  private PersistentDataStore mockPersistentDataStore;

  private final User USER_ONE =
	      new User(UUID.randomUUID(), "test_username_one", "test_password_one", Instant.ofEpochMilli(1000));
  private final User USER_TWO =
	      new User(UUID.randomUUID(), "test_username_two", "test_password_two", Instant.ofEpochMilli(2000));
  private final User USER_THREE =
	      new User(UUID.randomUUID(), "test_username_three", "test_password_three", Instant.ofEpochMilli(3000));
  private final UUID CONVERSATION_ID_ONE = UUID.randomUUID();
  
  private final Message MESSAGE_ONE =
      new Message(
          USER_ONE.getId(),
          CONVERSATION_ID_ONE,
          USER_TWO.getId(),
          "message one",
          Instant.ofEpochMilli(1000));
  private final Message MESSAGE_TWO =
      new Message(
    	  USER_TWO.getId(),
          CONVERSATION_ID_ONE,
          USER_THREE.getId(),
          "message two",
          Instant.ofEpochMilli(2000));
  private final Message MESSAGE_THREE =
      new Message(
    	  USER_THREE.getId(),
          UUID.randomUUID(),
          USER_ONE.getId(),
          "message three",
          Instant.ofEpochMilli(3000));

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    messageStore = MessageStore.getTestInstance(mockPersistentStorageAgent);

    final List<Message> messageList = new ArrayList<>();
    messageList.add(MESSAGE_ONE);
    messageList.add(MESSAGE_TWO);
    messageList.add(MESSAGE_THREE);
    messageStore.setMessages(messageList);
  }

  @Test
  public void testGetMessagesInConversation() {
    List<Message> resultMessages = messageStore.getMessagesInConversation(CONVERSATION_ID_ONE);

    Assert.assertEquals(2, resultMessages.size());
    assertEquals(MESSAGE_ONE, resultMessages.get(0));
    assertEquals(MESSAGE_TWO, resultMessages.get(1));
  }

  @Test
  public void testAddMessage() {
    UUID inputConversationId = UUID.randomUUID();
    Message inputMessage =
        new Message(
            UUID.randomUUID(),
            inputConversationId,
            UUID.randomUUID(),
            "test message",
            Instant.now());

    Mockito.when(userStore.getInstance()).thenReturn(userStore.getInstance());
    messageStore.addMessage(inputMessage);
    Message resultMessage = messageStore.getMessagesInConversation(inputConversationId).get(0);

    assertEquals(inputMessage, resultMessage);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(inputMessage);
  }

  private void assertEquals(Message expectedMessage, Message actualMessage) {
    Assert.assertEquals(expectedMessage.getId(), actualMessage.getId());
    Assert.assertEquals(expectedMessage.getConversationId(), actualMessage.getConversationId());
    Assert.assertEquals(expectedMessage.getAuthorId(), actualMessage.getAuthorId());
    Assert.assertEquals(expectedMessage.getContent(), actualMessage.getContent());
    Assert.assertEquals(expectedMessage.getCreationTime(), actualMessage.getCreationTime());
  }
}
