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
package org.sonar.server.component.ws;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.sonar.api.config.MapSettings;
import org.sonar.api.resources.Qualifiers;
import org.sonar.api.server.ws.WebService;
import org.sonar.api.utils.System2;
import org.sonar.db.DbTester;
import org.sonar.db.component.ComponentDto;
import org.sonar.db.organization.OrganizationDto;
import org.sonar.server.component.index.ComponentIndex;
import org.sonar.server.component.index.ComponentIndexDefinition;
import org.sonar.server.component.index.ComponentIndexer;
import org.sonar.server.es.EsTester;
import org.sonar.server.es.ProjectIndexer;
import org.sonar.server.permission.index.AuthorizationTypeSupport;
import org.sonar.server.permission.index.PermissionIndexerTester;
import org.sonar.server.tester.UserSessionRule;
import org.sonar.server.ws.TestRequest;
import org.sonar.server.ws.WsActionTester;
import org.sonarqube.ws.WsComponents.Component;
import org.sonarqube.ws.WsComponents.SuggestionsWsResponse;
import org.sonarqube.ws.WsComponents.SuggestionsWsResponse.Project;

import static java.util.Optional.ofNullable;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.sonar.db.component.ComponentTesting.newModuleDto;
import static org.sonar.db.component.ComponentTesting.newProjectDto;
import static org.sonar.server.component.ws.SuggestionsAction.DEFAULT_LIMIT;
import static org.sonar.server.component.ws.SuggestionsAction.EXTENDED_LIMIT;
import static org.sonar.server.component.ws.SuggestionsAction.SHORT_INPUT_WARNING;
import static org.sonar.server.component.ws.SuggestionsAction.URL_PARAM_MORE;
import static org.sonar.server.component.ws.SuggestionsAction.URL_PARAM_QUERY;
import static org.sonarqube.ws.MediaTypes.PROTOBUF;
import static org.sonarqube.ws.WsComponents.SuggestionsWsResponse.Category;
import static org.sonarqube.ws.WsComponents.SuggestionsWsResponse.Organization;
import static org.sonarqube.ws.WsComponents.SuggestionsWsResponse.parseFrom;

public class SuggestionsActionTest {

  @Rule
  public DbTester db = DbTester.create(System2.INSTANCE);
  @Rule
  public EsTester es = new EsTester(new ComponentIndexDefinition(new MapSettings()));
  @Rule
  public UserSessionRule userSessionRule = UserSessionRule.standalone();

  private ComponentIndexer componentIndexer = new ComponentIndexer(db.getDbClient(), es.client());
  private ComponentIndex index = new ComponentIndex(es.client(), new AuthorizationTypeSupport(userSessionRule));
  private SuggestionsAction underTest = new SuggestionsAction(db.getDbClient(), index);
  private OrganizationDto organization;
  private PermissionIndexerTester authorizationIndexerTester = new PermissionIndexerTester(es, componentIndexer);
  private WsActionTester actionTester = new WsActionTester(underTest);

  @Before
  public void setUp() {
    organization = db.organizations().insert();
  }

  @Test
  public void define_suggestions_action() {
    WebService.Action action = actionTester.getDef();
    assertThat(action).isNotNull();
    assertThat(action.isInternal()).isTrue();
    assertThat(action.isPost()).isFalse();
    assertThat(action.handler()).isNotNull();
    assertThat(action.responseExampleAsString()).isNotEmpty();
    assertThat(action.params()).extracting(WebService.Param::key).containsExactlyInAnyOrder(
      URL_PARAM_MORE,
      URL_PARAM_QUERY);
  }

  @Test
  public void exact_match_in_one_qualifier() throws IOException {
    ComponentDto project = db.components().insertComponent(newProjectDto(organization));

    componentIndexer.indexOnStartup(null);
    authorizationIndexerTester.allowOnlyAnyone(project);

    SuggestionsWsResponse response = parseFrom(actionTester.newRequest()
      .setMethod("POST")
      .setMediaType(PROTOBUF)
      .setParam(URL_PARAM_QUERY, project.getKey())
      .execute()
      .getInputStream());

    // assert match in qualifier "TRK"
    assertThat(response.getSuggestionsList())
      .filteredOn(q -> q.getItemsCount() > 0)
      .extracting(Category::getCategory)
      .containsExactly(Qualifiers.PROJECT);

    // assert correct id to be found
    assertThat(response.getSuggestionsList())
      .flatExtracting(Category::getItemsList)
      .extracting(Component::getKey, Component::getOrganization)
      .containsExactly(tuple(project.getKey(), organization.getKey()));
  }

  @Test
  public void must_not_search_if_no_valid_tokens_are_provided() throws IOException {
    ComponentDto project = db.components().insertComponent(newProjectDto(organization).setName("SonarQube"));

    componentIndexer.indexOnStartup(null);
    authorizationIndexerTester.allowOnlyAnyone(project);

    SuggestionsWsResponse response = parseFrom(actionTester.newRequest()
      .setMethod("POST")
      .setMediaType(PROTOBUF)
      .setParam(URL_PARAM_QUERY, "S o")
      .execute()
      .getInputStream());

    assertThat(response.getSuggestionsList()).filteredOn(q -> q.getItemsCount() > 0).isEmpty();
    assertThat(response.getWarning()).contains(SHORT_INPUT_WARNING);
  }

