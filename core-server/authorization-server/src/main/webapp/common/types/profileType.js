'use strict';

import type roleType from './roleType';

export default class profileType {
  created: number;
  lastUpdated: number;
  id: number;
  username: string;
  email: string;
  isEnabled: boolean;
  isBlocked: boolean;
  roles: roleType[];
}