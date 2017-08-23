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
package org.sonarqube.ws.client.setting;

public class SettingsWsParameters {
  public static final String CONTROLLER_SETTINGS = "api/settings";

  public static final String ACTION_LIST_DEFINITIONS = "list_definitions";
  public static final String ACTION_VALUES = "values";
  public static final String ACTION_SET = "set";
  public static final String ACTION_RESET = "reset";

  public static final String PARAM_COMPONENT = "component";
  public static final String PARAM_KEYS = "keys";
  public static final String PARAM_KEY = "key";
  public static final String PARAM_VALUE = "value";
  public static final String PARAM_VALUES = "values";
  public static final String PARAM_FIELD_VALUES = "fieldValues";

  private SettingsWsParameters() {
    // Only static stuff
  }

}
