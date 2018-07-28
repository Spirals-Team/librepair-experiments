import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Control} from './control';
import {ControlService} from './control-service';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';

@Injectable()
export class ControlResolver implements Resolve<Control> {

  constructor(private controlService: ControlService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Control> {
    return this.controlService.getControl(route.params['id']);
  }
}
