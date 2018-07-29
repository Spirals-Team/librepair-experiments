package codeu.model.store.basic;

import static codeu.model.data.ModelDataTestHelpers.TestHashtagBuilder;
import static codeu.model.data.ModelDataTestHelpers.assertHashtagEquals;

import codeu.model.data.Hashtag;
import codeu.model.data.HashtagCreator;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class HashtagStoreTest {
  private HashtagStore hashtagStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    hashtagStore = HashtagStore.getTestInstance(mockPersistentStorageAgent);
  }

  @Test
  public void testSetAndGetHashtags() {
    final HashMap<String, Hashtag> hashtags = new HashMap<String, Hashtag>();

    final Hashtag hash1 = new TestHashtagBuilder().withContent("Soccer").build();
    final Hashtag hash2 = new TestHashtagBuilder().withContent("Swim").build();

    hashtags.put(hash1.getContent(), hash1);
    hashtags.put(hash2.getContent(), hash2);
    hashtagStore.setHashtags(hashtags);

    Map<String, Hashtag> resultHashtags = hashtagStore.getAllHashtags();
    Assert.assertEquals(hashtags, resultHashtags);
  }

  @Test
  public void testAddHashtagNotContain() {
    final HashMap<String, Hashtag> hashtags = new HashMap<String, Hashtag>();
    final Hashtag hash1 = new TestHashtagBuilder().withContent("Soccer").build();
    final Hashtag hash2 = new TestHashtagBuilder().withContent("Swim").build();

    UUID user1 = UUID.randomUUID();
    hash1.addUser(user1);
    UUID user2 = UUID.randomUUID();
    hash2.addUser(user2);

    hashtags.put(hash1.getContent(), hash1);
    hashtags.put(hash2.getContent(), hash2);

    hashtagStore.addHashtag(hash1, HashtagCreator.USER, user1);
    hashtagStore.addHashtag(hash2, HashtagCreator.USER, user2);

    Map<String, Hashtag> resultHashtags = hashtagStore.getAllHashtags();
    assertHashtagEquals(hashtags.get(hash1.getContent()), resultHashtags.get(hash1.getContent()));
    assertHashtagEquals(hashtags.get(hash2.getContent()), resultHashtags.get(hash2.getContent()));
  }

  @Test
  public void testAddHashtagContain() {
    final HashMap<String, Hashtag> hashtags = new HashMap<String, Hashtag>();
    final Hashtag hash1 = new TestHashtagBuilder().withContent("Soccer").build();
    UUID user1 = UUID.randomUUID();
    hash1.addUser(user1);
    UUID user2 = UUID.randomUUID();
    hash1.addUser(user2);

    hashtags.put(hash1.getContent(), hash1);

    hashtagStore.addHashtag(hash1, HashtagCreator.USER, user1);
    hashtagStore.addHashtag(hash1, HashtagCreator.USER, user2);

    Map<String, Hashtag> resultHashtags = hashtagStore.getAllHashtags();
    assertHashtagEquals(hashtags.get(hash1.getContent()), resultHashtags.get(hash1.getContent()));
  }
}
