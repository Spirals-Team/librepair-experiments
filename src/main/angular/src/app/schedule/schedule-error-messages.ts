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

import {ErrorMessage, ValidatorType} from '../shared/error-message';
import {ErrorMessages} from '../shared/error-messages';
import {TranslateService} from '@ngx-translate/core';

export class ScheduleErrorMessages extends ErrorMessages {

  constructor(protected translate: TranslateService) {
    super('ScheduleComponent.error.',
      [
//        new ErrorMessage('schedule.0.dayTimeframe.startTime', ValidatorType.required),
        new ErrorMessage('dayTimeframe_startTime', ValidatorType.required),
        new ErrorMessage('dayTimeframe_startTime', ValidatorType.pattern),
        new ErrorMessage('dayTimeframe_endTime', ValidatorType.required),
        new ErrorMessage('dayTimeframe_endTime', ValidatorType.pattern),
        new ErrorMessage('dayTimeframe_minRunningTime', ValidatorType.required),
        new ErrorMessage('dayTimeframe_minRunningTime', ValidatorType.pattern),
        new ErrorMessage('dayTimeframe_maxRunningTime', ValidatorType.pattern),
        new ErrorMessage('consecutiveDaysTimeframe_startTime', ValidatorType.required),
        new ErrorMessage('consecutiveDaysTimeframe_startTime', ValidatorType.pattern),
        new ErrorMessage('consecutiveDaysTimeframe_endTime', ValidatorType.required),
        new ErrorMessage('consecutiveDaysTimeframe_endTime', ValidatorType.pattern),
        new ErrorMessage('consecutiveDaysTimeframe_minRunningTime', ValidatorType.required),
        new ErrorMessage('consecutiveDaysTimeframe_minRunningTime', ValidatorType.pattern),
        new ErrorMessage('consecutiveDaysTimeframe_maxRunningTime', ValidatorType.pattern)
      ], translate
    );
  }
}
