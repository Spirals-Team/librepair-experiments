
package com.arcao.geocaching.api.data.bookmarks;

import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_BookmarkList extends BookmarkList {

  private final int id;
  private final String guid;
  private final String name;
  private final String description;
  private final int itemCount;
  private final boolean shared;
  private final boolean publicList;
  private final boolean archived;
  private final boolean special;
  private final int type;

  private AutoValue_BookmarkList(
      int id,
      String guid,
      String name,
      @Nullable String description,
      int itemCount,
      boolean shared,
      boolean publicList,
      boolean archived,
      boolean special,
      int type) {
    this.id = id;
    this.guid = guid;
    this.name = name;
    this.description = description;
    this.itemCount = itemCount;
    this.shared = shared;
    this.publicList = publicList;
    this.archived = archived;
    this.special = special;
    this.type = type;
  }

  @Override
  public int id() {
    return id;
  }

  @Override
  public String guid() {
    return guid;
  }

  @Override
  public String name() {
    return name;
  }

  @Nullable
  @Override
  public String description() {
    return description;
  }

  @Override
  public int itemCount() {
    return itemCount;
  }

  @Override
  public boolean shared() {
    return shared;
  }

  @Override
  public boolean publicList() {
    return publicList;
  }

  @Override
  public boolean archived() {
    return archived;
  }

  @Override
  public boolean special() {
    return special;
  }

  @Override
  public int type() {
    return type;
  }

  @Override
  public String toString() {
    return "BookmarkList{"
        + "id=" + id + ", "
        + "guid=" + guid + ", "
        + "name=" + name + ", "
        + "description=" + description + ", "
        + "itemCount=" + itemCount + ", "
        + "shared=" + shared + ", "
        + "publicList=" + publicList + ", "
        + "archived=" + archived + ", "
        + "special=" + special + ", "
        + "type=" + type
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof BookmarkList) {
      BookmarkList that = (BookmarkList) o;
      return (this.id == that.id())
           && (this.guid.equals(that.guid()))
           && (this.name.equals(that.name()))
           && ((this.description == null) ? (that.description() == null) : this.description.equals(that.description()))
           && (this.itemCount == that.itemCount())
           && (this.shared == that.shared())
           && (this.publicList == that.publicList())
           && (this.archived == that.archived())
           && (this.special == that.special())
           && (this.type == that.type());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id;
    h *= 1000003;
    h ^= this.guid.hashCode();
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= this.itemCount;
    h *= 1000003;
    h ^= this.shared ? 1231 : 1237;
    h *= 1000003;
    h ^= this.publicList ? 1231 : 1237;
    h *= 1000003;
    h ^= this.archived ? 1231 : 1237;
    h *= 1000003;
    h ^= this.special ? 1231 : 1237;
    h *= 1000003;
    h ^= this.type;
    return h;
  }

  private static final long serialVersionUID = 2322622811124797813L;

  static final class Builder extends BookmarkList.Builder {
    private Integer id;
    private String guid;
    private String name;
    private String description;
    private Integer itemCount;
    private Boolean shared;
    private Boolean publicList;
    private Boolean archived;
    private Boolean special;
    private Integer type;
    Builder() {
    }
    @Override
    public BookmarkList.Builder id(int id) {
      this.id = id;
      return this;
    }
    @Override
    public BookmarkList.Builder guid(String guid) {
      if (guid == null) {
        throw new NullPointerException("Null guid");
      }
      this.guid = guid;
      return this;
    }
    @Override
    public BookmarkList.Builder name(String name) {
      if (name == null) {
        throw new NullPointerException("Null name");
      }
      this.name = name;
      return this;
    }
    @Override
    public BookmarkList.Builder description(@Nullable String description) {
      this.description = description;
      return this;
    }
    @Override
    public BookmarkList.Builder itemCount(int itemCount) {
      this.itemCount = itemCount;
      return this;
    }
    @Override
    public BookmarkList.Builder shared(boolean shared) {
      this.shared = shared;
      return this;
    }
    @Override
    public BookmarkList.Builder publicList(boolean publicList) {
      this.publicList = publicList;
      return this;
    }
    @Override
    public BookmarkList.Builder archived(boolean archived) {
      this.archived = archived;
      return this;
    }
    @Override
    public BookmarkList.Builder special(boolean special) {
      this.special = special;
      return this;
    }
    @Override
    public BookmarkList.Builder type(int type) {
      this.type = type;
      return this;
    }
    @Override
    public BookmarkList build() {
      String missing = "";
      if (this.id == null) {
        missing += " id";
      }
      if (this.guid == null) {
        missing += " guid";
      }
      if (this.name == null) {
        missing += " name";
      }
      if (this.itemCount == null) {
        missing += " itemCount";
      }
      if (this.shared == null) {
        missing += " shared";
      }
      if (this.publicList == null) {
        missing += " publicList";
      }
      if (this.archived == null) {
        missing += " archived";
      }
      if (this.special == null) {
        missing += " special";
      }
      if (this.type == null) {
        missing += " type";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_BookmarkList(
          this.id,
          this.guid,
          this.name,
          this.description,
          this.itemCount,
          this.shared,
          this.publicList,
          this.archived,
          this.special,
          this.type);
    }
  }

}
