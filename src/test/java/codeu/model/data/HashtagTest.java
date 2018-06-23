package codeu.model.data;

import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class HashtagTest {

  @Test
  public void testCreate() {
    UUID id = UUID.randomUUID();
    String content = "Football";
    Instant creation = Instant.now();

    Hashtag hashtag =
        new Hashtag(id, content, creation, new HashSet<String>(), new HashSet<String>());

    Assert.assertEquals(id, hashtag.getId());
    Assert.assertEquals(content.toLowerCase(), hashtag.getContent());
    Assert.assertEquals(creation, hashtag.getCreationTime());
  }

  @Test
  public void testAddUser() {

    String content = "Football";
    Hashtag hashtag =
        new Hashtag(
            UUID.randomUUID(),
            content,
            Instant.now(),
            new HashSet<String>(),
            new HashSet<String>());

    UUID userID1 = UUID.randomUUID();
    UUID userID2 = UUID.randomUUID();

    Assert.assertEquals(0, hashtag.getUserSource().length());
    Assert.assertTrue(hashtag.addUser(userID1));
    Assert.assertTrue(hashtag.addUser(userID2));
    Assert.assertFalse(hashtag.addUser(userID1));

    Assert.assertTrue(hashtag.getUserSource().indexOf(',') != -1);
  }

  @Test
  public void testAddConversation() {
    String content = "Football";
    Hashtag hashtag =
        new Hashtag(
            UUID.randomUUID(),
            content,
            Instant.now(),
            new HashSet<String>(),
            new HashSet<String>());

    UUID convID1 = UUID.randomUUID();
    UUID convID2 = UUID.randomUUID();

    Assert.assertEquals(0, hashtag.getUserSource().length());
    Assert.assertTrue(hashtag.addUser(convID1));
    Assert.assertTrue(hashtag.addUser(convID2));
    Assert.assertFalse(hashtag.addUser(convID1));

    Assert.assertTrue(hashtag.getUserSource().indexOf(',') != -1);
  }
}
