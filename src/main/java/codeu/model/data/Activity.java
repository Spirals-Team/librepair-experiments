package codeu.model.data;

import codeu.model.util.Util;
import java.time.Instant;
import java.util.UUID;

/**
 * Class representing an activity (creating user, message, or conversation). Any creation done by a
 * user prompts the creation of an activity object.
 */
public class Activity implements Comparable<Activity>{
  private final UUID id;
  private final UUID ownerId;
  private final Action action;
  private boolean isPrivate;
  private final Instant creation;
  private final String thumbnail;

  /**
   * Constructs a new activity.
   *
   * @param id the ID of this Activity
   * @param ownerId the ID of the owner of this activity
   * @param action the action of the activity (join, send, create)
   * @param isPrivate how accessible is this activity.
   * @param creation the creation time of this Conversation
   * @param thumbnail a short summary about the activity
   */
  public Activity(
      UUID id, UUID ownerId, Action action, boolean isPrivate, Instant creation, String thumbnail) {
    this.id = id;
    this.action = action;
    this.ownerId = ownerId;
    this.isPrivate = isPrivate;
    this.creation = creation;
    this.thumbnail = thumbnail;
  }

  public Activity(User u) {
    this(
        u.getId(),
        // Owner and activity have the same ID.
        u.getId(),
        Action.REGISTER_USER,
        true,
        u.getCreationTime(),
        Util.FormatDateTime(u.getCreationTime()) + ": " + u.getName() + " joined CodeByters!");
  }

  public Activity(Conversation c) {
    this(
        c.getId(),
        c.getOwnerId(),
        Action.CREATE_CONV,
        true,
        c.getCreationTime(),
        Util.FormatDateTime(c.getCreationTime())
            + ": [USER] created a new public conversation = \""
            + c.getTitle()
            + "\".");
  }

  public Activity(Message m) {
    this(
        m.getId(),
        m.getAuthorId(),
        Action.SEND_MESSAGE,
        true,
        m.getCreationTime(),
        Util.FormatDateTime(m.getCreationTime())
            + ": [USER] sent a message in [Conversation]: "
            + m.getContent()
            + ".");
  }

  /** Returns the ID of this activity. */
  public UUID getId() {
    return id;
  }

  /** Returns the ID of the User who created this activity. */
  public UUID getOwnerId() {
    return ownerId;
  }

  /** Returns the action of this activity. */
  public Action getAction() {
    return action;
  }

  /** Returns true if the activity is private. */
  public boolean isPrivate() {
    return isPrivate;
  }

  /** Returns the creation time of this activity. */
  public Instant getCreationTime() {
    return creation;
  }

  /** Returns a small summary of the activity */
  public String getThumbnail() {
    return thumbnail;
  }

  /** Sets the accessibility of activity. */
  public void setIsPrivate(Boolean isPrivate) {
    this.isPrivate = isPrivate;
  }

  @Override
  public int compareTo(Activity o) {
    Instant secondTime = o.getCreationTime();
    return (secondTime.compareTo(creation));
  }
}
