import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';
import {Response} from '@angular/http';
import {environment} from '../../environments/environment';
import {Skill} from './../Interfaces/Skill';
import {Injectable} from '@angular/core';
import {Artefact} from '../Interfaces/Artefact';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class SkillService {

  private skillsSubject: Subject<Skill[]> = new ReplaySubject(1);

  constructor(public http: HttpClient) {
    this.getSkills();
  }


  getSkills(): Observable<Skill[]> {
    this.http.get<Skill[]>(`${environment.endpoint}/skill/`)
      .subscribe(
        result => this.skillsSubject.next(result),
        err => this.skillsSubject.error(err)
      );
    return this.skillsSubject.asObservable();
  }

  getSkillsForArtefact(artefact: Artefact): Promise<Skill[]> {
    return this.http.get<Skill[]>(`${environment.endpoint}/skill/artefact/${artefact.id}`)
      .toPromise()
      .catch(this.handleError);
  }

  deleteSkill(skill: Skill): Promise<any> {
    return this.http.delete(`${environment.endpoint}/skill/${skill.id}`)
      .toPromise()
  }

  createSkill(skill: any): Promise<Skill> {
    return this.http.post<Skill>(`${environment.endpoint}/skill/`, skill)
      .toPromise()
      .catch(this.handleError);
  }

  private handleError(error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Promise.reject(errMsg);
  }
}
