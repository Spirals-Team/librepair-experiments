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

package codeu.model.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.Instant;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class ModelDataTestHelpers {

  /** Asserts that all fields on both Conversations are the same. */
  public static void assertConversationEquals(Conversation expected, Conversation actual) {
    if (expected == null) {
      assertNull(actual);
    } else {
      assertNotNull("Conversation not found", actual);
      assertEquals(expected.getId(), actual.getId());
      assertEquals(expected.getOwnerId(), actual.getOwnerId());
      assertEquals(expected.getTitle(), actual.getTitle());
      assertEquals(expected.getCreationTime(), actual.getCreationTime());
    }
  }

  /** Asserts that all fields on both Messages are the same. */
  public static void assertMessageEquals(Message expected, Message actual) {
    if (expected == null) {
      assertNull(actual);
    } else {
      assertNotNull("Message not found", actual);
      assertEquals(expected.getId(), actual.getId());
      assertEquals(expected.getConversationId(), actual.getConversationId());
      assertEquals(expected.getAuthorId(), actual.getAuthorId());
      assertEquals(expected.getContent(), actual.getContent());
      assertEquals(expected.getCreationTime(), actual.getCreationTime());
    }
  }

  /** Asserts that all fields on both Users are the same. */
  public static void assertUserEquals(User expected, User actual) {
    if (expected == null) {
      assertNull(actual);
    } else {
      assertNotNull("User not found", actual);
      assertEquals(expected.getId(), actual.getId());
      assertEquals(expected.getName(), actual.getName());
      assertEquals(expected.getPasswordHash(), actual.getPasswordHash());
      assertEquals(expected.getCreationTime(), actual.getCreationTime());
      assertEquals(expected.isAdmin(), actual.isAdmin());
      assertEquals(expected.getAboutMe(), actual.getAboutMe());
    }
  }

  /** Asserts that all fields on both Hashtags are the same. */
  public static void assertHashtagEquals(Hashtag expected, Hashtag actual) {
    if (expected == null) {
      assertNull(actual);
    } else {
      assertNotNull("Hashtag not found", actual);
      assertEquals(expected.getId(), actual.getId());
      assertEquals(expected.getCreationTime(), actual.getCreationTime());
      assertEquals(expected.getContent(), actual.getContent());
      assertEquals(expected.getConversationSource(), actual.getConversationSource());
      assertEquals(expected.getUserSource(), actual.getUserSource());
    }
  }

  /** Asserts that all fields on both Activities are the same. */
  public static void assertActivityEquals(Activity expected, Activity actual) {
    if (expected == null) {
      assertNull(actual);
    } else {
      assertNotNull("Activity not found", actual);
      assertEquals(expected.getId(), actual.getId());
      assertEquals(expected.getId(), actual.getId());
      assertEquals(expected.getOwnerId(), actual.getOwnerId());
      assertEquals(expected.getAction(), actual.getAction());
      assertEquals(expected.isPrivate(), actual.isPrivate());
      assertEquals(expected.getCreationTime(), actual.getCreationTime());
      assertEquals(expected.getThumbnail(), actual.getThumbnail());
    }
  }

  /**
   * Use this to create a fake Conversation to use in a unit test. When created it contains random
   * data in every field, and the individual methods can be used to set the test conditions. For
   * example, if the test needs specific owner ID and title, then you could do:
   *
   * <pre>
   * {
   *   &#64;code
   *   UUID fakeOwner = UUID.randomUUID();
   *   String fakeTitle = "test title 1";
   *   Conversation fakeConversation =
   *       new TestConversationBuilder().withOwnerId(fakeOwner).withTitle(fakeTitle).build();
   * }
   * </pre>
   */
  public static class TestConversationBuilder {
    private UUID id;
    private UUID ownerId;
    private String title;
    private Instant creationTime;
    private boolean isPrivate;

    public TestConversationBuilder() {
      this.id = UUID.randomUUID();
      this.ownerId = UUID.randomUUID();
      this.title = UUID.randomUUID().toString();
      this.creationTime = Instant.now();
      this.isPrivate = false;
    }

    public TestConversationBuilder withId(UUID id) {
      this.id = id;
      return this;
    }

    public TestConversationBuilder withOwnerId(UUID ownerId) {
      this.ownerId = ownerId;
      return this;
    }

    public TestConversationBuilder withTitle(String title) {
      this.title = title;
      return this;
    }

    public TestConversationBuilder withCreationTime(Instant creationTime) {
      this.creationTime = creationTime;
      return this;
    }

    public TestConversationBuilder withIsPrivate(boolean isPrivate) {
      this.isPrivate = isPrivate;
      return this;
    }

    public Conversation build() {
      return new Conversation(id, ownerId, title, creationTime, isPrivate);
    }
  }

  /**
   * Use this to create a fake Message to use in a unit test. When created it contains random data
   * in every field, and the individual methods can be used to set the test conditions. For example,
   * if the test needs specific author ID and content, then you could do:
   *
   * <pre>
   * {
   *   &#64;code
   *   UUID fakeAuthor = UUID.randomUUID();
   *   String fakeContent = "test message 1";
   *   Message fakeMessage =
   *       new TestMessageBuilder().withAuthorId(fakeAuthor).withContent(fakeContent).build();
   * }
   * </pre>
   */
  public static class TestMessageBuilder {
    private UUID id;
    private UUID conversationId;
    private UUID authorId;
    private boolean isPrivate;
    private String content;
    private Instant creationTime;


    public TestMessageBuilder() {
      this.id = UUID.randomUUID();
      this.conversationId = UUID.randomUUID();
      this.authorId = UUID.randomUUID();
      this.isPrivate = false;
      this.content = UUID.randomUUID().toString();
      this.creationTime = Instant.now();
    }

    public TestMessageBuilder withId(UUID id) {
      this.id = id;
      return this;
    }

    public TestMessageBuilder withConversationId(UUID conversationId) {
      this.conversationId = conversationId;
      return this;
    }

    public TestMessageBuilder withAuthorId(UUID authorId) {
      this.authorId = authorId;
      return this;
    }

    public TestMessageBuilder withIsPrivate(boolean isPrivate) {
      this.isPrivate = isPrivate;
      return this;
    }

    public TestMessageBuilder withContent(String content) {
      this.content = content;
      return this;
    }

    public TestMessageBuilder withCreationTime(Instant creationTime) {
      this.creationTime = creationTime;
      return this;
    }

    public Message build() {
      return new Message(id, conversationId, authorId, isPrivate, content, creationTime);
    }
  }

  /**
   * Use this to create a fake User to use in a unit test. When created it contains random data in
   * every field, and the individual methods can be used to set the test conditions. For example, if
   * the test needs specific name, then you could do:
   *
   * <pre>
   * {
   *   &#64;code
   *   String fakeName = "Alex Smith";
   *   User fakeUser = new TestUserBuilder().withName(fakeName).build();
   * }
   * </pre>
   */
  public static class TestUserBuilder {
    private UUID id;
    private String name;
    private String passwordHash;
    private Instant creationTime;
    private boolean admin;
    private String aboutMe;

    public TestUserBuilder() {
      this.id = UUID.randomUUID();
      this.name = UUID.randomUUID().toString();
      this.passwordHash = UUID.randomUUID().toString();
      this.creationTime = Instant.now();
      this.admin = false;
      this.aboutMe = UUID.randomUUID().toString();
    }

    public TestUserBuilder withId(UUID id) {
      this.id = id;
      return this;
    }

    public TestUserBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public TestUserBuilder withPasswordHash(String passwordHash) {
      this.passwordHash = passwordHash;
      return this;
    }

    public TestUserBuilder withCreationTime(Instant creationTime) {
      this.creationTime = creationTime;
      return this;
    }

    public TestUserBuilder withAdmin(boolean admin) {
      this.admin = admin;
      return this;
    }

    public TestUserBuilder withAboutMe(String aboutMe) {
      this.aboutMe = aboutMe;
      return this;
    }

    public User build() {
      User user = new User(id, name, passwordHash, creationTime);
      user.setAdmin(admin);
      user.setAboutMe(aboutMe);
      return user;
    }
  }

  /**
   * Use this to create a fake Hashtag to use in a unit test. When created it contains random data
   * in every field, and the individual methods can be used to set the test conditions. For example,
   * if the test needs specific owner ID and title, then you could do:
   *
   * <pre>
   * {
   *   &#64;code
   *   String fakeContent = "test content 1";
   *   Hashtag fakeHashtag = new TestHashtagBuilder().withContent(fakeContent).build();
   * }
   * </pre>
   */
  public static class TestHashtagBuilder {
    private UUID id;
    private String content;
    private Instant creationTime;
    private Set<String> userSource;
    private Set<String> conversationSource;

    public TestHashtagBuilder() {
      this.id = UUID.randomUUID();
      this.content = UUID.randomUUID().toString();
      this.creationTime = Instant.now();
      this.userSource = new HashSet<>();
      this.conversationSource = new HashSet<>();
    }

    public TestHashtagBuilder withId(UUID id) {
      this.id = id;
      return this;
    }

    public TestHashtagBuilder withContent(String content) {
      this.content = content;
      return this;
    }

    public TestHashtagBuilder withCreationTime(Instant creationTime) {
      this.creationTime = creationTime;
      return this;
    }

    public TestHashtagBuilder withUserSource(Set<String> userSource) {
      this.userSource = userSource;
      return this;
    }

    public TestHashtagBuilder withConversationSource(Set<String> conversationSource) {
      this.conversationSource = conversationSource;
      return this;
    }

    public Hashtag build() {
      return new Hashtag(id, content, creationTime, userSource, conversationSource);
    }
  }

  /**
   * Use this to create a fake Activity to use in a unit test. When created it contains random data
   * in every field, and the individual methods can be used to set the test conditions. For example,
   * if the test needs specific name, then you could do:
   *
   * <pre>
   * {
   *   &#64;code
   *   UUID fakeOwner = UUID.randomUUID();
   *   boolean isPrivate = false;
   *   Activity fakeActivity =
   *       new TestActivityBuilder().withOwnerId(fakeOwner).withIsPrivate(isPrivate).build();
   * }
   * </pre>
   */
  public static class TestActivityBuilder {
    private UUID id;
    private UUID ownerId;
    private Action action;
    private boolean isPrivate;
    private Instant creationTime;
    private String thumbnail;

    public TestActivityBuilder() {
      this.id = UUID.randomUUID();
      this.ownerId = UUID.randomUUID();
      this.isPrivate = false;
      this.creationTime = Instant.now();
      // default action: Registering_USER
      this.action = Action.REGISTER_USER;
      this.thumbnail = UUID.randomUUID().toString();
    }

    public TestActivityBuilder withId(UUID id) {
      this.id = id;
      return this;
    }

    public TestActivityBuilder withOwnerId(UUID ownerId) {
      this.ownerId = ownerId;
      return this;
    }

    public TestActivityBuilder withIsPrivate(boolean isPrivate) {
      this.isPrivate = isPrivate;
      return this;
    }

    public TestActivityBuilder withCreationTime(Instant creationTime) {
      this.creationTime = creationTime;
      return this;
    }

    public TestActivityBuilder withAction(Action action) {
      this.action = action;
      return this;
    }

    public TestActivityBuilder withThumbnail(String thumbnail) {
      this.thumbnail = thumbnail;
      return this;
    }

    public Activity build() {
      Activity activity = new Activity(id, ownerId, action, isPrivate, creationTime, thumbnail);
      return activity;
    }
  }
}
