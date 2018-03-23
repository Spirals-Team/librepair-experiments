'use strict';

import React from 'react';

import {shallow} from 'enzyme';
import {expect} from 'chai';

import FlashMessage from '../../../../app/components/common/FlashMessage';

describe('FlashMessage', () => {
  const testMessage = Math.random().toString(36).substring(7);

  it('renders without problems', () => {
    var flashMessage = shallow(
      <FlashMessage message={testMessage} deleteFlashMessage={() => 'delete'}/>
    );

    expect(flashMessage).to.be.ok;
  });
});