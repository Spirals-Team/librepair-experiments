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
package org.sonar.server.computation.task.projectanalysis.webhook;

import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.sonar.api.utils.System2;
import org.sonar.core.util.UuidFactory;
import org.sonar.db.DbClient;
import org.sonar.db.DbSession;
import org.sonar.db.DbTester;
import org.sonar.db.webhook.WebhookDeliveryDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.sonar.db.webhook.WebhookDbTesting.newWebhookDeliveryDto;
import static org.sonar.db.webhook.WebhookDbTesting.selectAllDeliveryUuids;

public class WebhookDeliveryStorageTest {

  private static final String DELIVERY_UUID = "abcde1234";
  private static final long NOW = 1_500_000_000_000L;
  private static final long TWO_MONTHS_AGO = NOW - 60L * 24 * 60 * 60 * 1000;
  private static final long TWO_WEEKS_AGO = NOW - 14L * 24 * 60 * 60 * 1000;

  private final System2 system = mock(System2.class);

  @Rule
  public final DbTester dbTester = DbTester.create(system).setDisableDefaultOrganization(true);

  private DbClient dbClient = dbTester.getDbClient();
  private DbSession dbSession = dbTester.getSession();
  private UuidFactory uuidFactory = mock(UuidFactory.class);
  private WebhookDeliveryStorage underTest = new WebhookDeliveryStorage(dbClient, system, uuidFactory);

  @Test
  public void persist_generates_uuid_then_inserts_record() {
    when(uuidFactory.create()).thenReturn(DELIVERY_UUID);
    WebhookDelivery delivery = newBuilderTemplate().build();

    underTest.persist(delivery);

    WebhookDeliveryDto dto = dbClient.webhookDeliveryDao().selectByUuid(dbSession, DELIVERY_UUID).get();
    assertThat(dto.getUuid()).isEqualTo(DELIVERY_UUID);
    assertThat(dto.getComponentUuid()).isEqualTo(delivery.getWebhook().getComponentUuid());
    assertThat(dto.getCeTaskUuid()).isEqualTo(delivery.getWebhook().getCeTaskUuid());
    assertThat(dto.getName()).isEqualTo(delivery.getWebhook().getName());
    assertThat(dto.getUrl()).isEqualTo(delivery.getWebhook().getUrl());
    assertThat(dto.getCreatedAt()).isEqualTo(delivery.getAt());
    assertThat(dto.getHttpStatus()).isEqualTo(delivery.getHttpStatus().get());
    assertThat(dto.getDurationMs()).isEqualTo(delivery.getDurationInMs().get());
    assertThat(dto.getPayload()).isEqualTo(delivery.getPayload().toJson());
    assertThat(dto.getErrorStacktrace()).isNull();
  }

  @Test
  public void persist_error_stacktrace() {
    when(uuidFactory.create()).thenReturn(DELIVERY_UUID);
    WebhookDelivery delivery = newBuilderTemplate()
      .setError(new IOException("fail to connect"))
      .build();

    underTest.persist(delivery);

    WebhookDeliveryDto dto = dbClient.webhookDeliveryDao().selectByUuid(dbSession, DELIVERY_UUID).get();
    assertThat(dto.getErrorStacktrace()).contains("java.io.IOException", "fail to connect");
  }

  @Test
  public void purge_deletes_records_older_than_one_month_on_the_project() {
    when(system.now()).thenReturn(NOW);
    dbClient.webhookDeliveryDao().insert(dbSession, newDto("D1", "PROJECT_1", TWO_MONTHS_AGO));
    dbClient.webhookDeliveryDao().insert(dbSession, newDto("D2", "PROJECT_1", TWO_WEEKS_AGO));
    dbClient.webhookDeliveryDao().insert(dbSession, newDto("D3", "PROJECT_2", TWO_MONTHS_AGO));
    dbSession.commit();

    underTest.purge("PROJECT_1");

    // do not purge another project PROJECT_2
    assertThat(selectAllDeliveryUuids(dbTester, dbSession)).containsOnly("D2", "D3");
  }

  private static WebhookDelivery.Builder newBuilderTemplate() {
    return new WebhookDelivery.Builder()
      .setWebhook(new Webhook("COMPONENT1", "TASK1", "Jenkins", "http://jenkins"))
      .setPayload(new WebhookPayload("my-project", "{json}"))
      .setAt(1_000_000L)
      .setHttpStatus(200)
      .setDurationInMs(1_000);
  }

  private static WebhookDeliveryDto newDto(String uuid, String componentUuid, long at) {
    return newWebhookDeliveryDto()
      .setUuid(uuid)
      .setComponentUuid(componentUuid)
      .setCreatedAt(at);
  }
}
