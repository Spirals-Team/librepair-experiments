// @flow
'use strict';

import axios, {CancelToken} from 'axios';

import type userType from '../../../common/types/userType';

var cancelPassword;

export default function (password: string, errors: userType, callback: (errors: userType) => void) {
  if (password == undefined || password == '') {
    delete errors.password;
    return;
  }

  if (cancelPassword != null) {
    cancelPassword();
  }

  axios.post('signup/does-password-match-format', {password: password}, {
    cancelToken: new CancelToken(function executor(c) {
      cancelPassword = c;
    })
  }).then(
    response => {
      delete errors.password;
      callback(errors);
    }, error => {
      if (!axios.isCancel(error)) {
        errors.password = error.response.data.message;
        callback(errors);
      }
    }
  );
}