import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {SettingsService} from './settings-service';
import {SettingsDefaults} from './settings-defaults';

@Injectable()
export class SettingsDefaultsResolver implements Resolve<SettingsDefaults> {

  constructor(private settingsService: SettingsService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<SettingsDefaults> {
    return this.settingsService.getSettingsDefaults();
  }
}
