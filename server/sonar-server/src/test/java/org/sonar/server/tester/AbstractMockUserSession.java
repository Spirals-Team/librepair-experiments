/*
 * SonarQube
 * Copyright (C) 2009-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.tester;

import com.google.common.collect.HashMultimap;
import java.util.List;
import java.util.Map;
import org.sonar.db.component.ComponentDto;
import org.sonar.server.user.AbstractUserSession;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

public abstract class AbstractMockUserSession<T extends AbstractMockUserSession> extends AbstractUserSession {
  private final Class<T> clazz;
  private HashMultimap<String, String> projectUuidByPermission = HashMultimap.create();
  private HashMultimap<String, String> permissionsByOrganizationUuid = HashMultimap.create();
  private Map<String, String> projectUuidByComponentUuid = newHashMap();
  private List<String> projectPermissionsCheckedByUuid = newArrayList();

  protected AbstractMockUserSession(Class<T> clazz) {
    this.clazz = clazz;
  }

  public T addProjectUuidPermissions(String projectPermission, String... projectUuids) {
    this.projectPermissionsCheckedByUuid.add(projectPermission);
    this.projectUuidByPermission.putAll(projectPermission, newArrayList(projectUuids));
    for (String projectUuid : projectUuids) {
      this.projectUuidByComponentUuid.put(projectUuid, projectUuid);
    }
    return clazz.cast(this);
  }

  public T addComponentUuidPermission(String projectPermission, String projectUuid, String componentUuid) {
    this.projectUuidByComponentUuid.put(componentUuid, projectUuid);
    addProjectUuidPermissions(projectPermission, projectUuid);
    return clazz.cast(this);
  }

  @Override
  public boolean hasComponentPermission(String permission, ComponentDto component) {
    return isRoot() || hasComponentUuidPermission(permission, component.projectUuid());
  }

  @Override
  public boolean hasComponentUuidPermission(String permission, String componentUuid) {
    if (isRoot()) {
      return true;
    }
    String projectUuid = projectUuidByComponentUuid.get(componentUuid);
    return projectUuid != null && hasProjectPermissionByUuid(permission, projectUuid);
  }

  private boolean hasProjectPermissionByUuid(String permission, String projectUuid) {
    return projectPermissionsCheckedByUuid.contains(permission) && projectUuidByPermission.get(permission).contains(projectUuid);
  }

  @Override
  public boolean hasOrganizationPermission(String organizationUuid, String permission) {
    return isRoot() || permissionsByOrganizationUuid.get(organizationUuid).contains(permission);
  }

  public T addOrganizationPermission(String organizationUuid, String permission) {
    permissionsByOrganizationUuid.put(organizationUuid, permission);
    return clazz.cast(this);
  }
}
