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
// @flow
import React from 'react';
import { css } from 'glamor';
import { translate } from '../../../helpers/l10n';

type Props = {|
  myIssues: boolean,
  onMyIssuesChange: (boolean) => void
|};

export default class MyIssuesFilter extends React.Component {
  props: Props;

  handleClick = (myIssues: boolean) =>
    (e: Event & { currentTarget: HTMLElement }) => {
      e.preventDefault();
      e.currentTarget.blur();
      this.props.onMyIssuesChange(myIssues);
    };

  render() {
    const { myIssues } = this.props;

    return (
      <div className={css({ marginBottom: 24, textAlign: 'center' })}>
        <div className="button-group">
          <button
            className={myIssues ? 'button-active' : undefined}
            onClick={this.handleClick(true)}>
            {translate('issues.my_issues')}
          </button>
          <button
            className={myIssues ? undefined : 'button-active'}
            onClick={this.handleClick(false)}>
            {translate('all')}
          </button>
        </div>
      </div>
    );
  }
}
