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

package codeu.model.store.persistence;

import codeu.model.data.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This class handles all interactions with Google App Engine's Datastore service. On startup it
 * sets the state of the applications's data objects from the current contents of its Datastore. It
 * also performs writes of new or modified objects back to the Datastore.
 */
public class PersistentDataStore {

  // Handle to Google AppEngine's Datastore service.
  private DatastoreService datastore;

  /**
   * Constructs a new PersistentDataStore and sets up its state to begin loading objects from the
   * Datastore service.
   */
  public PersistentDataStore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /**
   * Loads all User objects from the Datastore service and returns them in a List.
   *
   * @throws codeu.model.store.persistence.PersistentDataStoreException if an error was detected
   *     during the load from the Datastore service
   */
  public List<User> loadUsers() throws PersistentDataStoreException {

    List<User> users = new ArrayList<>();

    // Retrieve all users from the datastore.
    Query query = new Query("chat-users");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        String userName = (String) entity.getProperty("username");
        String passwordHash = (String) entity.getProperty("password_hash");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        User user = new User(uuid, userName, passwordHash, creationTime);
        String aboutMe = (String) entity.getProperty("about_me");
        String email = (String) entity.getProperty("email");
        HashSet<String> hashtags =
            new HashSet<String>(
                Arrays.asList((((String) (entity.getProperty("hashtag_set"))).split(", "))));
        user.setHashtagSet(hashtags);
        user.setAboutMe(aboutMe);
        user.setEmail(email);
        // Forces an admin to have "true" for the admin field
        if (BCrypt.checkpw("AdminPass203901", user.getPasswordHash().toString())) {
          user.setAdmin(true);
        }
        users.add(user);
      } catch (Exception e) {
        // In a production environment, errors should be very rare.
        // Errors which may
        // occur include network errors, Datastore service errors,
        // authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return users;
  }

