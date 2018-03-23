'use strict';

import React from 'react';

import {shallow} from 'enzyme';
import {expect} from 'chai';

import Navigation from '../../../app/components/Navigation' ;

describe('Navigation', () => {
  const texts = {};

  it('renders without problems', () => {
    const navigation = shallow(
      <Navigation logout={() => 'logout'} texts={texts}/>
    );

    expect(navigation).to.be.ok;
  });
})