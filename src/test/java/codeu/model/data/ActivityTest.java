package codeu.model.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import codeu.model.util.Util;
import java.time.Instant;
import java.util.UUID;
import org.junit.Test;

public class ActivityTest {
  @Test
  public void testCreate1() {
    UUID id = UUID.randomUUID();
    UUID ownerId = id;
    Action action = Action.REGISTER_USER;
    Boolean isPublic = true;
    Instant creation = Instant.now();
    String thumbnail = "A new user joined CodeByters!";

    Activity activity = new Activity(id, ownerId, action, isPublic, creation, thumbnail);

    assertEquals(id, activity.getId());
    assertEquals(ownerId, activity.getOwnerId());
    assertEquals(action, activity.getAction());
    assertTrue(isPublic);
    assertEquals(creation, activity.getCreationTime());
    assertEquals(thumbnail, activity.getThumbnail());
  }

  @Test
  public void testCreate2() {
    UUID id = UUID.randomUUID();
    UUID owner = UUID.randomUUID();
    Instant creation = Instant.now();

    Conversation c = new Conversation(id, owner, "Title1", creation);

    Activity activity = new Activity(c);
    activity.setIsPublic(false);

    assertEquals(id, activity.getId());
    assertEquals(owner, activity.getOwnerId());
    assertEquals("CREATE_CONV", activity.getAction().name());
    assertTrue(!activity.isPublic());
    String time =
        Util.FormatDateTime(c.getCreationTime())
            + ": [USER] created a new public conversation = \""
            + c.getTitle()
            + "\".";
    assertEquals(time, activity.getThumbnail());
  }
}
