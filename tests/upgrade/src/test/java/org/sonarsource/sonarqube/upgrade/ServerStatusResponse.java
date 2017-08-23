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
package org.sonarsource.sonarqube.upgrade;

public class ServerStatusResponse {
  private final String id;
  private final String version;
  private final Status status;

  public ServerStatusResponse(String id, String version, Status status) {
    this.id = id;
    this.version = version;
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public String getVersion() {
    return version;
  }

  public Status getStatus() {
    return status;
  }

  public static enum Status {
    UP, DOWN, DB_MIGRATION_NEEDED, DB_MIGRATION_RUNNING
  }
}
