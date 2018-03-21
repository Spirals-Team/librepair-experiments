'use strict';

import React from 'react';

import {shallow} from 'enzyme';
import {expect} from 'chai';

import Router from '../../app/Router';

describe('Router', () => {
  it('renders without problems', () => {
    const router = shallow(
      <Router/>
    );

    expect(router).to.be.ok;
  });
});