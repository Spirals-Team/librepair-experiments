import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

import { mockOperatorMetaData } from './mock-operator-metadata.data';
import { OperatorMetadata } from '../../types/operator-schema.interface';

import '../../../common/rxjs-operators';
import { EMPTY_OPERATOR_METADATA } from './operator-metadata.service';

@Injectable()
export class StubOperatorMetadataService {

  private operatorMetadataObservable = Observable
    .of(mockOperatorMetaData)
    .shareReplay(1);

  constructor() { }

  public getOperatorMetadata(): Observable<OperatorMetadata> {
    return this.operatorMetadataObservable;
  }

  public operatorTypeExists(operatorType: string): boolean {
    const operator = mockOperatorMetaData.operators.filter(op => op.operatorType === operatorType);
    if (operator.length === 0) {
      return false;
    }
    return true;
  }

}
