// @flow
'use strict';

import profileType from '../../common/types/profileType';

import AuthorityConstants from '../../common/contants/AuthorityConstants';
import roleType from '../../common/types/roleType';

export default function (authority: string, profile: profileType) {
  if (profile == null || profile.roles == null || AuthorityConstants.indexOf(authority) === -1) {
    return false;
  }

  const applyingAuthorities = AuthorityConstants.splice(0, AuthorityConstants.indexOf(authority) + 1);

  return profile.roles.some((role: roleType) => applyingAuthorities.indexOf(role.name) !== -1);
}