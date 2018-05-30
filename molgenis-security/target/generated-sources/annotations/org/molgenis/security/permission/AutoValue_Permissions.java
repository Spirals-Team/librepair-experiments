
package org.molgenis.security.permission;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Permissions extends Permissions {

  private final Set<String> ids;
  private final Map<String, Collection<String>> permissions;

  AutoValue_Permissions(
      Set<String> ids,
      Map<String, Collection<String>> permissions) {
    if (ids == null) {
      throw new NullPointerException("Null ids");
    }
    this.ids = ids;
    if (permissions == null) {
      throw new NullPointerException("Null permissions");
    }
    this.permissions = permissions;
  }

  @Override
  public Set<String> getIds() {
    return ids;
  }

  @Override
  public Map<String, Collection<String>> getPermissions() {
    return permissions;
  }

  @Override
  public String toString() {
    return "Permissions{"
        + "ids=" + ids + ", "
        + "permissions=" + permissions
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Permissions) {
      Permissions that = (Permissions) o;
      return (this.ids.equals(that.getIds()))
           && (this.permissions.equals(that.getPermissions()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.ids.hashCode();
    h *= 1000003;
    h ^= this.permissions.hashCode();
    return h;
  }

}
