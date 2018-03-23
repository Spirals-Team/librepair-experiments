// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

import isEmpty from 'lodash/isEmpty';

import {addFlashMessage} from '../../common/actions/flashMessageActions';

import hasAuthority from './hasAuthority';

export default function (ComposedComponent: Component, authority: string) {
  class AccessWithAuthority extends Component<AccessWithAuthority.propTypes> {
    displayWarningMessage: (props: AccessWithAuthority.propTypes) => void;

    constructor(props) {
      super(props);

      this.displayWarningMessage = this.displayWarningMessage.bind(this);
    }

    componentWillMount() {
      const {profile} = this.props;

      if (!isEmpty(profile) && !hasAuthority(authority, profile)) {
        this.displayWarningMessage(this.props);
        this.context.router.history.push('/');
      }
    }

    componentWillUpdate(nextProps: AccessWithAuthority.propTypes) {
      const {profile} = nextProps;

      if (!hasAuthority(authority, profile)) {
        this.displayWarningMessage(nextProps);
        this.context.router.history.push('/');
      }
    }

    displayWarningMessage(props: AccessWithAuthority.propTypes) {
      const {texts} = props;
      const {addFlashMessage} = props.actions;

      addFlashMessage({
        type: 'warning',
        title: texts.access_denied_alert_title,
        text: texts.access_denied_alert_text
      });

    }

    render() {
      const {profile} = this.props;

      if (isEmpty(profile)) {
        return null;
      }

      return (
        <ComposedComponent {...this.props} />
      );
    }
  }

  AccessWithAuthority.propTypes = {
    actions: PropTypes.object.isRequired,
    profile: PropTypes.object.isRequired,
    texts: PropTypes.object.isRequired
  }

  AccessWithAuthority.contextTypes = {
    router: PropTypes.object.isRequired
  }

  function mapStateToProps(state) {
    return {
      profile: state.profile,
      texts: state.language.texts.app
    }
  }

  function mapDispatchToProps(dispatch) {
    return {
      actions: {
        addFlashMessage: bindActionCreators(addFlashMessage, dispatch)
      }
    }
  }

  return connect(mapStateToProps, mapDispatchToProps)(AccessWithAuthority);
}