  @Test
  public void should_warn_about_short_inputs() throws IOException {
    SuggestionsWsResponse response = parseFrom(actionTester.newRequest()
      .setMethod("POST")
      .setMediaType(PROTOBUF)
      .setParam(URL_PARAM_QUERY, "validLongToken x")
      .execute()
      .getInputStream());

    assertThat(response.getWarning()).contains(SHORT_INPUT_WARNING);
  }

  @Test
  public void should_contain_organization_names() throws IOException {
    OrganizationDto organization1 = db.organizations().insert(o -> o.setKey("org-1").setName("Organization One"));
    OrganizationDto organization2 = db.organizations().insert(o -> o.setKey("org-2").setName("Organization Two"));

    ComponentDto project1 = db.components().insertComponent(newProjectDto(organization1).setName("Project1"));
    componentIndexer.indexProject(project1.projectUuid(), ProjectIndexer.Cause.PROJECT_CREATION);
    authorizationIndexerTester.allowOnlyAnyone(project1);

    ComponentDto project2 = db.components().insertComponent(newProjectDto(organization2).setName("Project2"));
    componentIndexer.indexProject(project2.projectUuid(), ProjectIndexer.Cause.PROJECT_CREATION);
    authorizationIndexerTester.allowOnlyAnyone(project2);

    SuggestionsWsResponse response = parseFrom(actionTester.newRequest()
      .setMethod("POST")
      .setMediaType(PROTOBUF)
      .setParam(URL_PARAM_QUERY, "Project")
      .execute()
      .getInputStream());

    assertThat(response.getOrganizationsList())
      .extracting(Organization::getKey, Organization::getName)
      .containsExactlyInAnyOrder(
        of(organization1, organization2)
          .map(o -> tuple(o.getKey(), o.getName())).toArray(Tuple[]::new));
  }

  @Test
  public void should_contain_project_names() throws IOException {
    ComponentDto project = db.components().insertComponent(newProjectDto(organization));
    db.components().insertComponent(newModuleDto(project).setName("Module1"));
    db.components().insertComponent(newModuleDto(project).setName("Module2"));
    componentIndexer.indexProject(project.projectUuid(), ProjectIndexer.Cause.PROJECT_CREATION);
    authorizationIndexerTester.allowOnlyAnyone(project);

    SuggestionsWsResponse response = parseFrom(actionTester.newRequest()
      .setMethod("POST")
      .setMediaType(PROTOBUF)
      .setParam(URL_PARAM_QUERY, "Module")
      .execute()
      .getInputStream());

    assertThat(response.getSuggestionsList())
      .flatExtracting(Category::getItemsList)
      .extracting(Component::getProject)
      .containsOnly(project.key());

    assertThat(response.getProjectsList())
      .extracting(Project::getKey, Project::getName)
      .containsExactlyInAnyOrder(
        tuple(project.key(), project.longName()));
  }

  @Test
  public void should_propose_to_show_more_results_if_7_projects_are_found() throws IOException {
    check_proposal_to_show_more_results(7, DEFAULT_LIMIT, 1L, null);
  }

  @Test
  public void should_not_propose_to_show_more_results_if_6_projects_are_found() throws IOException {
    check_proposal_to_show_more_results(6, DEFAULT_LIMIT, 0L, null);
  }

  @Test
  public void should_not_propose_to_show_more_results_if_5_projects_are_found() throws IOException {
    check_proposal_to_show_more_results(5, DEFAULT_LIMIT, 0L, null);
  }

  @Test
  public void show_show_more_results_if_requested() throws IOException {
    check_proposal_to_show_more_results(21, EXTENDED_LIMIT, 1L, SuggestionCategory.PROJECT);
  }

  private void check_proposal_to_show_more_results(int numberOfProjects, int results, long numberOfMoreResults, @Nullable SuggestionCategory more) throws IOException {
    String namePrefix = "MyProject";

    List<ComponentDto> projects = range(0, numberOfProjects)
      .mapToObj(i -> db.components().insertComponent(newProjectDto(organization).setName(namePrefix + i)))
      .collect(Collectors.toList());

    componentIndexer.indexOnStartup(null);
    projects.forEach(authorizationIndexerTester::allowOnlyAnyone);

    TestRequest request = actionTester.newRequest()
      .setMethod("POST")
      .setMediaType(PROTOBUF)
      .setParam(URL_PARAM_QUERY, namePrefix);
    ofNullable(more).ifPresent(c -> request.setParam(URL_PARAM_MORE, c.getName()));
    SuggestionsWsResponse response = parseFrom(request
      .execute()
      .getInputStream());

    // assert match in qualifier "TRK"
    assertThat(response.getSuggestionsList())
      .filteredOn(q -> q.getItemsCount() > 0)
      .extracting(Category::getCategory)
      .containsExactly(Qualifiers.PROJECT);

    // include limited number of results in the response
    assertThat(response.getSuggestionsList())
      .flatExtracting(Category::getItemsList)
      .hasSize(Math.min(results, numberOfProjects));

    // indicate, that there are more results
    assertThat(response.getSuggestionsList())
      .filteredOn(q -> q.getItemsCount() > 0)
      .extracting(Category::getMore)
      .containsExactly(numberOfMoreResults);
  }
}
