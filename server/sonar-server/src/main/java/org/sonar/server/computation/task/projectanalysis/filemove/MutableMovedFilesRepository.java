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
package org.sonar.server.computation.task.projectanalysis.filemove;

import org.sonar.server.computation.task.projectanalysis.component.Component;

import static org.sonar.server.computation.task.projectanalysis.component.Component.Type;

public interface MutableMovedFilesRepository extends MovedFilesRepository {
  /**
   * Registers the original file for the specified file of the report.
   *
   * @throws IllegalArgumentException if {@code file} type is not {@link Type#FILE}
   * @throws IllegalStateException if {@code file} already has an original file
   */
  void setOriginalFile(Component file, OriginalFile originalFile);
}
