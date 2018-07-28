/*
Copyright (C) 2017 Axel Müller <axel.mueller@avanux.de>

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/

import {ErrorMessage, ValidatorType} from './error-message';
import {TranslateService} from '@ngx-translate/core';

export class ErrorMessages {

  constructor(private typePrefix: string, private errorMessages: ErrorMessage[], protected translate: TranslateService) {
    const controlValidators = [];
    this.errorMessages.forEach(errorMessage => controlValidators.push(this.buildTextKey(errorMessage)));
    this.translate.get(controlValidators).subscribe(translatedTexts => {
//      console.log('TRANSLATED=' + JSON.stringify(translatedTexts));
      this.errorMessages.forEach(errorMessage => {
        errorMessage.text = translatedTexts[this.buildTextKey(errorMessage)];
      });
    });
  }

  buildTextKey(errorMessage: ErrorMessage) {
    const key = this.typePrefix + errorMessage.forControl + '_' + ValidatorType[errorMessage.forValidator];
//    console.log('KEY=' + key);
    return key;
  }

  getErrorMessages(): ErrorMessage[] {
    return this.errorMessages;
  }

}
