/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
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
package org.sonar.server.component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import org.sonar.api.i18n.I18n;
import org.sonar.api.resources.Scopes;
import org.sonar.api.utils.System2;
import org.sonar.core.component.ComponentKeys;
import org.sonar.core.util.Uuids;
import org.sonar.db.DbClient;
import org.sonar.db.DbSession;
import org.sonar.db.component.ComponentDto;
import org.sonar.server.es.ProjectIndexer;
import org.sonar.server.es.ProjectIndexer.Cause;
import org.sonar.server.favorite.FavoriteUpdater;
import org.sonar.server.permission.PermissionTemplateService;

import static java.util.Arrays.asList;
import static org.sonar.api.resources.Qualifiers.PROJECT;
import static org.sonar.core.component.ComponentKeys.isValidModuleKey;
import static org.sonar.server.ws.WsUtils.checkRequest;

public class ComponentUpdater {

  private final DbClient dbClient;
  private final I18n i18n;
  private final System2 system2;
  private final PermissionTemplateService permissionTemplateService;
  private final FavoriteUpdater favoriteUpdater;
  private final Collection<ProjectIndexer> projectIndexers;

  public ComponentUpdater(DbClient dbClient, I18n i18n, System2 system2,
    PermissionTemplateService permissionTemplateService, FavoriteUpdater favoriteUpdater,
    ProjectIndexer... projectIndexers) {
    this.dbClient = dbClient;
    this.i18n = i18n;
    this.system2 = system2;
    this.permissionTemplateService = permissionTemplateService;
    this.favoriteUpdater = favoriteUpdater;
    this.projectIndexers = asList(projectIndexers);
  }

  /**
   * - Create component
   * - Apply default permission template
   * - Add component to favorite if the component has the 'Project Creators' permission
   * - Index component if es indexes
   */
  public ComponentDto create(DbSession dbSession, NewComponent newComponent, @Nullable Long userId) {
    checkKeyFormat(newComponent.qualifier(), newComponent.key());
    ComponentDto componentDto = createRootComponent(dbSession, newComponent);
    removeDuplicatedProjects(dbSession, componentDto.getKey());
    handlePermissionTemplate(dbSession, componentDto, newComponent.getOrganizationUuid(), userId);
    dbSession.commit();
    index(componentDto);
    return componentDto;
  }

  private ComponentDto createRootComponent(DbSession session, NewComponent newComponent) {
    checkBranchFormat(newComponent.qualifier(), newComponent.branch());
    String keyWithBranch = ComponentKeys.createKey(newComponent.key(), newComponent.branch());
    checkRequest(!dbClient.componentDao().selectByKey(session, keyWithBranch).isPresent(),
      formatMessage("Could not create %s, key already exists: %s", newComponent.qualifier(), keyWithBranch));

    String uuid = Uuids.create();
    ComponentDto component = new ComponentDto()
      .setOrganizationUuid(newComponent.getOrganizationUuid())
      .setUuid(uuid)
      .setUuidPath(ComponentDto.UUID_PATH_OF_ROOT)
      .setRootUuid(uuid)
      .setModuleUuid(null)
      .setModuleUuidPath(ComponentDto.UUID_PATH_SEPARATOR + uuid + ComponentDto.UUID_PATH_SEPARATOR)
      .setProjectUuid(uuid)
      .setKey(keyWithBranch)
      .setDeprecatedKey(keyWithBranch)
      .setName(newComponent.name())
      .setLongName(newComponent.name())
      .setScope(Scopes.PROJECT)
      .setQualifier(newComponent.qualifier())
      .setCreatedAt(new Date(system2.now()));
    dbClient.componentDao().insert(session, component);
    return component;
  }

  /**
   * On MySQL, as PROJECTS.KEE is not unique, if the same project is provisioned multiple times, then it will be duplicated in the database.
   * So, after creating a project, we commit, and we search in the db if their are some duplications and we remove them.
   *
   * SONAR-6332
   */
  private void removeDuplicatedProjects(DbSession session, String projectKey) {
    List<ComponentDto> duplicated = dbClient.componentDao().selectComponentsHavingSameKeyOrderedById(session, projectKey);
    for (int i = 1; i < duplicated.size(); i++) {
      dbClient.componentDao().delete(session, duplicated.get(i).getId());
    }
  }

  private void handlePermissionTemplate(DbSession dbSession, ComponentDto componentDto, String organizationUuid, @Nullable Long userId) {
    permissionTemplateService.applyDefault(dbSession, organizationUuid, componentDto, userId);
    if (componentDto.qualifier().equals(PROJECT)
      && permissionTemplateService.hasDefaultTemplateWithPermissionOnProjectCreator(dbSession, organizationUuid, componentDto)) {
      favoriteUpdater.add(dbSession, componentDto, userId);
    }
  }

  private void checkKeyFormat(String qualifier, String kee) {
    checkRequest(isValidModuleKey(kee), formatMessage("Malformed key for %s: %s. Allowed characters are alphanumeric, '-', '_', '.' and ':', with at least one non-digit.",
      qualifier, kee));
  }

  private void checkBranchFormat(String qualifier, @Nullable String branch) {
    checkRequest(branch == null || ComponentKeys.isValidBranch(branch),
      formatMessage("Malformed branch for %s: %s. Allowed characters are alphanumeric, '-', '_', '.' and '/', with at least one non-digit.", qualifier, branch));
  }

  private String formatMessage(String message, String qualifier, String key) {
    return String.format(message, i18n.message(Locale.getDefault(), "qualifier." + qualifier, "Project"), key);
  }

  private void index(ComponentDto project) {
    projectIndexers.forEach(i -> i.indexProject(project.uuid(), Cause.PROJECT_CREATION));
  }
}
