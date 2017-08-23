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
package org.sonar.server.setting.ws;

import javax.annotation.Nullable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.config.PropertyDefinitions;
import org.sonar.api.server.ws.WebService;
import org.sonar.api.utils.System2;
import org.sonar.db.DbClient;
import org.sonar.db.DbSession;
import org.sonar.db.DbTester;
import org.sonar.db.component.ComponentDbTester;
import org.sonar.db.component.ComponentDto;
import org.sonar.db.property.PropertyDbTester;
import org.sonar.db.property.PropertyQuery;
import org.sonar.db.user.UserDto;
import org.sonar.db.user.UserTesting;
import org.sonar.server.component.ComponentFinder;
import org.sonar.server.exceptions.BadRequestException;
import org.sonar.server.exceptions.ForbiddenException;
import org.sonar.server.i18n.I18nRule;
import org.sonar.server.tester.UserSessionRule;
import org.sonar.server.ws.TestRequest;
import org.sonar.server.ws.TestResponse;
import org.sonar.server.ws.WsActionTester;
import org.sonarqube.ws.MediaTypes;

import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.sonar.api.resources.Qualifiers.PROJECT;
import static org.sonar.api.resources.Qualifiers.VIEW;
import static org.sonar.api.web.UserRole.ADMIN;
import static org.sonar.api.web.UserRole.USER;
import static org.sonar.db.component.ComponentTesting.newProjectDto;
import static org.sonar.db.property.PropertyTesting.newComponentPropertyDto;
import static org.sonar.db.property.PropertyTesting.newGlobalPropertyDto;
import static org.sonar.db.property.PropertyTesting.newUserPropertyDto;

