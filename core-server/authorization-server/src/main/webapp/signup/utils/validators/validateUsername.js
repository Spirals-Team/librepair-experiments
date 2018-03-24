// @flow
'use strict';

import axios, {CancelToken} from 'axios';

import type userType from '../../../common/types/userType';

var cancelUsername;

export default function (username: string, errors: userType, callback: (errors: userType) => void) {
  if (username == undefined || username == '') {
    delete errors.username;
    return;
  }

  if (cancelUsername != null) {
    cancelUsername();
  }

  axios.post('signup/does-username-match-format', {username: username}, {
    cancelToken: new CancelToken(function executor(c) {
      cancelUsername = c;
    })
  }).then(
    response => {
      axios.post('signup/is-username-unique', {username: username}, {
        cancelToken: new CancelToken(function executor(c) {
          cancelUsername = c;
        })
      }).then(
        response => {
          delete errors.username;
          callback(errors);
        }, error => {
          if (!axios.isCancel(error)) {
            errors.username = error.response.data.message;
            callback(errors);
          }
        }
      );
    }, error => {
      if (!axios.isCancel(error)) {
        errors.username = error.response.data.message;
        callback(errors);
      }
    }
  );
}