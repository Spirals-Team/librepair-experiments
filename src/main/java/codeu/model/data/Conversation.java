// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

import codeu.model.store.basic.UserStore;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Class representing a conversation, which can be thought of as a chat room. Conversations are
 * created by a User and contain Messages.
 */
public class Conversation {
  private final UUID id;
  private final UUID owner;
  private final Instant creation;
  private final String title;
  private final boolean isPrivate;
  private Set<String> users;



  /**
   * Constructs a new Conversation.
   *
   * @param id the ID of this Conversation
   * @param owner the ID of the User who created this Conversation
   * @param title the title of this Conversation
   * @param creation the creation time of this Conversation
   * @param isPrivate how accessible is this activity.
   */
  public Conversation(UUID id, UUID owner, String title, Instant creation, boolean isPrivate) {
    this.id = id;
    this.owner = owner;
    this.creation = creation;
    this.title = title;
    this.isPrivate = isPrivate;
    // Only create a list of users if the conversation is private.
    if(isPrivate) users = new HashSet<>();
  }

  /** Returns the ID of this Conversation. */
  public UUID getId() {
    return id;
  }

  /** Returns the ID of the User who created this Conversation. */
  public UUID getOwnerId() {
    return owner;
  }

  /** Returns the title of this Conversation. */
  public String getTitle() {
    return title;
  }

  /** Returns the String representation of the users that have acess to the conversation. */
  public String getUsers() {
    if(!isPrivate) return null;
    return String.join(",", this.users);
  }

  /** Returns true if the conversation is private. */
  public boolean isPrivate() {
    return isPrivate;
  }

  /**
   * Add the ID of the user to the users. Returns true if the String representation of the User
   * is added; returns false if the String representation of users Does NOT exists.
   */
  public Boolean addUser(UUID userID) {
    if(!isPrivate) return false;
    return users.add(userID.toString());
  }

  /**
   * Checks if this user in the list based on their name.
   */
  public boolean check(String name) {
    User user = UserStore.getInstance().getUser(name);
    for (String id: users){
      if(id.equals(user.getId().toString())){
        return true;
      }
    }
    return false;
  }

  /**
   * Add all the users that should have access to the conversation.
   */
  public void setUsers(Set<String> users) {
    this.users = users;
  }

  /** Returns the creation time of this Conversation. */
  public Instant getCreationTime() {
    return creation;
  }
}
