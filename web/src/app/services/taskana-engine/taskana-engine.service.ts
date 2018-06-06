import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from 'environments/environment';
import { UserInfoModel } from 'app/models/user-info';
import { ReplaySubject } from 'rxjs/ReplaySubject';


@Injectable()
export class TaskanaEngineService {

  currentUserInfo: UserInfoModel;

  constructor(
    private httpClient: HttpClient
  ) { }

  // GET
  getUserInformation(): Promise<any> {
    return this.httpClient.get<any>(`${environment.taskanaRestUrl}/v1/current-user-info`).map(
      data => {
        this.currentUserInfo = data
      }
    ).toPromise();
  }

  hasRole(roles2Find: Array<string>): boolean {
    if (!this.currentUserInfo || this.currentUserInfo.roles.length < 1) {
      return false;
    }
    if (this.findRole(roles2Find)) {
      return true;
    }
    return false;
  }

  private findRole(roles2Find: Array<string>) {
    return this.currentUserInfo.roles.find(role => {
      return roles2Find.some(roleLookingFor => {
        if (role === roleLookingFor) {
          return true;
        }
      });
    });
  }
}
