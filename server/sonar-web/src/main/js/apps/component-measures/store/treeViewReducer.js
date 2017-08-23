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
import { pick } from 'lodash';
import { DISPLAY_HOME } from './../app/actions';
import { UPDATE_STORE, INIT } from './treeViewActions';

const initialState = {
  components: [],
  breadcrumbs: [],
  total: 0
};

export default function drilldownReducer(state = initialState, action = {}) {
  switch (action.type) {
    case DISPLAY_HOME:
      return initialState;
    case UPDATE_STORE:
      return { ...state, ...action.state };
    case INIT:
      return { ...state, ...pick(action, ['rootComponent', 'metric', 'periodIndex']) };
    default:
      return state;
  }
}
