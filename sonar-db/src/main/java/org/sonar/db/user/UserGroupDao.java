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
package org.sonar.db.user;

import org.sonar.db.Dao;
import org.sonar.db.DbSession;

public class UserGroupDao implements Dao {

  public UserGroupDto insert(DbSession session, UserGroupDto dto) {
    mapper(session).insert(dto);
    return dto;
  }

  public void delete(DbSession session, long groupId, long userId) {
    mapper(session).delete(groupId, userId);
  }

  public void deleteByGroupId(DbSession session, long groupId) {
    mapper(session).deleteByGroupId(groupId);
  }

  private static UserGroupMapper mapper(DbSession session) {
    return session.getMapper(UserGroupMapper.class);
  }
}
