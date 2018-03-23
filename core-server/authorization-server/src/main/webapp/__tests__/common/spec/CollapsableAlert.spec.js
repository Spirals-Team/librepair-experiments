'use strict';

import React from 'react';

import {shallow} from 'enzyme';
import {expect} from 'chai';

import CollapsableAlert from '../../../common/components/CollapsableAlert';

describe('CollapsableAlert', () => {
  const STYLE_SUCCESS = 'success';
  const STYLE_INFO = 'info';
  const STYLE_DANGER = 'danger';
  const STYLE_WARNING = 'warning';

  it('renders success-message without problems', () => {
    var collapsableAlert = shallow(
      <CollapsableAlert collapse={false} style={STYLE_SUCCESS}/>
    );

    expect(collapsableAlert).to.be.ok;
  });

  it('renders info-message without problems', () => {
    var collapsableAlert = shallow(
      <CollapsableAlert collapse={false} style={STYLE_INFO}/>
    );

    expect(collapsableAlert).to.be.ok;
  });

  it('renders danger-message without problems', () => {
    var collapsableAlert = shallow(
      <CollapsableAlert collapse={false} style={STYLE_DANGER}/>
    );

    expect(collapsableAlert).to.be.ok;
  });

  it('renders warning-message without problems', () => {
    var collapsableAlert = shallow(
      <CollapsableAlert collapse={false} style={STYLE_WARNING}/>
    );

    expect(collapsableAlert).to.be.ok;
  });
});