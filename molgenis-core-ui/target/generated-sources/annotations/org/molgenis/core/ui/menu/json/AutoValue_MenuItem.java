
package org.molgenis.core.ui.menu.json;

import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nullable;
import org.molgenis.web.UiMenuItemType;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_MenuItem extends MenuItem {

  private final String id;
  private final String label;
  private final String href;
  private final UiMenuItemType type;
  private final List<MenuItem> items;

  AutoValue_MenuItem(
      String id,
      String label,
      @Nullable String href,
      UiMenuItemType type,
      @Nullable List<MenuItem> items) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (label == null) {
      throw new NullPointerException("Null label");
    }
    this.label = label;
    this.href = href;
    if (type == null) {
      throw new NullPointerException("Null type");
    }
    this.type = type;
    this.items = items;
  }

  @Override
  String getId() {
    return id;
  }

  @Override
  String getLabel() {
    return label;
  }

  @Nullable
  @Override
  String getHref() {
    return href;
  }

  @Override
  UiMenuItemType getType() {
    return type;
  }

  @Nullable
  @Override
  List<MenuItem> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return "MenuItem{"
        + "id=" + id + ", "
        + "label=" + label + ", "
        + "href=" + href + ", "
        + "type=" + type + ", "
        + "items=" + items
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof MenuItem) {
      MenuItem that = (MenuItem) o;
      return (this.id.equals(that.getId()))
           && (this.label.equals(that.getLabel()))
           && ((this.href == null) ? (that.getHref() == null) : this.href.equals(that.getHref()))
           && (this.type.equals(that.getType()))
           && ((this.items == null) ? (that.getItems() == null) : this.items.equals(that.getItems()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.label.hashCode();
    h *= 1000003;
    h ^= (href == null) ? 0 : this.href.hashCode();
    h *= 1000003;
    h ^= this.type.hashCode();
    h *= 1000003;
    h ^= (items == null) ? 0 : this.items.hashCode();
    return h;
  }

}
