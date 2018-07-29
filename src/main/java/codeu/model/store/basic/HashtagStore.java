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

package codeu.model.store.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import codeu.model.data.Hashtag;
import codeu.model.data.HashtagCreator;
import codeu.model.store.persistence.PersistentStorageAgent;

public class HashtagStore {

  /** Singleton instance of HashtagStore. */
  private static HashtagStore instance;

  /**
   * Returns the singleton instance of HashtagStore that should be shared between all Servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static HashtagStore getInstance() {
    if (instance == null) {
      instance = new HashtagStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static HashtagStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new HashtagStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Users from and saving Users to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Hashtags. */
  private Map<String, Hashtag> map;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private HashtagStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    map = new HashMap<String, Hashtag>();
  }

  /**
   * Add a new Hashtag to the current set of Hashtags known to the applications. The UUID, id,
   * represents either the ID of the user or the ID of the conversation.
   *
   * <p>Precondition: content should not contain any symbols, the case of the content does not
   * matter.
   */
  public void addHashtag(Hashtag hashtag, HashtagCreator creator, UUID id) {
    String content = hashtag.getContent().toLowerCase();
    // If the content already exist in the map; overwrite the parameter
    // hashtag with this stored one.
    if (map.containsKey(content)) {
      hashtag = map.get(content);
    }
    // If the content does not exist in the map; the parameter hashtag will
    // be added.
    switch (creator) {
      case USER:
        hashtag.addUser(id);
      case CONVERSATION:
        hashtag.addConversation(id);
    }
    this.map.remove(content);
    this.map.put(content, hashtag);
    persistentStorageAgent.writeThrough(hashtag);
  }

  /** Sets the HashMap of Hashtags stored by this HashtagStore based on the input HashMap. */
  public void setHashtags(Map<String, Hashtag> map) {
    this.map = map;
  }

  /** Access the current set of Hashtags known to the application. */
  public Map<String, Hashtag> getAllHashtags() {
    return map;
  }

  /** Returns the corresponding Hashtag based on the name of the hashtag. */
  public Hashtag findHashtag(String hashtagName) {
    return map.get(hashtagName);
  }

  public void updateHashtag(Hashtag newHashtag) {
    persistentStorageAgent.writeThrough(newHashtag);
  }
}
