import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {Settings} from './settings';
import {SettingsService} from './settings-service';

@Injectable()
export class SettingsResolver implements Resolve<Settings> {

  constructor(private settingsService: SettingsService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Settings> {
    return this.settingsService.getSettings();
  }
}
