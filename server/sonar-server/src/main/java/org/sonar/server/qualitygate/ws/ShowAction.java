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
package org.sonar.server.qualitygate.ws;

import com.google.common.io.Resources;
import java.util.Collection;
import javax.annotation.Nullable;
import org.sonar.api.server.ws.Request;
import org.sonar.api.server.ws.Response;
import org.sonar.api.server.ws.WebService;
import org.sonar.api.utils.text.JsonWriter;
import org.sonar.db.qualitygate.QualityGateConditionDto;
import org.sonar.db.qualitygate.QualityGateDto;
import org.sonar.server.exceptions.BadRequestException;
import org.sonar.server.qualitygate.QualityGates;
import org.sonarqube.ws.client.qualitygate.QualityGatesWsParameters;

public class ShowAction implements QualityGatesWsAction {

  private final QualityGates qualityGates;

  public ShowAction(QualityGates qualityGates) {
    this.qualityGates = qualityGates;
  }

  @Override
  public void define(WebService.NewController controller) {
    WebService.NewAction action = controller.createAction("show")
      .setDescription("Display the details of a quality gate")
      .setSince("4.3")
      .setResponseExample(Resources.getResource(this.getClass(), "example-show.json"))
      .setHandler(this);

    action.createParam(QualityGatesWsParameters.PARAM_ID)
      .setDescription("ID of the quality gate. Either id or name must be set")
      .setExampleValue("1");

    action.createParam(QualityGatesWsParameters.PARAM_NAME)
      .setDescription("Name of the quality gate. Either id or name must be set")
      .setExampleValue("My Quality Gate");
  }

  @Override
  public void handle(Request request, Response response) {
    Long qGateId = request.paramAsLong(QualityGatesWsParameters.PARAM_ID);
    String qGateName = request.param(QualityGatesWsParameters.PARAM_NAME);
    checkOneOfIdOrNamePresent(qGateId, qGateName);

    QualityGateDto qGate = qGateId == null ? qualityGates.get(qGateName) : qualityGates.get(qGateId);
    qGateId = qGate.getId();

    JsonWriter writer = response.newJsonWriter().beginObject()
      .prop(QualityGatesWsParameters.PARAM_ID, qGate.getId())
      .prop(QualityGatesWsParameters.PARAM_NAME, qGate.getName());
    Collection<QualityGateConditionDto> conditions = qualityGates.listConditions(qGateId);
    if (!conditions.isEmpty()) {
      writer.name("conditions").beginArray();
      for (QualityGateConditionDto condition : conditions) {
        QualityGatesWs.writeQualityGateCondition(condition, writer);
      }
      writer.endArray();
    }
    writer.endObject().close();
  }

  private static void checkOneOfIdOrNamePresent(@Nullable Long qGateId, @Nullable String qGateName) {
    if (qGateId == null && qGateName == null) {
      throw BadRequestException.create("Either one of 'id' or 'name' is required.");
    } else if (qGateId != null && qGateName != null) {
      throw BadRequestException.create("Only one of 'id' or 'name' must be provided.");
    }
  }

}
