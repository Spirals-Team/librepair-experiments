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
package org.sonar.scanner.scan.filesystem;

import org.junit.Test;
import org.sonar.scanner.repository.ProjectRepositories;
import org.sonar.scanner.scan.filesystem.StatusDetection;
import org.sonar.scanner.scan.filesystem.StatusDetectionFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class StatusDetectionFactoryTest {
  @Test
  public void testCreate() throws Exception {
    StatusDetectionFactory factory = new StatusDetectionFactory(mock(ProjectRepositories.class));
    StatusDetection detection = factory.create();
    assertThat(detection).isNotNull();
  }
}
