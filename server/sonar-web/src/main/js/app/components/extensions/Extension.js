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
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { addGlobalErrorMessage } from '../../../store/globalMessages/duck';
import { getCurrentUser } from '../../../store/rootReducer';
import { translate } from '../../../helpers/l10n';
import { getExtensionStart } from './utils';
import getStore from '../../utils/getStore';

type Props = {
  currentUser: Object,
  extension: {
    key: string,
    title: string
  },
  onFail: (string) => void,
  options?: {},
  router: Object
};

class Extension extends React.Component {
  container: Object;
  props: Props;
  stop: ?Function;

  componentDidMount() {
    this.startExtension();
  }

  componentDidUpdate(prevProps: Props) {
    if (prevProps.extension !== this.props.extension) {
      this.stopExtension();
      this.startExtension();
    }
  }

  componentWillUnmount() {
    this.stopExtension();
  }

  handleStart = (start: Function) => {
    const store = getStore();
    this.stop = start({
      store,
      el: this.container,
      currentUser: this.props.currentUser,
      router: this.props.router,
      ...this.props.options
    });
  };

  handleFailure = () => {
    this.props.onFail(translate('page_extension_failed'));
  };

  startExtension() {
    const { extension } = this.props;
    getExtensionStart(extension.key).then(this.handleStart, this.handleFailure);
  }

  stopExtension() {
    this.stop && this.stop();
    this.stop = null;
  }

  render() {
    return (
      <div>
        <div ref={container => this.container = container} />
      </div>
    );
  }
}

const mapStateToProps = state => ({
  currentUser: getCurrentUser(state)
});

const mapDispatchToProps = { onFail: addGlobalErrorMessage };

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(Extension));
