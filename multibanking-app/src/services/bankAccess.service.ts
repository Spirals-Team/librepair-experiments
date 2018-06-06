import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { BankAccess } from "../api/BankAccess";
import { Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ENV } from "../env/env";

@Injectable()
export class BankAccessService {

  public bankAccessDeletedObservable = new Subject();

  constructor(private http: HttpClient) {
  }

  getBankAccesses(): Observable<Array<BankAccess>> {
    return this.http.get(ENV.api_url + "/bankaccesses")
      .map((res: any) => {
        return res._embedded != null ? res._embedded.bankAccessEntityList : []
      })
      .catch(this.handleError);
  }

  createBankAcccess(bankaccess: BankAccess): Observable<Object> {
    return this.http.post(ENV.api_url + "/bankaccesses", bankaccess, { responseType: 'text' })
      .catch(this.handleError);
  }

  updateBankAcccess(bankaccess: BankAccess): Observable<any> {
    return this.http.put(ENV.api_url + "/bankaccesses/" + bankaccess.id, bankaccess)
      .catch(this.handleError);
  }

  deleteBankAccess(accessId): Observable<any> {
    return this.http.delete(ENV.api_url + "/bankaccesses/" + accessId)
      .catch(this.handleError)
      .finally(() => this.bankAccessDeletedObservable.next(accessId));
  }

  handleError(error) {
    console.error(error);
    let errorJson = error.json();
    if (errorJson) {
      return Observable.throw(errorJson || 'Server error');
    } else {
      return Observable.throw(error || 'Server error');
    }
  }


}