  /**
   * Loads all Conversation objects from the Datastore service and returns them in a List, sorted in
   * ascending order by creation time.
   *
   * @throws codeu.model.store.persistence.PersistentDataStoreException if an error was detected
   *     during the load from the Datastore service
   */
  public List<Conversation> loadConversations() throws PersistentDataStoreException {

    List<Conversation> conversations = new ArrayList<>();

    // Retrieve all conversations from the datastore.
    Query query = new Query("chat-conversations").addSort("creation_time", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID ownerUuid = UUID.fromString((String) entity.getProperty("owner_uuid"));
        String title = (String) entity.getProperty("title");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        Boolean isPrivate = Boolean.valueOf((String) entity.getProperty("isPrivate"));
        Conversation conversation =
            new Conversation(uuid, ownerUuid, title, creationTime, isPrivate);
        if (isPrivate) {
          Set<String> users =
              new HashSet<String>(
                  Arrays.asList(((String) (entity.getProperty("users"))).split(",")));
          conversation.setUsers(users);
        }
        conversations.add(conversation);
      } catch (Exception e) {
        // In a production environment, errors should be very rare.
        // Errors which may
        // occur include network errors, Datastore service errors,
        // authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return conversations;
  }

  /**
   * Loads all Message objects from the Datastore service and returns them in a List, sorted in
   * ascending order by creation time.
   *
   * @throws codeu.model.store.persistence.PersistentDataStoreException if an error was detected
   *     during the load from the Datastore service
   */
  public List<Message> loadMessages() throws PersistentDataStoreException {

    List<Message> messages = new ArrayList<>();

    // Retrieve all messages from the datastore.
    Query query = new Query("chat-messages").addSort("creation_time", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID conversationUuid = UUID.fromString((String) entity.getProperty("conv_uuid"));
        UUID authorUuid = UUID.fromString((String) entity.getProperty("author_uuid"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String content = (String) entity.getProperty("content");
        Boolean isPrivate = Boolean.valueOf((String) entity.getProperty("isPrivate"));
        Message message =
            new Message(uuid, conversationUuid, authorUuid, isPrivate, content, creationTime);
        messages.add(message);
      } catch (Exception e) {
        // In a production environment, errors should be very rare.
        // Errors which may
        // occur include network errors, Datastore service errors,
        // authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return messages;
  }

  /**
   * Loads all Activity objects from the Datastore service and returns them in a List.
   *
   * @throws codeu.model.store.persistence.PersistentDataStoreException if an error was detected
   *     during the load from the Datastore service
   */
  public List<Activity> loadActivities() throws PersistentDataStoreException {

    List<Activity> activities = new ArrayList<>();

    // Retrieve all activities from the datastore.
    Query query = new Query("chat-activities");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID uuidOwner = UUID.fromString((String) entity.getProperty("owner_uuid"));
        Action action = Action.valueOf((String) entity.getProperty("action"));
        Boolean isPrivate = Boolean.valueOf((String) entity.getProperty("isPrivate"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String thumbnail = (String) entity.getProperty("thumbnail");
        Activity activity =
            new Activity(uuid, uuidOwner, action, isPrivate, creationTime, thumbnail);
        activities.add(activity);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return activities;
  }

  public HashMap<String, Hashtag> loadHashtags() throws PersistentDataStoreException {

    HashMap<String, Hashtag> hashtags = new HashMap<String, Hashtag>();
    // Retrieve all hashtags from the datastore.
    Query query = new Query("chat-hashtags").addSort("creation_time", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        String content = (String) entity.getProperty("content");
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        Set<String> userSource =
            new HashSet<String>(
                Arrays.asList(((String) (entity.getProperty("user_source"))).split(", ")));
        Set<String> convSource =
            new HashSet<String>(
                Arrays.asList(((String) (entity.getProperty("conv_source"))).split(", ")));
        Hashtag hashtag = new Hashtag(uuid, content, creationTime, userSource, convSource);
        hashtags.put(content.toLowerCase(), hashtag);
      } catch (Exception e) {
        // In a production environment, errors should be very rare.
        // Errors which may
        // occur include network errors, Datastore service errors,
        // authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }
    return hashtags;
  }

  /** Write a User object to the Datastore service. */
  public void writeThrough(User user) {
    Entity userEntity = new Entity("chat-users", user.getId().toString());
    userEntity.setProperty("uuid", user.getId().toString());
    userEntity.setProperty("username", user.getName());
    userEntity.setProperty("email", user.getEmail());
    userEntity.setProperty("password_hash", user.getPasswordHash());
    userEntity.setProperty("creation_time", user.getCreationTime().toString());
    userEntity.setProperty("about_me", user.getAboutMe());
    userEntity.setProperty("hashtag_set", user.getHashtagNames());
    datastore.put(userEntity);
  }

  /** Write a Message object to the Datastore service. */
  public void writeThrough(Message message) {
    Entity messageEntity = new Entity("chat-messages", message.getId().toString());
    messageEntity.setProperty("uuid", message.getId().toString());
    messageEntity.setProperty("conv_uuid", message.getConversationId().toString());
    messageEntity.setProperty("author_uuid", message.getAuthorId().toString());
    messageEntity.setProperty("isPrivate", String.valueOf(message.isPrivate()));
    messageEntity.setProperty("content", message.getContent());
    messageEntity.setProperty("creation_time", message.getCreationTime().toString());
    datastore.put(messageEntity);
  }

  /** Write a Conversation object to the Datastore service. */
  public void writeThrough(Conversation conversation) {
    Entity conversationEntity = new Entity("chat-conversations", conversation.getId().toString());
    conversationEntity.setProperty("uuid", conversation.getId().toString());
    conversationEntity.setProperty("owner_uuid", conversation.getOwnerId().toString());
    conversationEntity.setProperty("title", conversation.getTitle());
    conversationEntity.setProperty("creation_time", conversation.getCreationTime().toString());
    conversationEntity.setProperty("isPrivate", String.valueOf(conversation.isPrivate()));
    if (conversation.isPrivate()) conversationEntity.setProperty("users", conversation.getUsers());
    datastore.put(conversationEntity);
  }

  /** Write a Hashtag object to the Datastore service. */
  public void writeThrough(Hashtag hashtag) {
    Entity hashtagEntity = new Entity("chat-hashtags", hashtag.getId().toString());
    hashtagEntity.setProperty("uuid", hashtag.getId().toString());
    hashtagEntity.setProperty("content", hashtag.getContent());
    hashtagEntity.setProperty("creation_time", hashtag.getCreationTime().toString());
    hashtagEntity.setProperty("user_source", hashtag.getUserSource());
    hashtagEntity.setProperty("conv_source", hashtag.getConversationSource());
    datastore.put(hashtagEntity);
  }

  /** Write an Activity object to the Datastore service. */
  public void writeThrough(Activity activity) {
    Entity activityEntity = new Entity("chat-activities", activity.getId().toString());
    activityEntity.setProperty("uuid", activity.getId().toString());
    activityEntity.setProperty("owner_uuid", activity.getOwnerId().toString());
    activityEntity.setProperty("action", activity.getAction().name());
    activityEntity.setProperty("isPrivate", String.valueOf(activity.isPrivate()));
    activityEntity.setProperty("creation_time", activity.getCreationTime().toString());
    activityEntity.setProperty("thumbnail", activity.getThumbnail().toString());
    datastore.put(activityEntity);
  }

  /** Delete a Message object from the Datastore service. */
  public void deleteFrom(Message message) {
    // Retrieve all messages from the datastore.
    Query query = new Query("chat-messages").addSort("creation_time", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      String id = (String) entity.getProperty("uuid");
      if (id.equals(message.getId().toString())) {
        datastore.delete(entity.getKey());
      }
    }
  }

  /** Delete an Activity object from the Datastore service. */
  public void deleteFrom(Activity activity) {
    // Retrieve all activities from the datastore.
    Query query = new Query("chat-activities").addSort("creation_time", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      String id = (String) entity.getProperty("uuid");
      if (id.equals(activity.getId().toString())) {
        datastore.delete(entity.getKey());
      }
    }
  }
}
