package codeu.model.store.basic;

import codeu.model.data.Action;
import codeu.model.data.Activity;
import static codeu.model.data.ModelDataTestHelpers.assertActivityEquals;
import codeu.model.data.ModelDataTestHelpers.TestActivityBuilder;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ActivityStoreTest {

  private ActivityStore activityStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  private final Activity ACTIVITY_ONE =
          new TestActivityBuilder().withAction(Action.REGISTER_USER).build();
  private final Activity ACTIVITY_TWO =
          new TestActivityBuilder().withAction(Action.CREATE_CONV).build();
  private final Activity ACTIVITY_THREE =
          new TestActivityBuilder().withAction(Action.REGISTER_USER).build();

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    activityStore = ActivityStore.getTestInstance(mockPersistentStorageAgent);

    final List<Activity> activityList = new ArrayList<>();
    activityList.add(ACTIVITY_ONE);
    activityList.add(ACTIVITY_TWO);
    activityList.add(ACTIVITY_THREE);
    activityStore.setActivities(activityList);
  }

  @Test
  public void testGetAllActivities() {
    List<Activity> activities = activityStore.getAllActivities();
    Assert.assertEquals(3, activities.size());
  }

  @Test
  public void testAddActivity() {
    UUID newAct = UUID.randomUUID();
    Activity inputActivity =
            new TestActivityBuilder()
                    .withId(newAct)
                    .withAction(Action.REGISTER_USER)
                    .withIsPublic(true)
                    .build();

    activityStore.addActivity(inputActivity);
    Activity resultActivity = activityStore.getActivityWithId(newAct);

    assertActivityEquals(inputActivity, resultActivity);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(inputActivity);
  }

  @Test
  public void testGetActivitiesWithType() {
    List<Activity> resultActivity = activityStore.getActivitiesWithAction(Action.REGISTER_USER);
    Assert.assertEquals(2, resultActivity.size());
  }

  @Test
  public void testGetActivitiesWithType_notFound() {
    List<Activity> resultActivity =
            activityStore.getActivitiesWithAction(Action.SEND_MESSAGE);
    Assert.assertNull(resultActivity);
  }

  @Test
  public void testgetActivityWithId() {
    UUID activity1 = UUID.randomUUID();
    Activity activity_one =
            new TestActivityBuilder()
                    .withId(activity1)
                    .withAction(Action.SEND_MESSAGE)
                    .withIsPublic(true)
                    .build();

    UUID activity2 = UUID.randomUUID();
    Activity activity_two =
            new TestActivityBuilder()
                    .withId(activity2)
                    .withAction(Action.REGISTER_USER)
                    .withIsPublic(true)
                    .build();

    activityStore.addActivity(activity_one);
    activityStore.addActivity(activity_two);

    Activity resultAct = activityStore.getActivityWithId(activity2);

    assertActivityEquals(resultAct, activity_two);
  }
}