public class ResetActionTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Rule
  public UserSessionRule userSession = UserSessionRule.standalone();

  @Rule
  public DbTester db = DbTester.create(System2.INSTANCE);

  private I18nRule i18n = new I18nRule();
  private PropertyDbTester propertyDb = new PropertyDbTester(db);
  private ComponentDbTester componentDb = new ComponentDbTester(db);
  private DbClient dbClient = db.getDbClient();
  private DbSession dbSession = db.getSession();
  private ComponentFinder componentFinder = new ComponentFinder(dbClient);
  private PropertyDefinitions definitions = new PropertyDefinitions();
  private SettingsUpdater settingsUpdater = new SettingsUpdater(dbClient, definitions);
  private SettingValidations settingValidations = new SettingValidations(definitions, dbClient, i18n);
  private ComponentDto project;
  private ResetAction underTest = new ResetAction(dbClient, componentFinder, settingsUpdater, userSession, definitions, settingValidations);
  private WsActionTester ws = new WsActionTester(underTest);

  @Before
  public void setUp() throws Exception {
    project = componentDb.insertComponent(newProjectDto(db.organizations().insert()));
  }

  @Test
  public void remove_global_setting() throws Exception {
    logInAsSystemAdministrator();
    definitions.addComponent(PropertyDefinition.builder("foo").build());
    propertyDb.insertProperties(newGlobalPropertyDto().setKey("foo").setValue("one"));

    executeRequestOnGlobalSetting("foo");
    assertGlobalPropertyDoesNotExist("foo");
  }

  @Test
  public void remove_global_setting_even_if_not_defined() throws Exception {
    logInAsSystemAdministrator();
    propertyDb.insertProperties(newGlobalPropertyDto().setKey("foo").setValue("one"));

    executeRequestOnGlobalSetting("foo");
    assertGlobalPropertyDoesNotExist("foo");
  }

  @Test
  public void remove_component_setting() throws Exception {
    logInAsProjectAdmin();
    definitions.addComponent(PropertyDefinition.builder("foo").onQualifiers(PROJECT).build());
    propertyDb.insertProperties(newComponentPropertyDto(project).setKey("foo").setValue("value"));

    executeRequestOnProjectSetting("foo");
    assertProjectPropertyDoesNotExist("foo");
  }

  @Test
  public void remove_component_setting_even_if_not_defined() throws Exception {
    logInAsProjectAdmin();
    propertyDb.insertProperties(newComponentPropertyDto(project).setKey("foo").setValue("value"));

    executeRequestOnProjectSetting("foo");
    assertProjectPropertyDoesNotExist("foo");
  }

  @Test
  public void remove_hidden_setting() throws Exception {
    logInAsSystemAdministrator();
    definitions.addComponent(PropertyDefinition.builder("foo").hidden().build());
    propertyDb.insertProperties(newGlobalPropertyDto().setKey("foo").setValue("one"));

    executeRequestOnGlobalSetting("foo");
    assertGlobalPropertyDoesNotExist("foo");
  }

  @Test
  public void ignore_project_setting_when_removing_global_setting() throws Exception {
    logInAsSystemAdministrator();
    propertyDb.insertProperties(newGlobalPropertyDto().setKey("foo").setValue("one"));
    propertyDb.insertProperties(newComponentPropertyDto(project).setKey("foo").setValue("value"));

    executeRequestOnGlobalSetting("foo");

    assertGlobalPropertyDoesNotExist("foo");
    assertProjectPropertyExists("foo");
  }

  @Test
  public void ignore_global_setting_when_removing_project_setting() throws Exception {
    logInAsProjectAdmin();
    propertyDb.insertProperties(newGlobalPropertyDto().setKey("foo").setValue("one"));
    propertyDb.insertProperties(newComponentPropertyDto(project).setKey("foo").setValue("value"));

    executeRequestOnProjectSetting("foo");

    assertGlobalPropertyExists("foo");
    assertProjectPropertyDoesNotExist("foo");
  }

  @Test
  public void ignore_user_setting_when_removing_global_setting() throws Exception {
    logInAsSystemAdministrator();
    UserDto user = dbClient.userDao().insert(dbSession, UserTesting.newUserDto());
    propertyDb.insertProperties(newUserPropertyDto("foo", "one", user));

    executeRequestOnGlobalSetting("foo");
    assertUserPropertyExists("foo", user);
  }

  @Test
  public void ignore_user_setting_when_removing_project_setting() throws Exception {
    logInAsProjectAdmin();
    UserDto user = dbClient.userDao().insert(dbSession, UserTesting.newUserDto());
    propertyDb.insertProperties(newUserPropertyDto("foo", "one", user));

    executeRequestOnProjectSetting("foo");
    assertUserPropertyExists("foo", user);
  }

  @Test
  public void ignore_unknown_setting_key() throws Exception {
    logInAsSystemAdministrator();

    executeRequestOnGlobalSetting("unknown");
  }

  @Test
  public void remove_setting_by_deprecated_key() throws Exception {
    logInAsSystemAdministrator();
    definitions.addComponent(PropertyDefinition.builder("foo").deprecatedKey("old").build());
    propertyDb.insertProperties(newGlobalPropertyDto().setKey("foo").setValue("one"));

    executeRequestOnGlobalSetting("old");
    assertGlobalPropertyDoesNotExist("foo");
  }

  @Test
  public void empty_204_response() {
    logInAsSystemAdministrator();
    TestResponse result = ws.newRequest()
      .setParam("keys", "my.key")
      .execute();

    assertThat(result.getStatus()).isEqualTo(HTTP_NO_CONTENT);
    assertThat(result.getInput()).isEmpty();
  }

  @Test
  public void test_ws_definition() {
    WebService.Action action = ws.getDef();
    assertThat(action).isNotNull();
    assertThat(action.isInternal()).isFalse();
    assertThat(action.isPost()).isTrue();
    assertThat(action.responseExampleAsString()).isNull();
    assertThat(action.params()).hasSize(2);
  }

  @Test
  public void throw_ForbiddenException_if_global_setting_and_not_system_administrator() throws Exception {
    userSession.logIn().setNonSystemAdministrator();
    definitions.addComponent(PropertyDefinition.builder("foo").build());

    expectedException.expect(ForbiddenException.class);
    expectedException.expectMessage("Insufficient privileges");

    executeRequestOnGlobalSetting("foo");
  }

  @Test
  public void throw_ForbiddenException_if_project_setting_and_not_project_administrator() throws Exception {
    userSession.logIn().addProjectUuidPermissions(USER, project.uuid());
    definitions.addComponent(PropertyDefinition.builder("foo").build());

    expectedException.expect(ForbiddenException.class);
    expectedException.expectMessage("Insufficient privileges");

    executeRequestOnComponentSetting("foo", project);
  }

  @Test
  public void throw_ForbiddenException_if_project_setting_and_system_administrator() throws Exception {
    logInAsSystemAdministrator();
    definitions.addComponent(PropertyDefinition.builder("foo").build());

    expectedException.expect(ForbiddenException.class);
    expectedException.expectMessage("Insufficient privileges");

    executeRequestOnComponentSetting("foo", project);
  }

  @Test
  public void fail_when_not_global_and_no_component() {
    logInAsSystemAdministrator();
    definitions.addComponent(PropertyDefinition.builder("foo")
      .onlyOnQualifiers(VIEW)
      .build());

    expectedException.expect(BadRequestException.class);
    expectedException.expectMessage("Setting 'foo' cannot be global");

    executeRequestOnGlobalSetting("foo");
  }

  @Test
  public void fail_when_qualifier_not_included() {
    userSession.logIn().setRoot();
    definitions.addComponent(PropertyDefinition.builder("foo")
      .onQualifiers(VIEW)
      .build());
    i18n.put("qualifier." + PROJECT, "project");

    expectedException.expect(BadRequestException.class);
    expectedException.expectMessage("Setting 'foo' cannot be set on a project");

    executeRequestOnComponentSetting("foo", project);
  }

  @Test
  public void fail_to_reset_setting_component_when_setting_is_global() {
    userSession.logIn().setRoot();

    definitions.addComponent(PropertyDefinition.builder("foo").build());
    i18n.put("qualifier." + PROJECT, "project");

    expectedException.expect(BadRequestException.class);
    expectedException.expectMessage("Setting 'foo' cannot be set on a project");

    executeRequestOnComponentSetting("foo", project);
  }

  private void executeRequestOnGlobalSetting(String key) {
    executeRequest(key, null);
  }

  private void executeRequestOnProjectSetting(String key) {
    executeRequest(key, project.key());
  }

  private void executeRequestOnComponentSetting(String key, ComponentDto componentDto) {
    executeRequest(key, componentDto.key());
  }

  private void executeRequest(String key, @Nullable String componentKey) {
    TestRequest request = ws.newRequest()
      .setMediaType(MediaTypes.PROTOBUF)
      .setParam("keys", key);
    if (componentKey != null) {
      request.setParam("component", componentKey);
    }
    request.execute();
  }

  private void logInAsSystemAdministrator() {
    userSession.logIn().setSystemAdministrator();
  }

  private void logInAsProjectAdmin() {
    userSession.logIn().addProjectUuidPermissions(ADMIN, project.uuid());
  }

  private void assertGlobalPropertyDoesNotExist(String key) {
    assertThat(dbClient.propertiesDao().selectGlobalProperty(dbSession, key)).isNull();
  }

  private void assertGlobalPropertyExists(String key) {
    assertThat(dbClient.propertiesDao().selectGlobalProperty(dbSession, key)).isNotNull();
  }

  private void assertProjectPropertyDoesNotExist(String key) {
    assertThat(dbClient.propertiesDao().selectByQuery(PropertyQuery.builder().setComponentId(project.getId()).setKey(key).build(), dbSession)).isEmpty();
  }

  private void assertProjectPropertyExists(String key) {
    assertThat(dbClient.propertiesDao().selectByQuery(PropertyQuery.builder().setComponentId(project.getId()).setKey(key).build(), dbSession)).isNotEmpty();
  }

  private void assertUserPropertyExists(String key, UserDto user) {
    assertThat(dbClient.propertiesDao().selectByQuery(PropertyQuery.builder()
      .setKey(key)
      .setUserId(user.getId())
      .build(),
      dbSession)).isNotEmpty();
  }

}
