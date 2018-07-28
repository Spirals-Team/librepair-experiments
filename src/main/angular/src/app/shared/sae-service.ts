import {HttpClient, HttpHeaders} from '@angular/common/http';

export class SaeService {
  public static API = window.location.protocol + '//' + window.location.hostname + ':8080/sae';
  public static SEMP_API = window.location.protocol + '//' + window.location.hostname + ':8080/semp';
  protected headersContentTypeJson: HttpHeaders = new HttpHeaders().set('Content-Type', 'application/json');
  protected headersContentTypeXml: HttpHeaders = new HttpHeaders().set('Content-Type', 'application/xml');

  constructor(protected http: HttpClient) {
  }
}
