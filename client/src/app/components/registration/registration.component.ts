import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ReduxFormsNames } from '../../core/state/ReduxFormsNames';
import { Observable } from 'rxjs/Observable';
import { select } from '@angular-redux/store';
import { RegistrationService } from '../../core/services/RegistrationService';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  public registrationForm = ReduxFormsNames.registrationForm;
  @select([ReduxFormsNames.registrationForm, 'username'])
  readonly username: Observable<string>;
  private usernameValue: string;
  @select([ReduxFormsNames.registrationForm, 'password'])
  readonly password: Observable<string>;
  private passwordValue: string;
  @select([ReduxFormsNames.registrationForm, 'firstname'])
  readonly firstname: Observable<string>;
  private firstnameValue: string;
  @select([ReduxFormsNames.registrationForm, 'lastname'])
  readonly lastname: Observable<string>;
  private lastnameValue: string;

  public showErrorMessage = false;

  constructor(private router: Router, public registrationService: RegistrationService) {
    this.username.subscribe(value => this.usernameValue = value);
    this.password.subscribe(value => this.passwordValue = value);
    this.firstname.subscribe(value => this.firstnameValue = value);
    this.lastname.subscribe(value => this.lastnameValue = value);
  }

  ngOnInit() {
  }

  public onSubmit() {
    this.showErrorMessage = false;

    this.registrationService
      .registryUser(this.usernameValue, this.passwordValue, this.firstnameValue, this.lastnameValue)
      .subscribe(() => this.backToLogin(), () => this.showErrorMessage = true);
    console.log('erreom', this.showErrorMessage);
  }

  public backToLogin() {
    this.router.navigate(['/login']);
  }
}
