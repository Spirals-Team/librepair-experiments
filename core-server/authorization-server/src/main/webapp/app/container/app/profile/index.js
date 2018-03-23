// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

import AccountInformation from '../../../components/profile/AccountInformation';

require('../../../styles/profile.css');

class Profile extends Component<Profile.propTypes> {
  render() {
    const {profile, texts} = this.props;

    return (
      <div>
        <AccountInformation account={profile} texts={texts}/>
      </div>
    );
  }
}

Profile.propTypes = {
  profile: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    profile: state.profile,
    texts: state.language.texts.profile
  }
}

export default connect(mapStateToProps)(Profile);